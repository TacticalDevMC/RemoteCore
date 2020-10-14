package com.github.tacticaldevmc.remotecorecommon.data;

import com.github.tacticaldevmc.remotecore.player.RemoteOfflinePlayer;
import com.github.tacticaldevmc.remotecore.player.RemotePlayer;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class PlayerData {

    private RemotePlayer remotePlayer;
    private RemoteOfflinePlayer remoteOfflinePlayer;

    public RemotePlayer getRemotePlayer(Player player) {
        return remotePlayer == null ? new RemotePlayer(player) : remotePlayer;
    }

    public RemoteOfflinePlayer getRemoteOfflinePlayer(OfflinePlayer offlinePlayer) {
        return remoteOfflinePlayer == null ? new RemoteOfflinePlayer(offlinePlayer) : remoteOfflinePlayer;
    }

}
