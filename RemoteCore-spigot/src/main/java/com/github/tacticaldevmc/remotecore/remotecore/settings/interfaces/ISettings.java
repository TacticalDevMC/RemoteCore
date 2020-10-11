package com.github.tacticaldevmc.remotecore.remotecore.settings.interfaces;

import com.github.tacticaldevmc.remotecore.remotecore.config.interfaces.IConf;

import java.util.List;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public interface ISettings extends IConf {

    String getPrefix();

    String getPermissionMessage();

    String getLang();

    String getStorage();

    String getToken();

    String getGuildID();

    String getLogChannelID();

    boolean minecraftDiscord();

    boolean minecraftDiscordSync();

    boolean isDebug();

    boolean isCheckingForUpdates();

    Integer getMinecraftDiscordSynInterval();

    List<String> getMinecraftDiscordSyncList();

    void setDebug(boolean debug);

}
