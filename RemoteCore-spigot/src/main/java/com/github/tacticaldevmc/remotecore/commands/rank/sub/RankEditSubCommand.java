package com.github.tacticaldevmc.remotecore.commands.rank.sub;

import com.github.tacticaldevmc.remotecore.menus.EditRankMenu;
import com.github.tacticaldevmc.remotecore.messages.enums.RemoteMessages;
import com.github.tacticaldevmc.remotecore.player.RemotePlayer;
import com.github.tacticaldevmc.remotecore.player.ranks.RankData;
import com.github.tacticaldevmc.remotecore.settings.interfaces.ISettings;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class RankEditSubCommand {

    public boolean run(ISettings settings, RemotePlayer user, String[] args) {

        if (args.length == 1) {
            user.sendMessage(RemoteMessages.RANK_EDIT_ARGS);
            return false;
        }

        String name = args[1];

        RankData rankData = new RankData(name);

        if (!rankData.exists()) {
            user.sendMessage(RemoteMessages.RANK_NOT_EXISTS, name);
            return false;
        }

        user.sendMessage(RemoteMessages.RANK_EDIT_MENU_OPENED, name);
        EditRankMenu editMenu = new EditRankMenu(user.getBase(), rankData);

        editMenu.open();
        return false;
    }
}
