package me.droreo002.spy.commands;

import me.droreo002.spy.MainPlugin;
import me.droreo002.spy.lang.Lang;
import me.droreo002.spy.lang.Messages;
import me.droreo002.spy.utils.PlaceholderType;
import me.droreo002.spy.utils.SpyPlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.swing.text.PlainDocument;
import java.util.List;

public class MainCommand implements CommandExecutor {

    private MainPlugin main;

    public MainCommand(MainPlugin main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String gakGuna, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(main.getPrefix() + "Please use /spyc instead!");
            return false;
        }

        Player player = (Player) sender;
        Lang lang = main.getLang();

        if (args.length > 0) {
            if (args.length == 1) {
                switch (args[0].toLowerCase()) {
                    case "clearenabled":
                        main.getSpyManager().clear(player);
                        break;
                    case "about":
                        player.sendMessage(main.translateText("This plugin is made by &c@DrOreo002"));
                        player.sendMessage(main.translateText("&f&ohttp://bit.ly/2C4eurf &7| &6Spigot &fpage"));
                        break;
                    case "help":
                        if (!player.hasPermission("spy.command")) {
                            player.sendMessage(lang.getMessage(Messages.NO_PERMISSION));
                            break;
                        }
                        List<String> msg_help = lang.getListMessage(Messages.HELP);
                        for (String s : msg_help) {
                            player.sendMessage(main.translateText(s));
                        }
                        break;
                    case "reload":
                        if (!player.hasPermission("spy.admin")) {
                            player.sendMessage(lang.getMessage(Messages.NO_PERMISSION));
                            break;
                        }
                        lang.reloadLang();
                        main.getConfigManager().reloadConfig();
                        main.reloadPrefix();
                        player.sendMessage(lang.getMessage(Messages.RELOADED));
                        break;
                    case "on":
                        if (!player.hasPermission("spy.toggle")) {
                            player.sendMessage(lang.getMessage(Messages.NO_PERMISSION));
                            break;
                        }
                        main.getSpyManager().addEnabledSelf(player);
                        break;
                    case "off":
                        if (!player.hasPermission("spy.toggle")) {
                            player.sendMessage(lang.getMessage(Messages.NO_PERMISSION));
                            break;
                        }
                        main.getSpyManager().removeEnabledSelf(player);
                        break;
                    case "player":
                        if (!player.hasPermission("spy.other")) {
                            player.sendMessage(lang.getMessage(Messages.NO_PERMISSION));
                            break;
                        }
                        player.sendMessage(lang.getMessage(Messages.INVALID_USAGE));
                        break;
                    default:
                        player.sendMessage(lang.getMessage(Messages.INVALID_USAGE));
                        break;
                }
            }
            if (args.length > 1) {
                if (args[0].equalsIgnoreCase("on")) {
                    if (args.length > 2) {
                        player.sendMessage(lang.getMessage(Messages.TOO_MUCH_ARGS));
                        return false;
                    }
                    if (!player.hasPermission("spy.toggle.other")) {
                        player.sendMessage(lang.getMessage(Messages.NO_PERMISSION));
                        return false;
                    }
                    main.getSpyManager().addEnableOther(args[1], player);
                    return false;
                }
                if (args[0].equalsIgnoreCase("off")) {
                    if (args.length > 2) {
                        player.sendMessage(lang.getMessage(Messages.TOO_MUCH_ARGS));
                        return false;
                    }
                    if (!player.hasPermission("spy.toggle.other")) {
                        player.sendMessage(lang.getMessage(Messages.NO_PERMISSION));
                        return false;
                    }
                    main.getSpyManager().removeEnableOther(args[1], player);
                    return false;
                }
                if (args[0].equalsIgnoreCase("player") && args[1].equalsIgnoreCase("add")) {
                    if (args.length > 3) {
                        player.sendMessage(lang.getMessage(Messages.TOO_MUCH_ARGS));
                        return false;
                    }
                    if (!player.hasPermission("spy.other")) {
                        player.sendMessage(lang.getMessage(Messages.NO_PERMISSION));
                        return false;
                    }
                    if (!main.getSpyManager().getEnabled().contains(player.getUniqueId())) {
                        player.sendMessage(lang.getMessage(Messages.SPY_NOT_ENABLED));
                        return false;
                    }
                    main.getSpyManager().addSpiedPlayer(player, args[2]);
                    return false;
                }
                if (args[0].equalsIgnoreCase("player") && args[1].equalsIgnoreCase("remove")) {
                    if (args.length > 3) {
                        player.sendMessage(lang.getMessage(Messages.TOO_MUCH_ARGS));
                        return false;
                    }
                    if (!player.hasPermission("spy.other")) {
                        player.sendMessage(lang.getMessage(Messages.NO_PERMISSION));
                        return false;
                    }
                    if (!main.getSpyManager().getEnabled().contains(player.getUniqueId())) {
                        player.sendMessage(lang.getMessage(Messages.SPY_NOT_ENABLED));
                        return false;
                    }
                    main.getSpyManager().removeSpiedPlayer(player, args[2]);
                    return false;
                }
            }
            return false;
        } else {
            player.sendMessage(main.getLang().getMessage(Messages.DEFAULT));
        }
        return false;
    }
}
