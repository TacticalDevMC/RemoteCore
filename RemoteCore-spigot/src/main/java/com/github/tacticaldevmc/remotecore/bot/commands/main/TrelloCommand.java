package com.github.tacticaldevmc.remotecore.bot.commands.main;

import com.github.tacticaldevmc.remotecore.bot.interfaces.ICommand;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class TrelloCommand implements ICommand {

    @Override
    public void handle(String[] args, GuildMessageReceivedEvent event) {
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get("https://api.trello.com/GET/1/cards/tRdDOtbW")
                    .header("Accept", "application/json")
                    .queryString("key", "1e0c2ca915b55f5044991016e7456627")
                    .queryString("token", "a5e366fa4cbcabf743bc857957455d86751bbf703953c597b554b682c8027129")
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        event.getChannel().sendMessage("" + response.getBody()).queue();
    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public String getInvoke() {
        return "trello";
    }

    @Override
    public String getUsage() {
        return null;
    }
}
