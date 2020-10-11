package com.github.tacticaldevmc.remotecore.remotecore.config.interfaces;

import com.github.tacticaldevmc.remotecore.remotecore.config.Config;

public interface IConf {

    Config getConfig();

    void reload();

    String getName();

    String filePath();

}
