package me.droreo002.spy.listener;

import me.droreo002.spy.MainPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private MainPlugin main;

    public JoinListener(MainPlugin main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.hasPermission("spy.enableOnJoin") && main.getConfigManager().getConfig().getBoolean("EnableOnJoin")) {
            main.getSpyManager().addEnabledSelf(player);
        }
    }
}
