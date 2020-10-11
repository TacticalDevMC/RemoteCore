package com.github.tacticaldevmc.remotecore.remotecore.utils;

import com.github.tacticaldevmc.remotecore.remotecore.RemoteCoreSpigot;
import org.bukkit.Bukkit;

public class ThreadUtil {

    /**
     * Run a task in a separate thread.
     *
     * @param runnable the task.
     */
    public static void async(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(RemoteCoreSpigot.getInstance(), runnable);
    }

    /**
     * Run a task in the main thread.
     *
     * @param runnable the task.
     */
    public static void sync(Runnable runnable) {
        Bukkit.getScheduler().runTask(RemoteCoreSpigot.getInstance(), runnable);
    }

}