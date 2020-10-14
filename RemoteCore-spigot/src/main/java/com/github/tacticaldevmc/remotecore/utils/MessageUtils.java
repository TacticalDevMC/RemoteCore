package com.github.tacticaldevmc.remotecore.utils;

import java.util.Collection;
import java.util.List;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class MessageUtils {

    public static String formatBoolean(boolean b) {
        return b ? "&atrue" : "&cfalse";
    }

    public static String listToArrowSep(List<String> strings) {
        if (strings.isEmpty()) {
            return "&6None";
        }

        StringBuilder sb = new StringBuilder();
        strings.forEach(s -> sb.append("&3").append(s).append("&b ---> "));
        return sb.delete(sb.length() - 6, sb.length()).toString();
    }

    public static String toCommaSep(Collection<String> strings) {
        if (strings.isEmpty()) {
            return "&bNone";
        }

        StringBuilder sb = new StringBuilder();
        strings.forEach(s -> sb.append("&3").append(s).append("&7, "));
        return sb.delete(sb.length() - 2, sb.length()).toString();
    }

    public static String listToArrowSep(Collection<String> strings, String highlight) {
        if (strings.isEmpty()) {
            return "&bNone";
        }

        StringBuilder sb = new StringBuilder();
        strings.forEach(s -> sb.append(s.equalsIgnoreCase(highlight) ? "&b" : "&3").append(s).append("&7 ---> "));
        return sb.delete(sb.length() - 6, sb.length()).toString();
    }

    public static String listToArrowSep(Collection<String> strings, String highlightFirst, String highlightSecond, boolean reversed) {
        if (strings.isEmpty()) {
            return "&6None";
        }

        StringBuilder sb = new StringBuilder();
        for (String s : strings) {
            if (s.equalsIgnoreCase(highlightFirst)) {
                sb.append("&b").append(s).append("&4");
            } else if (s.equalsIgnoreCase(highlightSecond)) {
                sb.append("&b").append(s).append("&7");
            } else {
                sb.append("&3").append(s).append("&7");
            }

            sb.append(reversed ? " <--- " : " ---> ");
        }
        return sb.delete(sb.length() - 6, sb.length()).toString();
    }

}
