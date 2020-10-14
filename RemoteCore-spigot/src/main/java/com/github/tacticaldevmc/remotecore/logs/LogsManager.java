package com.github.tacticaldevmc.remotecore.logs;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class LogsManager {

    public void createLog(String file, LogType logType) {
        if (logType == LogType.CONSOLE) {
            // TODO: Create logs van de console
        } else if (logType == LogType.ERRORS) {
            // TODO: Create logs van errors van RemoteCore
        } else if (logType == LogType.FILE) {
            // TODO: Create logs van de files
        } else if (logType == LogType.DATABASE) {
            // TODO: Create logs van de database
        }
    }

    private enum LogType {
        CONSOLE,
        ERRORS,
        FILE,
        DATABASE
    }

}
