package com.github.tacticaldevmc.remotecore.remotecore.bot.commands.main;

import com.github.tacticaldevmc.remotecore.remotecore.bot.interfaces.ICommand;
import com.github.tacticaldevmc.remotecore.remotecore.bot.utils.Constants;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class PingCommand implements ICommand {

    @Override
    public void handle(String[] args, GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("Ping! ⚾").queue(m -> {
            long ping = event.getMessage().getTimeCreated().until(m.getTimeCreated(), ChronoUnit.MILLIS);
//            m.editMessage("").queueAfter(ping, TimeUnit.SECONDS);
            m.editMessage("Pong! \uD83C\uDFD3 " + ping + "ms | Websocket: " + event.getJDA().getGatewayPing() + "ms").queueAfter(3L, TimeUnit.SECONDS);
        });
    }

    @Override
    public String getHelp() {
        return "Get the ping of the bot!";
    }

    @Override
    public String getInvoke() {
        return "ping";
    }

    @Override
    public String getUsage() {
        return Constants.PREFIX + getInvoke();
    }
}
