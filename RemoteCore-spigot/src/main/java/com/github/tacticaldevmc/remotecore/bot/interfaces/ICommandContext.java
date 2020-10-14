package com.github.tacticaldevmc.remotecore.bot.interfaces;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.sharding.ShardManager;

public interface ICommandContext {

    /**
     * Returns the {@link Guild} for the current command/event
     *
     * @return the {@link Guild} for this command/event
     */
    Guild getGuild();

    /**
     * Returns the {@link GuildMessageReceivedEvent message event} that was received for this instance
     *
     * @return the {@link GuildMessageReceivedEvent message event} that was received for this instance
     */
    GuildMessageReceivedEvent getEvent();

    /**
     * Returns the {@link TextChannel channel} that the message for this event was send in
     *
     * @return the {@link TextChannel channel} that the message for this event was send in
     */
    default TextChannel getChannel() {
        return this.getEvent().getChannel();
    }

    /**
     * Returns the {@link Message message} that triggered this event
     *
     * @return the {@link Message message} that triggered this event
     */
    default Message getMessage() {
        return this.getEvent().getMessage();
    }

    /**
     * Returns the {@link User author} of the message as user
     *
     * @return the {@link User author} of the message as user
     */
    default User getAuthor() {
        return this.getEvent().getAuthor();
    }
    /**
     * Returns the {@link Member author} of the message as member
     *
     * @return the {@link Member author} of the message as member
     */
    default Member getMember() {
        return this.getEvent().getMember();
    }

    /**
     * Returns the current {@link JDA jda} instance
     *
     * @return the current {@link JDA jda} instance
     */
    default JDA getJDA() {
        return this.getEvent().getJDA();
    }

    /**
     * Returns the current {@link ShardManager} instance
     *
     * @return the current {@link ShardManager} instance
     */
    default ShardManager getShardManager() {
        return this.getJDA().getShardManager();
    }

    /**
     * Returns the {@link User user} for the currently logged in account
     *
     * @return the {@link User user} for the currently logged in account
     */
    default User getSelfUser() {
        return this.getJDA().getSelfUser();
    }

    /**
     * Returns the {@link Member member} in the guild for the currently logged in account
     *
     * @return the {@link Member member} in the guild for the currently logged in account
     */
    default Member getSelfMember() {
        return this.getGuild().getSelfMember();
    }

}