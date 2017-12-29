package me.droreo002.spy.manager;

import me.droreo002.spy.MainPlugin;
import me.droreo002.spy.lang.Messages;
import me.droreo002.spy.utils.AddType;
import me.droreo002.spy.utils.PlaceholderType;
import me.droreo002.spy.utils.SpyPlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class SpyManager {

    private Set<UUID> enabled = new HashSet<>();
    private HashMap<UUID, Set<UUID>> spied_player = new HashMap<>();
    private static SpyManager obj;
    private MainPlugin main;

    private SpyManager(MainPlugin main) {
        this.main = main;
    }

    public static SpyManager getSpyManager(MainPlugin main) {
        if (obj == null) {
            obj = new SpyManager(main);
            return obj;
        }
        return obj;
    }

    public Set<UUID> getEnabled() {
        return enabled;
    }

    public Set<UUID> getSpiedPlayerFor(UUID id) {
        return spied_player.get(id);
    }

    public HashMap<UUID, Set<UUID>> getAllSpiedPlayer() {
        return spied_player;
    }

    public void executeForSpied(Player player, String[] msg, String all) {
        if (player.hasPermission("spy.ignore")) {
            return;
        }
        boolean blocked = checkIfBlocked(msg);

        if (!blocked) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                if (enabled.contains(players.getUniqueId()) && main.getSpyManager().getAllSpiedPlayer().containsKey(players.getUniqueId())) {
                    String first = new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.SPY_FOR_PLAYER_FORMAT), player.getName());
                    String result = new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_TO_COMMAND, first, all);
                    players.sendMessage(main.getSpyPrefix() + result);
                }
            }
        } else {
            for (Player players : Bukkit.getOnlinePlayers()) {
                if (enabled.contains(players.getUniqueId()) && main.getSpyManager().getAllSpiedPlayer().containsKey(players.getUniqueId())) {
                    if (!players.hasPermission("spy.bypass.blocked")) {
                        if (main.getConfigManager().getConfig().getBoolean("ShowDisabledCommandMessage")) {
                            players.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.SPY_FOR_PLAYER_FORMAT_BLOCKED), player.getName()));
                        }
                    } else {
                        String first = new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.SPY_FOR_PLAYER_FORMAT), player.getName());
                        String result = new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_TO_COMMAND, first, all);
                        players.sendMessage(main.getSpyPrefix() + result);
                    }
                }
            }
        }
    }

    public void execute(Player player, String[] msg, String all) {
        if (player.hasPermission("spy.ignore")) {
            return;
        }
        boolean blocked = checkIfBlocked(msg);

        if (!blocked) {
            for (Player players : Bukkit.getOnlinePlayers()) {
                if (enabled.contains(players.getUniqueId()) && !main.getSpyManager().getAllSpiedPlayer().containsKey(players.getUniqueId())) {
                    String first = new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.SPY_FORMAT), player.getName());
                    String result = new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_TO_COMMAND, first, all);
                    players.sendMessage(main.getSpyPrefix() + result);
                    continue;
                }
                if (enabled.contains(players.getUniqueId()) && main.getSpyManager().getAllSpiedPlayer().containsKey(players.getUniqueId())) {
                    Set<UUID> list = main.getSpyManager().getAllSpiedPlayer().get(players.getUniqueId());
                    for (UUID id : list) {
                        Player target = Bukkit.getPlayer(id);
                        if (target == null || !player.getName().toLowerCase().equals(target.getName().toLowerCase())) {
                            continue;
                        }
                        String first = new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.SPY_FOR_PLAYER_FORMAT), target.getName());
                        String result = new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_TO_COMMAND, first, all);
                        players.sendMessage(main.getSpyPrefix() + result);
                    }
                }
            }
        } else {
            for (Player players : Bukkit.getOnlinePlayers()) {
                if (enabled.contains(players.getUniqueId()) && !main.getSpyManager().getAllSpiedPlayer().containsKey(players.getUniqueId())) {
                    if (!players.hasPermission("spy.bypass.blocked")) {
                        if (main.getConfigManager().getConfig().getBoolean("ShowDisabledCommandMessage")) {
                            players.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.SPY_FORMAT_BLOCKED), player.getName()));
                        }
                    } else {
                        String first = new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.SPY_FORMAT), player.getName());
                        String result = new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_TO_COMMAND, first, all);
                        players.sendMessage(main.getSpyPrefix() + result);
                        continue;
                    }
                }
                if (enabled.contains(players.getUniqueId()) && main.getSpyManager().getAllSpiedPlayer().containsKey(players.getUniqueId())) {
                    if (!players.hasPermission("spy.bypass.blocked")) {
                        if (main.getConfigManager().getConfig().getBoolean("ShowDisabledCommandMessage")) {
                            Set<UUID> list = main.getSpyManager().getAllSpiedPlayer().get(players.getUniqueId());
                            for (UUID id : list) {
                                Player target = Bukkit.getPlayer(id);
                                if (target == null || !player.getName().toLowerCase().equals(target.getName().toLowerCase())) {
                                    continue;
                                }
                                players.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.SPY_FOR_PLAYER_FORMAT_BLOCKED), target.getName()));
                            }
                        }
                    } else {
                        Set<UUID> list = main.getSpyManager().getAllSpiedPlayer().get(players.getUniqueId());
                        for (UUID id : list) {
                            Player target = Bukkit.getPlayer(id);
                            if (target == null || !player.getName().toLowerCase().equals(target.getName().toLowerCase())) {
                                continue;
                            }
                            String first = new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.SPY_FOR_PLAYER_FORMAT), target.getName());
                            String result = new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_TO_COMMAND, first, all);
                            players.sendMessage(main.getSpyPrefix() + result);
                        }
                    }
                }
            }
        }
    }

    private boolean checkIfBlocked(String[] msg) {
        List<String> list = main.getConfigManager().getConfig().getStringList("DisabledCommand");
        for (String s : list) {
            if (msg[0].equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    public void clear(Player executor) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (enabled.contains(player.getUniqueId())) {
                player.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.HAS_CLEARED), executor.getName()));
            }
        }
        executor.sendMessage(main.getLang().getMessage(Messages.CLEARED));
        enabled.clear();
        spied_player.clear();
    }

    public void clear(String name) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (enabled.contains(player.getUniqueId())) {
                player.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.HAS_CLEARED), name));
            }
        }
        enabled.clear();
    }
    public void addEnabledSelf(Player player) {
        if (enabled.contains(player.getUniqueId())) {
            player.sendMessage(main.getLang().getMessage(Messages.TOGGLE_ON_ALREADY));
            return;
        }
        enabled.add(player.getUniqueId());
        player.sendMessage(main.getLang().getMessage(Messages.TOGGLE_ON));
    }

    public void addEnableOther(String name, Player executor) {
        Player target = Bukkit.getPlayerExact(name);
        if (target == null) {
            executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.PLAYER_NOT_ONLINE), name));
            return;
        }
        if (target.hasPermission("spy.toggle.other.deny")) {
            executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.TOGGLE_OTHER_FAILED), name));
            return;
        }
        if (enabled.contains(target.getUniqueId())) {
            executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.TOGGLE_ON_OTHER_ALREADY), name));
            return;
        }
        executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.TOGGLE_ON_OTHER), name));
        target.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.TOGGLE_ON_OTHER_MESSAGE), executor.getName()));
        enabled.add(target.getUniqueId());
    }

    @SuppressWarnings("deprecation")
    public void removeEnableOther(String name, Player executor) {
        Player target = Bukkit.getPlayerExact(name);
        if (target == null) {
            OfflinePlayer off = Bukkit.getOfflinePlayer(name);
            if (!off.hasPlayedBefore()) {
                executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.NEVER_JOINED), name));
                return;
            }
            if (!enabled.contains(off.getUniqueId())) {
                executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.TOGGLE_OFF_OTHER_ALREADY), name));
                return;
            }
            executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.TOGGLE_OFF_OTHER), name));
            enabled.remove(off.getUniqueId());
            return;
        }

        if (!enabled.contains(target.getUniqueId())) {
            executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.TOGGLE_OFF_OTHER_ALREADY), name));
            return;
        }
        executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.TOGGLE_OFF_OTHER), name));
        target.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.TOGGLE_OFF_OTHER_MESSAGE), executor.getName()));
        enabled.remove(target.getUniqueId());
    }

    public void removeEnabledSelf(Player player) {
        if (!enabled.contains(player.getUniqueId())) {
            player.sendMessage(main.getLang().getMessage(Messages.TOGGLE_OFF_ALREADY));
            return;
        }
        player.sendMessage(main.getLang().getMessage(Messages.TOGGLE_OFF));
        enabled.remove(player.getUniqueId());
        spied_player.remove(player.getUniqueId());
    }

    public void addSpiedPlayer(Player executor, String name) {
        Player target = Bukkit.getPlayerExact(name);
        if (target == null) {
            executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.PLAYER_NOT_ONLINE), name));
            return;
        }

        if (executor.getName().equals(name)) {
            executor.sendMessage(main.getLang().getMessage(Messages.SPY_FAIL_SELF));
            return;
        }

        if (target.hasPermission("spy.other.deny")) {
            executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.SPY_FAIL_PERMS), name));
            return;
        }

        if (enabled.contains(target.getUniqueId()) || spied_player.containsKey(target.getUniqueId())) {
            executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.SPY_FAIL_ENABLED), name));
            return;
        }

        if (!spied_player.containsKey(executor.getUniqueId())) {
            spied_player.put(executor.getUniqueId(), new HashSet<>());
        }

        if (spied_player.get(executor.getUniqueId()).contains(target.getUniqueId())) {
            executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.ALREADY_SPIED), name));
            return;
        }

        spied_player.get(executor.getUniqueId()).add(target.getUniqueId());
        executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.SPY_DONE), name));
    }

    @SuppressWarnings("deprecation")
    public void removeSpiedPlayer(Player executor, String name) {
        if (!spied_player.containsKey(executor.getUniqueId())) {
            return;
        }
        Player target = Bukkit.getPlayerExact(name);
        if (target == null) {
            OfflinePlayer off = Bukkit.getOfflinePlayer(name);
            if (!off.hasPlayedBefore()) {
                executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.NEVER_JOINED), name));
                return;
            }
            if (!spied_player.get(executor.getUniqueId()).contains(off.getUniqueId())) {
                executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.NOT_ON_SPY_LIST), name));
                return;
            }
            executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.PLAYER_REMOVED), name));
            spied_player.remove(executor.getUniqueId());
            return;
        }

        if (!spied_player.get(executor.getUniqueId()).contains(target.getUniqueId())) {
            executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.NOT_ON_SPY_LIST), name));
            return;
        }
        executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.PLAYER_REMOVED), name));
        spied_player.remove(executor.getUniqueId());
    }

    public void addEnableSelfJoin(Player player) {
        if (enabled.contains(player.getUniqueId())) {
            player.sendMessage(main.getLang().getMessage(Messages.TOGGLE_ON_ALREADY));
            return;
        }
        enabled.add(player.getUniqueId());
        player.sendMessage(main.getLang().getMessage(Messages.TOGGLE_ON_JOIN));
    }

    public void removeEnableSelfLeave(Player player) {
        if (!enabled.contains(player.getUniqueId())) {
            return;
        }
        enabled.remove(player.getUniqueId());
        Bukkit.getLogger().info(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.TOGGLE_OFF_LEAVE), player.getName()));
    }

    @SuppressWarnings("deprecation")
    public void removeSpiedPlayerQuit(Player executor, String name) {
        if (!spied_player.containsKey(executor.getUniqueId())) {
            return;
        }
        Player target = Bukkit.getPlayerExact(name);
        if (target == null) {
            OfflinePlayer off = Bukkit.getOfflinePlayer(name);
            if (!off.hasPlayedBefore()) {
                return;
            }
            if (!spied_player.get(executor.getUniqueId()).contains(off.getUniqueId())) {
                return;
            }
            executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.SPIED_PLAYER_QUIT), name));
            spied_player.remove(executor.getUniqueId());
            return;
        }

        if (!spied_player.get(executor.getUniqueId()).contains(target.getUniqueId())) {
            return;
        }
        executor.sendMessage(new SpyPlaceholder().replaceText(PlaceholderType.REPLACE_PLAYER_NAME, main.getLang().getMessage(Messages.SPIED_PLAYER_QUIT), name));
        spied_player.remove(executor.getUniqueId());
    }
}
