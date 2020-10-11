package com.github.tacticaldevmc.remotecore.remotecore.bot.commands.base;

import com.github.tacticaldevmc.remotecore.remotecore.bot.commands.main.PingCommand;
import com.github.tacticaldevmc.remotecore.remotecore.bot.commands.main.TrelloCommand;
import com.github.tacticaldevmc.remotecore.remotecore.bot.interfaces.ICommand;
import com.github.tacticaldevmc.remotecore.remotecore.bot.utils.Constants;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {

    private final Map<String, ICommand> commands = new HashMap<>();

    public CommandManager(Random random) {
        System.out.println("[Bot] Loading commands");

        // Commands
//        addCommand(new TrelloCommand());

        // - Default
        addCommand(new PingCommand());
    }

    private void addCommand(ICommand command) {
        if (!commands.containsKey(command.getInvoke())) {
            commands.put(command.getInvoke(), command);
        }
    }

    public Collection<ICommand> getCommands() {
        return commands.values();
    }

    public ICommand getCommand(@NotNull String name) {
        return commands.get(name);
    }

    public void handleCommand(GuildMessageReceivedEvent event) {
        final String prefix = Constants.PREFIXES.get(event.getGuild().getIdLong());

        final String[] args = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(prefix), "").split("\\s+");
        final String invoke = args[0].toLowerCase();

        if (commands.containsKey(invoke)) {

//            if (event.getChannel().getIdLong() == 755380962308653086L || event.getChannel().getIdLong() == 755371432820604938L) {
            event.getChannel().sendTyping().queue();
            commands.get(invoke).handle(args, event);
//            } else {
//                EmbedBuilder channelErrorEmbed = new EmbedBuilder().setTitle("U mag in deze channel geen commands van de bot uitvoeren");
//
//                channelErrorEmbed.setColor(Color.RED);
//                event.getChannel().sendMessage(channelErrorEmbed.build()).queue();
//            }
        }
    }
}