package com.github.tacticaldevmc.remotecore.commands.base;

import com.github.tacticaldevmc.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.commands.main.RemoteCoreCommand;
import com.github.tacticaldevmc.remotecore.commands.rank.RankCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

public class CommandModule {

    private static CommandModule instance;
    private final ArrayList<CoreCommand> command = new ArrayList<CoreCommand>();

    public static CommandModule getInstance() {
        return instance == null ? instance = new CommandModule() : instance;
    }

    public static void setInstance(CommandModule instance) {
        CommandModule.instance = instance;
    }

    public ArrayList<CoreCommand> getCommand() {
        return command;
    }

    public CommandModule() {
        setInstance(this);
    }

    public void loadCommands(CoreCommand... coreCommands) {
        Arrays.stream(coreCommands).forEach(CoreCommand::registerCommand);
    }

    public void loadCommands() {
        loadMainCommands();
//        loadPluginManagerCommands();
//        loadTestCommands();
//        loadTeleportCommands();
//        loadEconomyCommands();

        if (RemoteCoreSpigot.getInstance().getSettings().isDebug()) {
            RemoteCoreSpigot.getInstance().getLogger().log(Level.INFO, "Commands registered! Size {0}!", command.size());
        } else {
            RemoteCoreSpigot.getInstance().getLogger().log(Level.INFO, "Commands registered!");
        }
    }

    public void loadMainCommands() {
        new RemoteCoreCommand("remotecore", "remotecore.command", "RemoteCore Main command", "<reload, user>", true, false).registerCommand();
        new RankCommand("rank", "remotecore.rank", "RemoteCore Rank command", "<create>", true, false).registerCommand();
    }

    public void loadPluginManagerCommands() {
    }

    public void loadTestCommands() {
    }

    public void loadTeleportCommands() {
    }

    public void loadEconomyCommands() {
    }

}