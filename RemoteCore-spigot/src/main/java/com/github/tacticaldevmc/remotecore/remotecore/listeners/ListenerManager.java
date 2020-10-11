package com.github.tacticaldevmc.remotecore.remotecore.listeners;

import com.github.tacticaldevmc.remotecore.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.remotecore.listeners.bukkit.PlayerConnectionListener;
import com.github.tacticaldevmc.remotecore.remotecore.menus.EditRankMenu;
import com.github.tacticaldevmc.remotecore.remotecore.utils.menu.MenuManager;
import com.github.tacticaldevmc.remotecore.remotecore.utils.menu.util.InventoryCheck;
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

    public void loadMenuListeners() {
        loadListeners(new MenuManager(), new InventoryCheck());
    }

    public void loadConnectionListeners() {
        loadListeners(new PlayerConnectionListener());
    }

}
