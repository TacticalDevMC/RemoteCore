package com.github.tacticaldevmc.remotecore.commands.rank.sub;

import com.github.tacticaldevmc.remotecore.bot.Main;
import com.github.tacticaldevmc.remotecore.messages.enums.RemoteMessages;
import com.github.tacticaldevmc.remotecore.player.RemotePlayer;
import com.github.tacticaldevmc.remotecore.player.ranks.RankData;
import com.github.tacticaldevmc.remotecore.settings.interfaces.ISettings;
import net.dv8tion.jda.api.EmbedBuilder;

import java.time.Instant;

import static com.github.tacticaldevmc.remotecore.bot.Main.getRandomColor;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class RankDeleteSubCommand {

    public boolean run(ISettings settings, RemotePlayer user, String[] args) {

        if (args.length == 1) {
            user.sendMessage(RemoteMessages.RANK_DELETE_ARGS);
            return false;
        }

        String name = args[1];

        RankData rankData = new RankData(name);

        if (!rankData.exists()) {
            user.sendMessage(RemoteMessages.RANK_NOT_EXISTS, name);
            return false;
        }

        String created_at = rankData.getCreatedAt().join();
        String discord_id = rankData.getDiscordID().join();

        rankData.deleteRank();
        user.sendMessage(RemoteMessages.RANK_DELETE_DELETED, name);

        if (settings.minecraftDiscord()) {
            if (rankData.getDiscordID().join().equals("0")) return false;

            Main.getJda().getGuildById(settings.getGuildID()).getRoleById(discord_id).delete().queue();

            EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Delete Role");

            embedBuilder.setColor(getRandomColor());
            embedBuilder.setFooter("RemoteCore Plugin", null);
            embedBuilder.setTimestamp(Instant.now());

            embedBuilder.addField("Name", name, false);
            embedBuilder.addField(settings.getLang().equals("nl") ? "Role was gemaakt op" : settings.getLang().equals("en") ? "Role was created on" : "Unknown lang", created_at, false);

            Main.getJda().getGuildById(settings.getGuildID()).getTextChannelById(settings.getLogChannelID()).sendMessage(embedBuilder.build()).queue();
        }
        return false;
    }
}
