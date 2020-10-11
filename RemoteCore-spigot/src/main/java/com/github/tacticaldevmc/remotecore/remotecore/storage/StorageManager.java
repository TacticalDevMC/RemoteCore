package com.github.tacticaldevmc.remotecore.remotecore.storage;

import com.github.tacticaldevmc.remotecore.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.remotecore.config.Config;
import com.github.tacticaldevmc.remotecore.remotecore.settings.interfaces.ISettings;
import com.github.tacticaldevmc.remotecore.remotecore.storage.impl.MySQLImplemention;
import com.github.tacticaldevmc.remotecore.remotecore.storage.interfaces.IStorage;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class StorageManager {

    private Config config;
    private ISettings settings = RemoteCoreSpigot.getInstance().getSettings();
    private IStorage storage;

    public IStorage manager() {
        if (settings.getStorage().equals("MYSQL")) {
            storage = new MySQLImplemention();

            return storage;
        } else {
            // TODO: File
        }
        return null;
    }

}
