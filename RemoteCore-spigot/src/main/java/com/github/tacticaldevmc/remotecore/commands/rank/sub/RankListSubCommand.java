package com.github.tacticaldevmc.remotecore.commands.rank.sub;

import com.github.tacticaldevmc.remotecore.messages.enums.RemoteMessages;
import com.github.tacticaldevmc.remotecore.player.RemotePlayer;
import com.github.tacticaldevmc.remotecore.player.ranks.RankData;
import com.github.tacticaldevmc.remotecore.menus.RankListMenu;
import com.github.tacticaldevmc.remotecore.settings.interfaces.ISettings;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class RankListSubCommand {

    int ranksPerPage = 20;

    public boolean run(ISettings settings, RemotePlayer user, String[] args) {

        if (RankData.getRanks().size() == 0) {
            user.sendMessage(RemoteMessages.RANK_LIST_NO_RANKS);
            return false;
        }

        RankListMenu listMenu = new RankListMenu(user.getBase());

        listMenu.open();

        return false;
    }
}
