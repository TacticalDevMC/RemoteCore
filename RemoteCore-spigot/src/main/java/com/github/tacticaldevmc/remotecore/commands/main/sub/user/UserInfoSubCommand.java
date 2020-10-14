package com.github.tacticaldevmc.remotecore.commands.main.sub.user;

import com.github.tacticaldevmc.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.messages.MessagesHandler;
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

public class UserInfoSubCommand {

    String prefix = RemoteCoreSpigot.getInstance().getRemotePrefix();

    public boolean run(ISettings settings, RemotePlayer user, String[] args) {

        Player target = Bukkit.getPlayer(args[1]);
        RemotePlayer base = new RemotePlayer(target);

        if (target == null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
            RemoteOfflinePlayer baseOffline = new RemoteOfflinePlayer(offlinePlayer);

            if (!baseOffline.exists()) {
                user.sendMessage(RemoteMessages.PLAYER_NOT_IN_DATABASE, args[1]);
                return false;
            }

            String status = offlinePlayer.isOnline() ? MessagesHandler.getHandler().getString("online") : MessagesHandler.getHandler().getString("offline");
            String mojang = offlinePlayer.getUniqueId().version() == 4 ? "&2mojang" : "&8offline";
            boolean active = baseOffline.isActive().join();
            String activePlayer = active ? "&2active" : "&8not active";
            String rankNone = "&bNone";
            String rank = baseOffline.getRank().join().equals("Geen") ? rankNone : baseOffline.getRank().join();

            user.sendMessage(prefix + "&b&l> User Info: &f" + offlinePlayer.getName());
            user.sendMessage(prefix + "&f- &3UUID: &f" + offlinePlayer.getUniqueId());
            user.sendMessage(prefix + "    &7(type: " + mojang + "&7)");
            user.sendMessage(prefix + "&f- &3Status: " + status);
            user.sendMessage(prefix + "&f - &aContextual Data: &7(mode: " + activePlayer + "&7)");
            user.sendMessage(prefix + "       &3Rank: " + rank);
            return false;
        }

        if (!base.exists()) {
            user.sendMessage(RemoteMessages.PLAYER_NOT_IN_DATABASE, args[1]);
            return false;
        }

        String status = target.isOnline() ? MessagesHandler.getHandler().getString("online") : MessagesHandler.getHandler().getString("offline");
        String mojang = target.getUniqueId().version() == 4 ? "&2mojang" : "&8offline";
        boolean active = base.isActive().join();
        String activePlayer = active ? "&2active" : "&8not active";
        String rankNone = "&bNone";
        String rank = base.getRank().join().equals("Geen") ? rankNone : base.getRank().join();

        user.sendMessage(prefix + "&b&l> User Info: &f" + target.getName());
        user.sendMessage(prefix + "&f- &3UUID: &f" + target.getUniqueId());
        user.sendMessage(prefix + "    &7(type: " + mojang + "&7)");
        user.sendMessage(prefix + "&f- &3Status: " + status);
        user.sendMessage(prefix + "&f - &aContextual Data: &7(mode: " + activePlayer + "&7)");
        user.sendMessage(prefix + "       &3Rank: " + rank);

        return false;
    }
}
