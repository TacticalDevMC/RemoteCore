package com.github.tacticaldevmc.remotecore.remotecore.utils.utils;

import com.github.tacticaldevmc.remotecore.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.remotecore.logs.LogHandler;
import com.github.tacticaldevmc.remotecore.remotecore.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class PlayerUtil {

    public static final int ticksAtMidnight = 18000;
    public static final int ticksPerDay = 24000;
    public static final int ticksPerHour = 1000;
    public static final double ticksPerMinute = 1000d / 60d;
    public static final double ticksPerSecond = 1000d / 60d / 60d;
    private static final SimpleDateFormat twentyFour = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    private static final SimpleDateFormat twelve = new SimpleDateFormat("h:mm aa", Locale.ENGLISH);

    public static String getPing(Player base) {
        try {
            Method getHandleMethod = base.getClass().getDeclaredMethod("getHandle", new Class[0]);
            Object nmsPlayer = getHandleMethod.invoke(base, new Object[0]);
            Field pingField = nmsPlayer.getClass().getDeclaredField("ping");
            return String.valueOf(pingField.getInt(nmsPlayer));
        } catch (Exception e) {
            LogHandler.getHandler().logException(Prefix.ERROR, e);
        }
        return "0";
    }

    public static String format12(long ticks) {
        try {
            return twelve.format(twentyFour.parse(ticksToTime(ticks)));
        } catch (ParseException e) {
            return ticksToTime(ticks);
        }
    }

    public static String format24(long ticks) {
        return ticksToTime(ticks);
    }

    private static String ticksToTime(long ticks) {
        ticks = ticks - ticksAtMidnight + ticksPerDay;

        long hours = ticks / ticksPerHour;
        ticks -= hours * ticksPerHour;
        long mins = (long) Math.floor(ticks / ticksPerMinute);
        if (hours >= 24) {
            hours = hours - 24;
        }
        return (hours < 10 ? "0" + hours : hours) + ":" + (mins < 10 ? 0 : mins);
    }

    public static String getCardinalDirection(Player base) {
        double rotation = base.getLocation().getYaw() - 180.0F;
        if (rotation < 0.0D) {
            rotation += 360.0D;
        }
        if ((0.0D <= rotation) && (rotation < 22.5D)) {
            return "N";
        }
        if ((22.5D <= rotation) && (rotation < 67.5D)) {
            return "NE";
        }
        if ((67.5D <= rotation) && (rotation < 112.5D)) {
            return "E";
        }
        if ((112.5D <= rotation) && (rotation < 157.5D)) {
            return "SE";
        }
        if ((157.5D <= rotation) && (rotation < 202.5D)) {
            return "S";
        }
        if ((202.5D <= rotation) && (rotation < 247.5D) || (rotation <= -119.33) && (rotation > -179)) {
            return "SW";
        }
        if ((247.5D <= rotation) && (rotation < 292.5D) || (rotation <= -59.66) && (rotation > -119.33)) {
            return "W";
        }
        if ((292.5D <= rotation) && (rotation < 337.5D) || (rotation <= -0.0) && (rotation > -59.66)) {
            return "NW";
        }
        if ((337.5D <= rotation) && (rotation < 360.0D)) {
            return "N";
        }
        return "";
    }

    public static ItemStack itemInHand(Player base) {
        try {
            return base.getInventory().getItemInMainHand();
        } catch (NoSuchMethodError e) {
            return base.getInventory().getItemInHand();
        }
    }

    public static int getEmptySlots(Player base) {
        int slots = 0;
        PlayerInventory inv = base.getInventory();

        for (ItemStack is : inv.getContents()) {
            if (is == null) slots++;
        }

        if (!Bukkit.getBukkitVersion().contains("1.7") && !Bukkit.getBukkitVersion().contains("1.8")) {
            if (inv.getItemInOffHand() == null || inv.getItemInOffHand().getType() == Material.AIR) slots--;
            if (inv.getHelmet() == null) slots--;
            if (inv.getChestplate() == null) slots--;
            if (inv.getLeggings() == null) slots--;
            if (inv.getBoots() == null) slots--;
        }
        return slots;
    }

    private static int getExperienceAtLevel(int level) {
        if (level <= 15) {
            return (level << 1) + 7;
        }
        if (level <= 30) {
            return (level * 5) - 38;
        }
        return (level * 9) - 158;
    }

    public static int getTotalExperience(Player base) {
        int experience = Math.round(getExperienceAtLevel(base.getLevel()) * base.getExp());
        int currentLevel = base.getLevel();
        while (currentLevel > 0) {
            currentLevel--;
            experience += getExperienceAtLevel(currentLevel);
        }
        if (experience < 0) {
            experience = 0;
        }
        return experience;
    }

}
