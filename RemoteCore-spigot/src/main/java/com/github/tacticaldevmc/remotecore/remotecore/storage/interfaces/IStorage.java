package com.github.tacticaldevmc.remotecore.remotecore.storage.interfaces;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public interface IStorage {

    void start();

    void stop();

    void restart();

    String backEndName();

    String getTable();

}
