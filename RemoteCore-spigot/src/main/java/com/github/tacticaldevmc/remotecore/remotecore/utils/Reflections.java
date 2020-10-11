package com.github.tacticaldevmc.remotecore.remotecore.utils;

import org.bukkit.Bukkit;

public class Reflections {

    public static Class<?> getNMSClass(String className) throws ClassNotFoundException {
        String version = getSpigotVersion() + ".";
        String name = "net.minecraft.server." + version + className;
        Class<?> nmsClass = Class.forName(name);
        return nmsClass;
    }

    public static String getSpigotVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

}