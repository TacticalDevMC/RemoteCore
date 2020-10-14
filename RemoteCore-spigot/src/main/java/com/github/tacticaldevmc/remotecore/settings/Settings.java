package com.github.tacticaldevmc.remotecore.settings;

import com.github.tacticaldevmc.remotecore.config.Config;
import com.github.tacticaldevmc.remotecore.settings.interfaces.ISettings;
import com.github.tacticaldevmc.remotecore.utils.Colors;

import java.util.List;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class Settings implements ISettings {

    private static Config config;

    public Settings() {
        config = new Config("config");
        reload();
    }

    @Override
    public String getPrefix() {
        return Colors.format(config.getString("prefix"));
    }

    @Override
    public String getPermissionMessage() {
        return Colors.format(config.getString("noPerms"));
    }

    @Override
    public String getLang() {
        return config.getString("lang");
    }

    @Override
    public String getStorage() {
        return config.getString("storage");
    }

    @Override
    public String getToken() {
        return config.getString("token");
    }

    @Override
    public String getGuildID() {
        return config.getString("guild-id");
    }

    @Override
    public String getLogChannelID() {
        return config.getString("log-channel-id");
    }

    @Override
    public String getChatFormat() {
        return config.getString("format");
    }

    @Override
    public boolean minecraftDiscord() {
        return config.getBoolean("minecraft-discord.enabled");
    }

    @Override
    public boolean minecraftDiscordSync() {
        return config.getBoolean("minecraft-discord.sync");
    }

    @Override
    public void setDebug(final boolean debug) {
        this.debug = debug;
    }

    private boolean debug = false;
    private boolean configDebug = false;

    private boolean _isDebug() {
        return config.getBoolean("debug", false);
    }

    @Override
    public boolean isDebug() {
        return debug || configDebug;
    }

    @Override
    public boolean isCheckingForUpdates() {
        return false;
    }

    @Override
    public Integer getMinecraftDiscordSynInterval() {
        return config.getInt("minecraft-discord.sync-interval");
    }

    @Override
    public List<String> getMinecraftDiscordSyncList() {
        return config.getStringList("minecraft-discord.sync-list");
    }

    @Override
    public Config getConfig() {
        return config;
    }

    @Override
    public void reload() {
        config.reload();

        configDebug = _isDebug();
    }

    @Override
    public String getName() {
        return config.getFile().getName();
    }

    @Override
    public String filePath() {
        return null;
    }
}
