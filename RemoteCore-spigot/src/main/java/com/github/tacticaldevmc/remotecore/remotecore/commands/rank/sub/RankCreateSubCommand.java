package com.github.tacticaldevmc.remotecore.remotecore.commands.rank.sub;

import com.github.tacticaldevmc.remotecore.remotecore.bot.Main;
import com.github.tacticaldevmc.remotecore.remotecore.messages.enums.RemoteMessages;
import com.github.tacticaldevmc.remotecore.remotecore.player.RemotePlayer;
import com.github.tacticaldevmc.remotecore.remotecore.player.ranks.RankData;
import com.github.tacticaldevmc.remotecore.remotecore.settings.interfaces.ISettings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.requests.restaction.RoleAction;

import java.awt.*;
import java.time.Instant;

import static com.github.tacticaldevmc.remotecore.remotecore.bot.Main.getRandomColor;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class RankCreateSubCommand {

    public boolean run(ISettings settings, RemotePlayer user, String[] args) {

        if (args.length == 1) {
            user.sendMessage(RemoteMessages.RANK_CREATE_ARGS);
            return false;
        } else if (args.length == 2) {
            user.sendMessage(RemoteMessages.RANK_CREATE_ARGS);
            return false;
        } else if (args.length == 3) {
            user.sendMessage(RemoteMessages.RANK_CREATE_ARGS);
            return false;
        }

        String name = args[1];
        String prefix = args[2];
        String color = args[3];

        if (!color.startsWith("#")) {
            user.sendMessage(RemoteMessages.RANK_CREATE_COLOR_MUST_START_WITH);
            return false;
        }

        RankData rankData = new RankData(name);

        if (rankData.exists()) {
            user.sendMessage(RemoteMessages.RANK_ALREADY_EXISTS, name);
            return false;
        }

        rankData.createRank(prefix, color);
        user.sendMessage(RemoteMessages.RANK_CREATE_CREATED, name);

        // TODO: Sync rank to discord
        if (settings.minecraftDiscord()) {
            RoleAction roleAction = Main.getJda().getGuildById(settings.getGuildID()).createRole().setName(name).setColor(Color.decode(color)).setMentionable(true);
            Role role = roleAction.complete();

            rankData.setDiscordID(role.getId());

            EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Create Role");

            embedBuilder.setColor(getRandomColor());
            embedBuilder.setFooter("RemoteCore Plugin", null);
            embedBuilder.setTimestamp(Instant.now());

            embedBuilder.addField("Name", name, false);
            embedBuilder.addField("Color", color, false);
            embedBuilder.addField("In-game Prefix", prefix, false);
            embedBuilder.addField("Created at", rankData.getCreatedAt().join(), false);

            Main.getJda().getGuildById(settings.getGuildID()).getTextChannelById(settings.getLogChannelID()).sendMessage(embedBuilder.build()).queue();
        }
        return false;
    }
}
