package me.droreo002.spy.listener;

import me.droreo002.spy.MainPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveListener implements Listener {

    private MainPlugin main;

    public LeaveListener(MainPlugin main) {
        this.main = main;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        if (player.hasPermission("spy.disableOnLeave") && main.getConfigManager().getConfig().getBoolean("DisableOnLeave")) {
            main.getSpyManager().removeEnableSelfLeave(e.getPlayer());
        }

        for (Player players : Bukkit.getOnlinePlayers()) {
            if (main.getSpyManager().getEnabled().contains(players.getUniqueId()) && main.getSpyManager().getAllSpiedPlayer().containsKey(players.getUniqueId())) {
                if (main.getConfigManager().getConfig().getBoolean("RemoveSpiedPlayerOnQuit")) {
                    main.getSpyManager().removeSpiedPlayerQuit(players, player.getName());
                }
            }
        }
    }
}
