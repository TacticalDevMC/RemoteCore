package com.github.tacticaldevmc.remotecore.utils;

import lombok.Getter;
import org.bukkit.ChatColor;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

@Getter
public enum Prefix {

    INFO("&7[&aInfo&7]", ChatColor.GREEN),
    ERROR("&7[&4Error&7]", ChatColor.DARK_RED),
    WARNING("&7[&bWarning&7]", ChatColor.BLUE),
    HIGH_LIGHT("&7[&6HighLight&7]", ChatColor.GOLD),
    LOG("&7[&eLog&7]", ChatColor.YELLOW);

    Prefix(String prefix, ChatColor chatColor, Boolean bungeeColor) {
        if (bungeeColor) {
            this.prefix = Colors.formatBungee(prefix);
        } else {
            this.prefix = Colors.format(prefix);
        }
        this.chatColor = chatColor;
        this.bungeeColor = bungeeColor;
    }

    Prefix(String prefix, ChatColor chatColor) {
        this.prefix = prefix;
        this.chatColor = chatColor;
    }

    String prefix;
    ChatColor chatColor;
    boolean bungeeColor;

    public ChatColor getChatColor() {
        return chatColor == null ? ChatColor.WHITE : ChatColor.GRAY;
    }

    public void setBungeeColor(boolean bungeeColor) {
        this.bungeeColor = bungeeColor;
    }
}
