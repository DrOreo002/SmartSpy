package me.droreo002.spy.listener;

import me.droreo002.spy.MainPlugin;
import me.droreo002.spy.lang.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.Set;

public class ChatListener implements Listener {

    private MainPlugin main;

    public ChatListener(MainPlugin main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent e) {
        Player ePlayer = e.getPlayer();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (main.getSpyManager().getEnabled().contains(player.getUniqueId()) && main.getSpyManager().getAllSpiedPlayer().containsKey(player.getUniqueId())) {
                if (!player.hasPermission("spy.bypass.chatreceive") && main.getConfigManager().getConfig().getBoolean("DisableChatReceive")) {
                    e.getRecipients().remove(player);
                    player.sendMessage(main.getLang().getMessage(Messages.CHAT_WHEN_ENABLED));
                }
            }
        }
    }
}
