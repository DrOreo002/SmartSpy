package me.droreo002.spy.commands;

import me.droreo002.spy.MainPlugin;
import me.droreo002.spy.lang.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ConsoleCommand implements CommandExecutor {

    private MainPlugin main;

    public ConsoleCommand(MainPlugin main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            sender.sendMessage(main.getLang().getMessage(Messages.CONSOLE_ONLY));
            return false;
        }

        if (args.length > 0) {
            if (args.length == 1) {
                switch (args[0].toLowerCase()) {
                    case "clear":
                        sender.sendMessage(main.getLang().getMessage(Messages.CLEARED));
                        main.getSpyManager().clear("CONSOLE");
                        break;
                    case "reload":
                        sender.sendMessage(main.getLang().getMessage(Messages.RELOADED));
                        main.reloadPrefix();
                        main.getConfigManager().reloadConfig();
                        main.getLang().reloadLang();
                        break;
                    case "about":
                        sender.sendMessage(main.translateText("This plugin is made by &c@DrOreo002"));
                        sender.sendMessage(main.translateText("&f&ohttp://bit.ly/2C4eurf &7| &6Spigot &fpage"));
                        break;
                    case "help":
                        List<String> msg = main.getLang().getListMessage(Messages.HELP_CONSOLE);
                        for (String s : msg) {
                            sender.sendMessage(main.translateText(s));
                        }
                        break;
                    default:
                        sender.sendMessage(main.getLang().getMessage(Messages.INVALID_USAGE));
                        break;
                }
            }
        } else {
            sender.sendMessage(main.getLang().getMessage(Messages.DEFAULT_CONSOLE));
        }
        return false;
    }
}
