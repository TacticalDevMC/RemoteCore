package com.github.tacticaldevmc.remotecore.remotecore.commands.rank;

import com.github.tacticaldevmc.remotecore.remotecore.commands.base.CoreCommand;
import com.github.tacticaldevmc.remotecore.remotecore.commands.base.SubCommand;
import com.github.tacticaldevmc.remotecore.remotecore.commands.rank.sub.*;
import com.github.tacticaldevmc.remotecore.remotecore.player.ranks.RankData;
import com.github.tacticaldevmc.remotecore.remotecore.utils.exception.CoreException;
import com.github.tacticaldevmc.remotecore.remotecore.utils.inventory.ItemBuilder;
import com.github.tacticaldevmc.remotecore.remotecore.utils.menu.MenuCore;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class RankCommand extends CoreCommand {

    public RankCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    RankCreateSubCommand subCreate = new RankCreateSubCommand();
    RankDeleteSubCommand subDelete = new RankDeleteSubCommand();
    RankEditSubCommand subEdit = new RankEditSubCommand();
    RankInfoSubCommand subInfo = new RankInfoSubCommand();
//    RankListSubCommand subList = new RankListSubCommand();

    @Override
    public void init() {
        this.registerSub(new SubCommand("create", "remotecore.rank.create", "Create een rank") {
            @Override
            public void execute(String[] args) throws CoreException {
                subCreate.run(getSettings(), getUser(), getArgs());
            }
        });
        this.registerSub(new SubCommand("delete", "remotecore.rank.delete", "Delete een rank") {
            @Override
            public void execute(String[] args) throws CoreException {
                subDelete.run(getSettings(), getUser(), getArgs());
            }
        });
        this.registerSub(new SubCommand("edit", "remotecore.rank.edit", "Bewerk een rank") {
            @Override
            public void execute(String[] args) throws CoreException {
                subEdit.run(getSettings(), getUser(), getArgs());
            }
        });
        this.registerSub(new SubCommand("info", "remotecore.rank.info", "Krijg info van een rank") {
            @Override
            public void execute(String[] args) throws CoreException {
                subInfo.run(getSettings(), getUser(), getArgs());
            }
        });
//        this.registerSub(new SubCommand("list", "remotecore.rank.list", "Krijg een lijst met alle ranks") {
//            @Override
//            public void execute(String[] args) throws CoreException {
//                subList.run(getSettings(), getUser(), getArgs());
//            }
//        });
    }

    @Override
    public void execute() throws CoreException {

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return getSuggestions(args[0], "create", "delete", "edit", "info");
        } else if (args.length == 2) {
            switch (args[0]) {
                case "create": {
                    return getSuggestions(args[1], "<name>");
                }
                case "delete":
                case "edit":
                case "info": {
                    ArrayList<String> suggestions = new ArrayList<>();
                    for (String rank : RankData.getRanks()) {
                        if (rank.toLowerCase().startsWith(args[1].toLowerCase())) {
                            suggestions.add(rank);
                        }
                    }
                    return suggestions;
                }
            }
        } else if (args.length == 3) {
            if ("create".equals(args[0])) {
                return getSuggestions(args[2], "<prefix>");
            }
        } else if (args.length == 4) {
            if ("create".equals(args[0])) {
                return getSuggestions(args[3], "#<color>");
            }
        }
        return new ArrayList<>();
    }
}
