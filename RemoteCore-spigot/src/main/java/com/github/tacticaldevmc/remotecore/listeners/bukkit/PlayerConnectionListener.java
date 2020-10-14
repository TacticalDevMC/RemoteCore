package com.github.tacticaldevmc.remotecore.listeners.bukkit;

import com.github.tacticaldevmc.remotecore.player.RemotePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class PlayerConnectionListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        RemotePlayer remotePlayer = new RemotePlayer(player);

        if (!remotePlayer.exists()) {
            remotePlayer.create();
        }

    }
}
