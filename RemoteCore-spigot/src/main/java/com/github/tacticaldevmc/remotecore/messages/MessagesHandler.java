package com.github.tacticaldevmc.remotecore.messages;

import com.github.tacticaldevmc.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.config.Config;
import com.github.tacticaldevmc.remotecore.config.interfaces.IConf;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class MessagesHandler implements IConf {

    private static MessagesHandler handler;

    public static MessagesHandler getHandler() {
        return handler == null ? new MessagesHandler(RemoteCoreSpigot.getInstance()) : handler;
    }

    private final Config messages_nl;
    private final Config messages_en;
    private final RemoteCoreSpigot remotecore;

    public MessagesHandler(RemoteCoreSpigot remotecore) {
        this.remotecore = remotecore;

        messages_nl = new Config("messages/messages_nl");
        messages_en = new Config("messages/messages_en");
        reload();
    }

    public String getString(String path) {
        if (remotecore.getSettings().getLang().equals("nl")) {
            return messages_nl.getString(path);
        } else if (remotecore.getSettings().getLang().equals("en")) {
            return messages_en.getString(path);
        }
        return null;
    }

    @Override
    public Config getConfig() {
        if (remotecore.getSettings().getLang().equals("nl")) {
            return messages_nl;
        } else if (remotecore.getSettings().getLang().equals("en")) {
            return messages_en;
        }
        return null;
    }

    @Override
    public void reload() {
        messages_nl.reload();
        messages_en.reload();
    }

    @Override
    public String getName() {
        if (remotecore.getSettings().getLang().equals("nl")) {
            return messages_nl.getFile().getName();
        } else if (remotecore.getSettings().getLang().equals("en")) {
            return messages_en.getFile().getName();
        }
        return "Unknown Message File";
    }

    @Override
    public String filePath() {
        return null;
    }
}
