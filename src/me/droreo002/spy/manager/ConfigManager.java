package me.droreo002.spy.manager;

import me.droreo002.spy.MainPlugin;
import org.apache.commons.lang.NullArgumentException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class ConfigManager {

    private FileConfiguration MainConfig;
    private File MainConfigFile;
    private MainPlugin plugin;
    private static ConfigManager obj;

    private ConfigManager(MainPlugin plugin) {
        this.plugin = plugin;
    }

    public static ConfigManager getConfigManager(MainPlugin plugin) {
        if (obj == null) {
            obj = new ConfigManager(plugin);
            return obj;
        }
        return obj;
    }

    public void setup()
    {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        MainConfigFile = new File(plugin.getDataFolder(), "config.yml");
        if (!MainConfigFile.exists()) {
            try
            {
                MainConfigFile.createNewFile();
                plugin.saveResource("config.yml", true);
            }
            catch (IOException e)
            {
                Bukkit.getServer().getLogger().log(Level.SEVERE, "Could not save " + MainConfigFile + ".", e);
            }
        }
        MainConfig = YamlConfiguration.loadConfiguration(MainConfigFile);
    }

    public FileConfiguration getConfig()
    {
        if (MainConfig == null) {
            reloadConfig();
        }
        return MainConfig;
    }

    public void saveConfig()
    {
        if (MainConfig == null) {
            throw new NullArgumentException("Cannot save a non-existant file!");
        }
        try
        {
            MainConfig.save(MainConfigFile);
        }
        catch (IOException e)
        {
            Bukkit.getServer().getLogger().log(Level.SEVERE, "Could not save " + MainConfigFile + ".", e);
        }
    }

    public void reloadConfig()
    {
        MainConfigFile = new File(plugin.getDataFolder(), "config.yml");
        if (!MainConfigFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        MainConfig = YamlConfiguration.loadConfiguration(MainConfigFile);
        InputStream configData = plugin.getResource("config.yml");
        if (configData != null) {
            MainConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(configData)));
        }
    }
}
