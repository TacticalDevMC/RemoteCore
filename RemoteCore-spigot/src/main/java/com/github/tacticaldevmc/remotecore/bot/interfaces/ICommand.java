package com.github.tacticaldevmc.remotecore.bot.interfaces;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public interface ICommand {

    void handle(String[] args, GuildMessageReceivedEvent event);

    String getHelp();

    String getInvoke();

    String getUsage();

    default List<String> getAliases() {
        return Arrays.asList();
    }

}