package me.droreo002.spy.utils;

import org.bukkit.entity.Player;

public class SpyPlaceholder {

    public String replaceText(PlaceholderType type, String msg, String sample) {
        switch (type) {
            case REPLACE_PLAYER_NAME:
                if (msg.contains("{player}")) {
                    return msg.replace("{player}", sample);
                } else {
                    return msg;
                }
            case REPLACE_TO_COMMAND:
                if (msg.contains("{command}")) {
                    return msg.replace("{command}", sample);
                } else {
                    return msg;
                }
            default:
                return "ERROR!";
        }
    }
}
