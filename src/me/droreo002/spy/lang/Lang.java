package me.droreo002.spy.lang;

import me.droreo002.spy.MainPlugin;
import org.apache.commons.lang.NullArgumentException;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Lang {

    private FileConfiguration MainConfig;
    private File MainConfigFile;
    private MainPlugin plugin;
    private static Lang obj;

    private Lang(MainPlugin main) {
        this.plugin = main;
    }

    public static Lang getLangClass(MainPlugin main) {
        if (obj == null) {
            obj = new Lang(main);
            return obj;
        }
        return obj;
    }

    public String getMessage(Messages type) {

        switch (type) {
            case SPIED_PLAYER_QUIT:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Spied-Player-Quit"));
            case CHAT_WHEN_ENABLED:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Chat-When-Enabled"));
            case TOGGLE_OFF_LEAVE:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Toggle-Off-Leave"));
            case TOGGLE_ON_JOIN:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Toggle-On-Join"));
            case SPY_FAIL_SELF:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Cannot-Spy-Self"));
            case SPY_FAIL_PERMS:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Spy-Fail-Perms"));
            case SPY_FAIL_ENABLED:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Spy-Fail-Enabled"));
            case SPY_FOR_PLAYER_FORMAT:
                return plugin.translateText(getLang().getString("Spy-For-Player-Format"));
            case SPY_FOR_PLAYER_FORMAT_BLOCKED:
                return plugin.translateText(getLang().getString("Spy-For-Player-Format-Blocked"));
            case CONSOLE_ONLY:
                return plugin.translateText(getLang().getString("Console-Only"));
            case DEFAULT:
                return plugin.translateText(getLang().getString("Default-Message"));
            case DEFAULT_CONSOLE:
                return plugin.translateText(getLang().getString("Default-Message-Console"));
            case SPY_NOT_ENABLED:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Spy-Not-Enabled"));
            case INVALID_USAGE:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Invalid-Usage"));
            case TOGGLE_ON_OTHER_MESSAGE:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Toggle-On-Other-Message"));
            case TOGGLE_OFF_OTHER_MESSAGE:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Toggle-Off-Other-Message"));
            case TOGGLE_OTHER_FAILED:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Toggle-Other-Failed"));
            case SPY_FORMAT_BLOCKED:
                return plugin.translateText(getLang().getString("Spy-Format-Blocked"));
            case SPY_FORMAT:
                return plugin.translateText(getLang().getString("Spy-Format"));
            case HAS_CLEARED:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Cleared-Message"));
            case CLEARED:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Cleared"));
            case TOO_MUCH_ARGS:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Too-Much-Args"));
            case RELOADED:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Plugin-Reloaded"));
            case NO_PERMISSION:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("No-Permission"));
            case TOGGLE_ON:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Toggle-On"));
            case TOGGLE_OFF:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Toggle-Off"));
            case TOGGLE_ON_ALREADY:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Toggle-On-Already"));
            case TOGGLE_OFF_ALREADY:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Toggle-Off-Already"));
            case TOGGLE_ON_OTHER:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Toggle-On-Other"));
            case TOGGLE_OFF_OTHER:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Toggle-Off-Other"));
            case TOGGLE_ON_OTHER_ALREADY:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Toggle-Off-Other-Already"));
            case TOGGLE_OFF_OTHER_ALREADY:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Toggle-Off-Other-Already"));
            case PLAYER_NOT_ONLINE:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Player-Not-Online"));
            case ALREADY_SPIED:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Already-Spy"));
            case SPY_DONE:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Spy-Done"));
            case PLAYER_REMOVED:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Player-Removed"));
            case NOT_ON_SPY_LIST:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Not-On-Spy-List"));
            case CHAT_DISABLED:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Chat-Disabled"));
            case NEVER_JOINED:
                return plugin.getPrefix() + plugin.translateText(getLang().getString("Never-Joined"));
            default:
                return plugin.getPrefix() + "ERROR, please contact DEV!. Reason : Cannot find <type>";
        }
    }

    public List<String> getListMessage(Messages type) {
        List<String> defaults = new ArrayList<>();
        defaults.add("ERROR, please contact DEV!. Reason : Cannot find <type>");

        switch (type) {
            case HELP:
                return getLang().getStringList("Help-Message");
            case HELP_CONSOLE:
                return getLang().getStringList("Help-Message-Console");
            default:
                return defaults;
        }
    }
    public void setup()
    {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        MainConfigFile = new File(plugin.getDataFolder(), "lang.yml");
        if (!MainConfigFile.exists()) {
            try
            {
                MainConfigFile.createNewFile();
                plugin.saveResource("lang.yml", true);
            }
            catch (IOException e)
            {
                Bukkit.getServer().getLogger().log(Level.SEVERE, "Could not save " + MainConfigFile + ".", e);
            }
        }
        MainConfig = YamlConfiguration.loadConfiguration(MainConfigFile);
    }

    public FileConfiguration getLang()
    {
        if (MainConfig == null) {
            reloadLang();
        }
        return MainConfig;
    }

    public void saveLang()
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

    public void reloadLang()
    {
        MainConfigFile = new File(plugin.getDataFolder(), "lang.yml");
        if (!MainConfigFile.exists()) {
            plugin.saveResource("lang.yml", false);
        }
        MainConfig = YamlConfiguration.loadConfiguration(MainConfigFile);
        InputStream configData = plugin.getResource("lang.yml");
        if (configData != null) {
            MainConfig.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(configData)));
        }
    }
}
