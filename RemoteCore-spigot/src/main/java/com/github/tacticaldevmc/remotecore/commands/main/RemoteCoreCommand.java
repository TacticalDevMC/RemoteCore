package com.github.tacticaldevmc.remotecore.commands.main;

import com.github.tacticaldevmc.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.player.ranks.RankData;
import com.github.tacticaldevmc.remotecore.commands.base.CoreCommand;
import com.github.tacticaldevmc.remotecore.commands.base.SubCommand;
import com.github.tacticaldevmc.remotecore.commands.main.sub.UserSubCommand;
import com.github.tacticaldevmc.remotecore.utils.exception.CoreException;
import com.github.tacticaldevmc.remotecore.utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class RemoteCoreCommand extends CoreCommand {

    public RemoteCoreCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    UserSubCommand subUser = new UserSubCommand();

    @Override
    public void init() {
        this.registerSub(new SubCommand("reload", "remotecore.command.reload", "Reload command") {
            @Override
            public void execute(String[] args) throws CoreException {
                RemoteCoreSpigot.getInstance().reload();
                getPlayer().sendMessage(Colors.format("&bRemoteCore TeamCity &3reloaded! &b" + RemoteCoreSpigot.getInstance().getDescription().getVersion()));
            }
        });
        this.registerSub(new SubCommand("user", "remotecore.command.user", "User command") {
            @Override
            public void execute(String[] args) throws CoreException {
                subUser.run(getSettings(), getUser(), getArgs());
            }
        });
    }

    @Override
    public void execute() throws CoreException {

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return getSuggestions(args[0], "user", "reload");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("user")) {
                ArrayList<String> sug = new ArrayList<>();
                for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                    if (offlinePlayer.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                        sug.add(offlinePlayer.getName());
                    }
                }
                return sug;
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("user")) {
                return getSuggestions(args[2], "info", "rank");
            }
        } else if (args.length == 4) {
            if (args[0].equalsIgnoreCase("user")) {
                if (args[2].equalsIgnoreCase("rank")) {
                    ArrayList<String> suggestions = new ArrayList<>();
                    for (String rank : RankData.getRanks()) {
                        if (rank.toLowerCase().startsWith(args[3].toLowerCase())) {
                            suggestions.add(rank);
                        }
                    }
                    return suggestions;
                }
            }
        }
        return new ArrayList<>();
    }
}
