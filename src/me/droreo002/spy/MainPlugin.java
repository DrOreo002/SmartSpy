package me.droreo002.spy;

import me.droreo002.spy.commands.ConsoleCommand;
import me.droreo002.spy.commands.MainCommand;
import me.droreo002.spy.lang.Lang;
import me.droreo002.spy.listener.CommandListener;
import me.droreo002.spy.manager.ConfigManager;
import me.droreo002.spy.manager.SpyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class MainPlugin extends JavaPlugin {

    private ConfigManager configManager;
    private Lang lang;
    private SpyManager spyManager;
    private String spyPrefix;
    private String prefix;
    public HashMap<UUID, String> debug = new HashMap<>();

    @Override
    public void onEnable() {
        spyManager = SpyManager.getSpyManager(this);
        lang = Lang.getLangClass(this);
        lang.setup();
        configManager = ConfigManager.getConfigManager(this);
        configManager.setup();
        reloadPrefix();
        getLogger().info("-========================================-");
        getLogger().info("> SmartSpy Has Been Enabled!");
        getLogger().info("> Version : " + getDescription().getVersion());
        getLogger().info("> Author : " + getDescription().getAuthors());
        getLogger().info("-========================================-");

        //Register Command and Listener Here
        Bukkit.getPluginCommand("smartspy").setExecutor(new MainCommand(this));
        Bukkit.getPluginCommand("smartspyconsole").setExecutor(new ConsoleCommand(this));
        Bukkit.getPluginManager().registerEvents(new CommandListener(this), this);
    }

    public void reloadPrefix() {
        prefix = translateText(getConfigManager().getConfig().getString("Prefix"));
        if (getConfigManager().getConfig().getBoolean("EnableSpyPrefix")) {
            spyPrefix = translateText(getConfigManager().getConfig().getString("SpyPrefix"));
        } else {
            spyPrefix = "";
        }
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSpyPrefix() {
        return this.spyPrefix;
    }

    public String translateText(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public SpyManager getSpyManager() {
        return spyManager;
    }

    public Lang getLang() {
        return lang;
    }
}
