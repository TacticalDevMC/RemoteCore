package com.github.tacticaldevmc.remotecore.logs;

import com.github.tacticaldevmc.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.utils.Prefix;
import com.github.tacticaldevmc.remotecore.utils.Colors;
import org.bukkit.Bukkit;

import java.util.logging.Logger;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class LogHandler {

    private final RemoteCoreSpigot coreSpigot = RemoteCoreSpigot.getInstance();

    private static final Logger LOGGER = Logger.getLogger("RemoteCore");

    public static Logger getLogger() {
        return LOGGER == null ? Logger.getLogger("RemoteCore") : LOGGER;
    }

    private static LogHandler handler;

    public static LogHandler getHandler() {
        return handler == null ? new LogHandler() : handler;
    }

    public void logException(Prefix prefix, Exception e) {
        Bukkit.getConsoleSender().sendMessage("[RemoteCore] " + Colors.format(prefix.getPrefix()));
        LOGGER.warning("Discovered RemoteCore Exception!");
        LOGGER.warning("---------------------------");
        LOGGER.warning("Exception: " + e.toString());
        for (StackTraceElement s : e.getStackTrace()) {
            LOGGER.warning(s.getClassName() + " [" + s.getLineNumber() + "/" + s.getMethodName() + "] [" + s.getFileName() + "]");
        }
        LOGGER.warning("---------------------------");
    }

    public void logException(String prefix, Exception e) {
        Bukkit.getConsoleSender().sendMessage("[RemoteCore] " + prefix);
        LOGGER.warning("Discovered RemoteCore Exception!");
        LOGGER.warning("---------------------------");
        LOGGER.warning("Exception: " + e.toString());
        for (StackTraceElement s : e.getStackTrace()) {
            LOGGER.warning(s.getClassName() + " [" + s.getLineNumber() + "/" + s.getMethodName() + "] [" + s.getFileName() + "]");
        }
        LOGGER.warning("---------------------------");
    }

    public void logMessage(Prefix prefix, String message) {
        Bukkit.getConsoleSender().sendMessage("[RemoteCore] " + Colors.format(prefix.getPrefix()));
        LOGGER.warning("---------------------------");
        LOGGER.warning(message);
        LOGGER.warning("---------------------------");
    }

    public void logMessage(String message) {
        Bukkit.getConsoleSender().sendMessage("[RemoteCore] [Message]");
        LOGGER.warning("---------------------------");
        LOGGER.warning(message);
        LOGGER.warning("---------------------------");
    }
}
