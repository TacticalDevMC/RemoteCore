package com.github.tacticaldevmc.remotecore.remotecore.utils;

import org.bukkit.Bukkit;

public class Reflection {
    private static String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    public Reflection() {
    }

    public static Class<?> getCraftBukkitClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        } catch (ClassNotFoundException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException var2) {
            var2.printStackTrace();
            return null;
        }
    }
}