package com.github.tacticaldevmc.remotecore.remotecore.bot;

import com.github.tacticaldevmc.remotecore.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.remotecore.bot.commands.base.CommandManager;
import com.github.tacticaldevmc.remotecore.remotecore.bot.utils.Listener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class Main {

    private static JDA jda;
    public Guild guild;

    private static final Random random = new Random();

    public static void main(String[] args) throws IOException {

        CommandManager commandManager = new CommandManager(random);
        Listener listener = new Listener(commandManager);
        Logger logger = LoggerFactory.getLogger(Main.class);

        try {
            logger.info("Booting");
            jda = JDABuilder.createDefault(RemoteCoreSpigot.getInstance().getSettings().getToken())
                    .addEventListeners(listener)
                    .build();
            jda.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
            jda.getPresence().setActivity(Activity.listening("RemoteCore Plugin!"));
            logger.info("Running");
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public Main() {
        try {
            main(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JDA getJda() {
        return jda;
    }

    public static Color getRandomColor() {
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();

        return new Color(r, g, b);
    }

}
