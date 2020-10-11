package com.github.tacticaldevmc.remotecorecommon;

import com.github.tacticaldevmc.remotecore.remotecore.player.RemoteOfflinePlayer;
import com.github.tacticaldevmc.remotecore.remotecore.player.RemotePlayer;
import com.github.tacticaldevmc.remotecorecommon.data.PlayerData;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class RemoteCommon {

    private Plugin plugin;
    private PlayerData playerData;

    public RemoteCommon(Plugin plugin) {
        this.plugin = plugin;
        this.playerData = new PlayerData();
    }

    public RemotePlayer getPlayer(Player player) {
        return playerData.getRemotePlayer(player);
    }

    public RemoteOfflinePlayer getPlayer(OfflinePlayer offlinePlayer) {
        return playerData.getRemoteOfflinePlayer(offlinePlayer);
    }

}
