package com.github.tacticaldevmc.remotecorecommon.test;

import com.github.tacticaldevmc.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.logs.LogHandler;
import com.github.tacticaldevmc.remotecorecommon.RemoteAPI;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class InitAPI {

    private RemoteAPI api;

    public void initAPI() {
        if (api.isInit()) {
            LogHandler.getHandler().logMessage("The api is already initialized.");
            return;
        }

        api = new RemoteAPI(RemoteCoreSpigot.getInstance());

        api.init();
    }

    public RemoteAPI getAPI() {
        if (!api.isInit()) {
            LogHandler.getHandler().logMessage("The api is'nt initialized.");
            return null;
        }
        return api;
    }
}
