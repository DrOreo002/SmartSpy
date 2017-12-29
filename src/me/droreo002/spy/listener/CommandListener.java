package me.droreo002.spy.listener;

import me.droreo002.spy.MainPlugin;
import net.minecraft.server.v1_12_R1.CommandList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class CommandListener implements Listener {

    private MainPlugin main;

    public CommandListener(MainPlugin main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        String msg = e.getMessage();
        String[] args = msg.split(" ");
        /*
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (main.getSpyManager().getEnabled().contains(players.getUniqueId()) && !main.getSpyManager().getAllSpiedPlayer().containsKey(players.getUniqueId())) {
                main.getSpyManager().execute(player, args, msg);
                continue;
            }
            if (main.getSpyManager().getEnabled().contains(players.getUniqueId()) && main.getSpyManager().getAllSpiedPlayer().containsKey(players.getUniqueId())) {
                Set<UUID> list = main.getSpyManager().getAllSpiedPlayer().get(players.getUniqueId());
                for (UUID id : list) {
                    Player target = Bukkit.getPlayer(id);
                    if (target == null || !player.getName().toLowerCase().equals(target.getName().toLowerCase())) {
                        continue;
                    }
                    main.getSpyManager().executeForSpied(player, args, msg);
                }
            }
        } */
        main.getSpyManager().execute(player, args, msg);
    }
}
