package com.github.tacticaldevmc.remotecore.commands.rank.sub;

import com.github.tacticaldevmc.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.messages.enums.RemoteMessages;
import com.github.tacticaldevmc.remotecore.player.RemotePlayer;
import com.github.tacticaldevmc.remotecore.player.ranks.RankData;
import com.github.tacticaldevmc.remotecore.settings.interfaces.ISettings;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class RankInfoSubCommand {

    public boolean run(ISettings settings, RemotePlayer user, String[] args) {

        if (args.length == 1) {
            user.sendMessage(RemoteMessages.RANK_INFO_ARGS);
            return false;
        }

        String name = args[1];

        RankData rank = new RankData(name);
        String prefix = RemoteCoreSpigot.getInstance().getRemotePrefix();

        if (!rank.exists()) {
            user.sendMessage(RemoteMessages.RANK_NOT_EXISTS, name);
            return false;
        }

        user.sendMessage(prefix + "&b&l> Command Info: &f/rank info");
        user.sendMessage(prefix + "&f- &3Rank: &f" + rank.getName().join());
        user.sendMessage(prefix + "&f- &3Total Ranks: &f" + rank.getRegisteredRanks());
        user.sendMessage(prefix + "&f- &aContextual Data:");
        user.sendMessage(prefix + "     &3Prefix: &f" + rank.getPrefix().join());
        user.sendMessage(prefix + "     &3Color: &f" + rank.getColor().join());
//        user.sendMessage("     &3DiscordID: &f" + rank.getDiscordID().join().equals("0") ? "&c&lUnknown" : rank.getDiscordID().join());
        user.sendMessage(rank.getDiscordID().join().equals("0") ? prefix + "     &3DiscordID: : &c&lUnknown" : prefix + "     &3DiscordID: &f" + rank.getDiscordID().join());
        user.sendMessage(prefix + "     &3Created At: &f" + rank.getCreatedAt().join());

        return false;
    }
}
