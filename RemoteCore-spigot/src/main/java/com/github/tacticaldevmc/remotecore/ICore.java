package com.github.tacticaldevmc.remotecore;

import com.github.tacticaldevmc.remotecore.config.interfaces.IConf;
import com.github.tacticaldevmc.remotecore.messages.MessagesHandler;
import com.github.tacticaldevmc.remotecore.settings.interfaces.ISettings;
import com.github.tacticaldevmc.remotecore.storage.StorageManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public interface ICore {

    ISettings getSettings();

    MessagesHandler getMessageHandler();

    StorageManager getStorageManager();

    BukkitScheduler getScheduler();

    BukkitTask runTaskAsynchronously(Runnable run);

    BukkitTask runTaskLaterAsynchronously(Runnable run, long delay);

    BukkitTask runTaskTimerAsynchronously(Runnable run, long delay, long period);

    int scheduleSyncDelayedTask(Runnable run);

    int scheduleSyncDelayedTask(Runnable run, long delay);

    int scheduleSyncRepeatingTask(Runnable run, long delay, long period);

    void addReloadListener(IConf listener);

    void reload();
}
