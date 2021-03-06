package com.github.tacticaldevmc.remotecore.bot.utils;

public class OtherUtil {

    public static int parseShortTime(String timestr) {
        timestr = timestr.toLowerCase();
        if (!timestr.matches("\\d{1,8}[smhd]?"))
            return -1;
        int multiplier = 1;
        switch (timestr.charAt(timestr.length() - 1)) {
            case 'd':
                multiplier *= 24;
            case 'h':
                multiplier *= 60;
            case 'm':
                multiplier *= 60;
            case 's':
                timestr = timestr.substring(0, timestr.length() - 1);
            default:
        }
        return multiplier * Integer.parseInt(timestr);
    }

    public static int parseTime(String timestr) {
        timestr = timestr.replaceAll("(?i)(\\s|,|and)", "")
                .replaceAll("(?is)(-?\\d+|[a-z]+)", "$1 ")
                .trim();
        String[] vals = timestr.split("\\s+");
        int timeinseconds = 0;
        try {
            for (int j = 0; j < vals.length; j += 2) {
                int num = Integer.parseInt(vals[j]);
                if (vals[j + 1].toLowerCase().startsWith("m"))
                    num *= 60;
                else if (vals[j + 1].toLowerCase().startsWith("h"))
                    num *= 60 * 60;
                else if (vals[j + 1].toLowerCase().startsWith("d"))
                    num *= 60 * 60 * 24;
                timeinseconds += num;
            }
        } catch (Exception ex) {
            return 0;
        }
        return timeinseconds;
    }

    public static int parseWinners(String winstr) {
        if (!winstr.toLowerCase().matches("\\d{1,3}w"))
            return -1;
        return Integer.parseInt(winstr.substring(0, winstr.length() - 1));
    }
}