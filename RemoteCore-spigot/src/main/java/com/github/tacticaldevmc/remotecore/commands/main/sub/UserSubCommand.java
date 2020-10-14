package com.github.tacticaldevmc.remotecore.commands.main.sub;

import com.github.tacticaldevmc.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.commands.main.sub.user.UserInfoSubCommand;
import com.github.tacticaldevmc.remotecore.commands.main.sub.user.UserRankSubCommand;
import com.github.tacticaldevmc.remotecore.messages.enums.RemoteMessages;
import com.github.tacticaldevmc.remotecore.player.RemoteOfflinePlayer;
import com.github.tacticaldevmc.remotecore.player.RemotePlayer;
import com.github.tacticaldevmc.remotecore.settings.interfaces.ISettings;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class UserSubCommand {

    String prefix = RemoteCoreSpigot.getInstance().getRemotePrefix();

    UserInfoSubCommand subInfo = new UserInfoSubCommand();
    UserRankSubCommand subRank = new UserRankSubCommand();

    public boolean run(ISettings settings, RemotePlayer user, String[] args) {

        if (args.length == 1) {
            user.sendMessage(RemoteMessages.MAIN_USER_ARGS);
            return false;
        }

        switch (args.length) {
            case 2: {
                Player target = Bukkit.getPlayer(args[1]);
                RemotePlayer targetBase = new RemotePlayer(target);

                if (target == null) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                    RemoteOfflinePlayer baseOffline = new RemoteOfflinePlayer(offlinePlayer);

                    if (!baseOffline.exists()) {
                        user.sendMessage(RemoteMessages.PLAYER_NOT_IN_DATABASE, args[1]);
                        return false;
                    }

                    String mojang = offlinePlayer.getUniqueId().version() == 4 ? "&2mojang" : "&8offline";

                    user.sendMessage(prefix + "&b&l> Command Info: &f/remotecore user");
                    user.sendMessage(prefix + "&f &3Player: &f" + offlinePlayer.getName());
                    user.sendMessage(prefix + "&f &3UUID: &f" + offlinePlayer.getUniqueId());
                    user.sendMessage(prefix + "    &7(type: " + mojang + "&7)");
                    user.sendMessage(prefix + "  &aSub-Commands:");
                    user.sendMessage(prefix + "     &3info");
                    user.sendMessage(prefix + "     &3rank <rank>");
                    user.sendMessage(prefix + "     &3prefix <prefix>");
                    user.sendMessage(prefix + "     &3suffix <suffix>");
                    return false;
                }

                if (!targetBase.exists()) {
                    user.sendMessage(RemoteMessages.PLAYER_NOT_IN_DATABASE, args[1]);
                    return false;
                }

                String mojang = target.getUniqueId().version() == 4 ? "&2mojang" : "&8offline";

                user.sendMessage(prefix + "&b&l> Command Info: &f/remotecore user");
                user.sendMessage(prefix + "&f- &3Player: &f" + target.getName());
                user.sendMessage(prefix + "&f- &3UUID: &f" + target.getUniqueId());
                user.sendMessage(prefix + "    &7(type: " + mojang + "&7)");
                user.sendMessage(prefix + "  &aSub-Commands:");
                user.sendMessage(prefix + "     &3info");
                user.sendMessage(prefix + "     &3rank <rank>");
                user.sendMessage(prefix + "     &3prefix <prefix>");
                user.sendMessage(prefix + "     &3suffix <suffix>");
                break;
            }
            case 4: {
                switch (args[2]) {
                    case "info":
                        subInfo.run(settings, user, args);
                        break;
                    case "rank":
                        subRank.run(settings, user, args);
                        break;
                    default:
                        user.sendMessage(prefix + "&b&l> Command Info: &f/remotecore user <player>");
                        user.sendMessage(prefix + "  &aSub-Commands:");
                        user.sendMessage(prefix + "     &3info");
                        user.sendMessage(prefix + "     &3rank <rank>");
                        user.sendMessage(prefix + "     &3prefix <prefix>");
                        user.sendMessage(prefix + "     &3suffix <suffix>");
                        break;
                }
            }
        }
        return false;
    }
}
