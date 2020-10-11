package com.github.tacticaldevmc.remotecore.remotecore.utils;

import org.bukkit.ChatColor;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class Colors {

    public static String format(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static String formatBungee(String input) {
        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', input);
    }

    public static String replace(String message, String from, String to) {
        return message.replace(from, to);
    }

}
