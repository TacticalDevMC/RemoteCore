package com.github.tacticaldevmc.remotecore.listeners;

import com.github.tacticaldevmc.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.listeners.bukkit.PlayerChatListener;
import com.github.tacticaldevmc.remotecore.listeners.bukkit.PlayerConnectionListener;
import com.github.tacticaldevmc.remotecore.utils.menu.MenuManager;
import com.github.tacticaldevmc.remotecore.utils.menu.util.InventoryCheck;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.Arrays;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class ListenerManager {

    public void loadListeners(Listener... listener) {
        Arrays.asList(listener).forEach(l -> {
            Bukkit.getPluginManager().registerEvents(l, RemoteCoreSpigot.getInstance());
        });
    }

    private void loadMenuListeners() {
        loadListeners(new MenuManager(), new InventoryCheck());
    }

    private void loadConnectionListeners() {
        loadListeners(new PlayerConnectionListener());
    }

    private void loadPlayerListeners() {
        loadListeners(new PlayerChatListener());
    }

    public void load() {
        loadMenuListeners();
        loadConnectionListeners();
        loadPlayerListeners();
    }

}
