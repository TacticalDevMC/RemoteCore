package com.github.tacticaldevmc.remotecore.storage;

import com.github.tacticaldevmc.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.config.Config;
import com.github.tacticaldevmc.remotecore.settings.interfaces.ISettings;
import com.github.tacticaldevmc.remotecore.storage.impl.MySQLImplemention;
import com.github.tacticaldevmc.remotecore.storage.interfaces.IStorage;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class StorageManager {

    private Config config;
    private final ISettings settings = RemoteCoreSpigot.getInstance().getSettings();
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
