package com.github.tacticaldevmc.remotecore.listeners.bukkit;

import com.github.tacticaldevmc.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.player.RemotePlayer;
import com.github.tacticaldevmc.remotecore.player.ranks.RankData;
import com.github.tacticaldevmc.remotecore.settings.interfaces.ISettings;
import com.github.tacticaldevmc.remotecore.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class PlayerChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        ISettings settings = RemoteCoreSpigot.getInstance().getSettings();
        RemotePlayer user = new RemotePlayer(player);
        RankData rankData = new RankData(user.getRank().join());

        String rank = user.getRank().join().equals("Geen") ? "§c§lNone" : rankData.getPrefix().join();
        String prefix = user.getPrefix().join().equals("Geen") ? "§c§lNone" : user.getPrefix().join();
        String suffix = user.getSuffix().join().equals("Geen") ? "§c§lNone" : user.getSuffix().join();

        String _FORMAT = settings.getChatFormat()
                .replace("<suffix>", suffix)
                .replace("<prefix>", prefix)
                .replace("<rank>", rank)
                .replace("<player>", player.getName())
                .replace("<message>", message);

        String FORMAT = _FORMAT.replace("%", "℅");
        event.setFormat(FORMAT);
    }
}

