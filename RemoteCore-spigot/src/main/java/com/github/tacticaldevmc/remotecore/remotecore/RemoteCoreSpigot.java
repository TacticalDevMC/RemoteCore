package com.github.tacticaldevmc.remotecore.remotecore;

import com.github.tacticaldevmc.remotecore.remotecore.bot.Main;
import com.github.tacticaldevmc.remotecore.remotecore.commands.base.CommandModule;
import com.github.tacticaldevmc.remotecore.remotecore.config.interfaces.IConf;
import com.github.tacticaldevmc.remotecore.remotecore.listeners.ListenerManager;
import com.github.tacticaldevmc.remotecore.remotecore.logs.LogHandler;
import com.github.tacticaldevmc.remotecore.remotecore.messages.MessagesHandler;
import com.github.tacticaldevmc.remotecore.remotecore.player.ranks.RankData;
import com.github.tacticaldevmc.remotecore.remotecore.settings.Settings;
import com.github.tacticaldevmc.remotecore.remotecore.settings.interfaces.ISettings;
import com.github.tacticaldevmc.remotecore.remotecore.storage.StorageManager;
import com.github.tacticaldevmc.remotecore.remotecore.utils.Prefix;
import com.github.tacticaldevmc.remotecore.remotecore.utils.mode.BuildMode;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.github.tacticaldevmc.remotecore.remotecore.utils.Colors.format;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public final class RemoteCoreSpigot extends JavaPlugin implements ICore {

    @Getter
    private static RemoteCoreSpigot instance;

    private String version = this.getDescription().getVersion();
    private String authors = this.getDescription().getAuthors().toString().replace("[", "").replace("]", "");

    List<String> pluginsList = Arrays.asList("RemoteCore");

    private List<IConf> configList = new ArrayList<>();
    private String vers = null;
    private ISettings settings;
    private MessagesHandler messageHandler;
    private StorageManager storageManager;

    public String getRemotePrefix() {
        return format("&7[&b&lR&3&lM&7] ");
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        // Check for the versions
        checkForVersion();
        if (version.endsWith(BuildMode.DEV.getMode())) {
            LogHandler.getHandler().logMessage(Prefix.WARNING, "This build of RemoteCore is an Development Build. In this build can you some get errors, etc.");
            vers = "Development";
        } else if (version.endsWith(BuildMode.BETA.getMode())) {
            LogHandler.getHandler().logMessage(Prefix.WARNING, "This build of RemoteCore is an Beta Build. In this version will test our Beta Team the plugin for errors, etc.");
            vers = "Beta";
        } else if (version.endsWith(BuildMode.OUTDATED.getMode())) {
            LogHandler.getHandler().logMessage(Prefix.WARNING, "This build of RemoteCore is outdated. Please download the latest version.");
            vers = "Outdated";
        } else if (version.endsWith(BuildMode.SNAPSHOT.getMode())) {
            LogHandler.getHandler().logMessage(Prefix.WARNING, "This build of RemoteCore is an SNAPSHOT build.");
            vers = "Snapshot";
        }
        LogHandler.getHandler().logMessage(Prefix.INFO, "Plugin is loading! Author(s): " + authors + " Version:" + version);

        // LOAD PLUGIN HERE
        settings = new Settings();
        configList.add(settings);
        messageHandler = new MessagesHandler(this);
        configList.add(messageHandler);

        storageManager = new StorageManager();
        storageManager.manager().start();

        // Load commands
        CommandModule commandModule = new CommandModule();
        commandModule.loadCommands();

        // Load listeners
        ListenerManager listenerManager = new ListenerManager();
        listenerManager.loadMenuListeners();
        listenerManager.loadConnectionListeners();

        if (settings.minecraftDiscord()) {
            try {
                Main.main(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Plugin enabled
        LogHandler.getHandler().logMessage(Prefix.INFO, "The plugin has been enabled! You are running a " + vers + " build!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
        LogHandler.getHandler().logMessage(Prefix.INFO, "Plugin is unloading! Author(s): " + authors + " Version:" + version);

        // UNLOAD PLUGIN HERE
        storageManager.manager().stop();

        // Unload bot
        Main.getJda().shutdown();

        // Plugin disabled
        LogHandler.getHandler().logMessage(Prefix.INFO, "The plugin has been disabled! You are running version " + version + "!");
    }

    public boolean checkForVersion() {
        pluginsList.forEach(s -> {
            for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                if (plugin.getName().startsWith(s)) {
                    if (!plugin.getDescription().getVersion().equals(this.getDescription().getVersion())) {
                        LogHandler.getHandler().logMessage(Prefix.ERROR, "§4Version mismatch! Please update all RemoteCore jars to the same version.");
                    }
                }
                return;
            }
        });
        return true;
    }

    @Override
    public ISettings getSettings() {
        return settings;
    }

    @Override
    public MessagesHandler getMessageHandler() {
        return messageHandler;
    }

    @Override
    public StorageManager getStorageManager() {
        return storageManager;
    }

    @Override
    public BukkitScheduler getScheduler() {
        return this.getServer().getScheduler();
    }

    @Override
    public BukkitTask runTaskAsynchronously(final Runnable run) {
        return this.getScheduler().runTaskAsynchronously(this, run);
    }

    @Override
    public BukkitTask runTaskLaterAsynchronously(final Runnable run, final long delay) {
        return this.getScheduler().runTaskLaterAsynchronously(this, run, delay);
    }

    @Override
    public BukkitTask runTaskTimerAsynchronously(final Runnable run, final long delay, final long period) {
        return this.getScheduler().runTaskTimerAsynchronously(this, run, delay, period);
    }

    @Override
    public int scheduleSyncDelayedTask(final Runnable run) {
        return this.getScheduler().scheduleSyncDelayedTask(this, run);
    }

    @Override
    public int scheduleSyncDelayedTask(final Runnable run, final long delay) {
        return this.getScheduler().scheduleSyncDelayedTask(this, run, delay);
    }

    @Override
    public int scheduleSyncRepeatingTask(final Runnable run, final long delay, final long period) {
        return this.getScheduler().scheduleSyncRepeatingTask(this, run, delay, period);
    }

    @Override
    public void addReloadListener(IConf listener) {
        listener.reload();
    }

    @Override
    public void reload() {
        for (IConf conf : configList) {
            conf.reload();
        }
        storageManager.manager().restart();
    }
}
