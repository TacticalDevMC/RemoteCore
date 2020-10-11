package com.github.tacticaldevmc.remotecore.remotecore.commands.main.sub.user;

import com.github.tacticaldevmc.remotecore.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.remotecore.messages.enums.RemoteMessages;
import com.github.tacticaldevmc.remotecore.remotecore.player.RemoteOfflinePlayer;
import com.github.tacticaldevmc.remotecore.remotecore.player.RemotePlayer;
import com.github.tacticaldevmc.remotecore.remotecore.player.ranks.RankData;
import com.github.tacticaldevmc.remotecore.remotecore.settings.interfaces.ISettings;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class UserRankSubCommand {

    String prefix = RemoteCoreSpigot.getInstance().getRemotePrefix();

    public boolean run(ISettings settings, RemotePlayer user, String[] args) {

        String name = args[3];

        RankData rank = new RankData(name);

        if (!rank.exists()) {
            user.sendMessage(RemoteMessages.RANK_NOT_EXISTS, name);
            return false;
        }

        Player target = Bukkit.getPlayer(args[1]);
        RemotePlayer base = new RemotePlayer(target);

        if (target == null) {
            OfflinePlayer targetOffline = Bukkit.getOfflinePlayer(args[1]);
            RemoteOfflinePlayer baseOffline = new RemoteOfflinePlayer(targetOffline);

            baseOffline.setRank(rank.getName().join());
            user.sendMessage(RemoteMessages.MAIN_USER_RANK_SET, targetOffline.getName());
            return false;
        }

        base.setRank(rank.getName().join());
        user.sendMessage(RemoteMessages.MAIN_USER_RANK_SET, target.getName());
        return false;
    }
}
