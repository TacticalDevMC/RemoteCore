package com.github.tacticaldevmc.remotecore.commands.base;

import com.github.tacticaldevmc.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.player.RemotePlayer;
import com.github.tacticaldevmc.remotecore.logs.LogHandler;
import com.github.tacticaldevmc.remotecore.settings.interfaces.ISettings;
import com.github.tacticaldevmc.remotecore.utils.exception.CoreCommandException;
import com.github.tacticaldevmc.remotecore.utils.exception.CoreException;
import com.github.tacticaldevmc.remotecore.utils.exception.UnkownPlayerException;
import com.github.tacticaldevmc.remotecore.utils.Colors;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public abstract class CoreCommand implements CommandExecutor, TabCompleter {

    private final ArrayList<SubCommand> subCommands = new ArrayList<SubCommand>();
    private final String name;
    private final String permission;
    private int lenght;
    private String[] args;
    private CommandSender sender;
    private final String info;
    private final String usage;
    private final boolean sub;
    private boolean allowConsole;
    private RemotePlayer user;
    private final ISettings settings = RemoteCoreSpigot.getInstance().getSettings();

    public ISettings getSettings() {
        return settings;
    }

    public boolean allowSub() {
        return sub;
    }

    public boolean allowConsole() {
        return allowConsole;
    }

    public void setAllowConsole(boolean allow) {
        this.allowConsole = allow;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public int getLenght() {
        return lenght;
    }

    public String[] getArgs() {
        return args;
    }

    public String getInfo() {
        return info;
    }

    public String getUsage() {
        return usage;
    }

    public CoreCommand(String name, String permission, String info, String usage, boolean sub,
                       boolean allowConsole) {
        this.name = name;
        this.permission = permission;
        this.info = info;
        this.usage = usage;
        CommandModule.getInstance().getCommand().add(this);
        this.sub = sub;
        this.allowConsole = allowConsole;
        if (sub) {
            initHelp();
            init();
        }
    }

//    public CoreCommand() {
//    }

    public abstract void init();

    public abstract void execute() throws CoreException;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            if (!sender.hasPermission(this.permission)) {
                sender.sendMessage(RemoteCoreSpigot.getInstance().getSettings().getPermissionMessage().replace("%permission%", this.permission));
                return false;
            }
            if (!allowConsole) {
                if (sender instanceof ConsoleCommandSender) {
                    getSender().sendMessage(Colors.format(RemoteCoreSpigot.getInstance().getSettings().getPrefix() + "&cDit commando is alleen beschikbaar voor spelers!"));
                    return false;
                }
            }
            this.args = args;
            this.sender = sender;
            if (sub) {
                SubCommand subC = getSub(getArgs()[0]);
                if (subC == null) {
                    getSub("help").execute(new String[]{"help", "1"});
                } else {
                    if (!sender.hasPermission(subC.getPermission())) {
                        sender.sendMessage(RemoteCoreSpigot.getInstance().getSettings().getPermissionMessage().replace("%permission%", this.permission));
                        return false;
                    }
                    if (!subC.allowConsole()) {
                        if (sender instanceof ConsoleCommandSender) {
                            getSender().sendMessage(Colors.format(RemoteCoreSpigot.getInstance().getSettings().getPrefix() + "&cDit commando is alleen beschikbaar voor spelers!"));
                            return false;
                        }
                    }
                    subC.execute(args);
                }
            } else {
                try {
                    this.execute();
                } catch (ArrayIndexOutOfBoundsException e) {
                    sender.sendMessage(ChatColor.GOLD + "/" + this.name + " " + ChatColor.GREEN + this.getInfo() + "");
                } catch (UnkownPlayerException e) {
                    getSender().sendMessage(Colors.format(RemoteCoreSpigot.getInstance().getSettings().getPrefix() + "&cOnbekende speler!"));
                } catch (Exception e) {
                    getSender().sendMessage(Colors.format(RemoteCoreSpigot.getInstance().getSettings().getPrefix() + "&cEr ging iets fout!"));
                    RemoteCoreSpigot.getInstance().getLogger().log(Level.SEVERE, "Discovered Command Exception!");
                    LogHandler.getHandler().logException("CoreCommand", e);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            if (args.length == 0) {
                try {
                    if (sub) {
                        getSub("help").execute(new String[]{"help", "1"});
                    }
                } catch (CoreException e1) {
                }
            }
            if (sub) {
                SubCommand subC = null;
                try {
                    subC = getSub(args[0]);
                } catch (ArrayIndexOutOfBoundsException ex) {

                }
                if (subC == null) return false;
                sender.sendMessage(ChatColor.GOLD + "/" + this.name + " " + ChatColor.GREEN + subC.getMainCommand() + " "
                        + ChatColor.LIGHT_PURPLE + subC.getInfo());
            } else {
                if (this.getUsage() == "") {
                    sender.sendMessage(
                            ChatColor.GOLD + "/" + this.getName() + " " + ChatColor.LIGHT_PURPLE + this.getInfo());
                } else {
                    sender.sendMessage(ChatColor.GOLD + "/" + this.getName() + " " + ChatColor.GREEN + this.getUsage()
                            + " " + ChatColor.LIGHT_PURPLE + this.getInfo());
                }
            }
        } catch (Exception e) {
            getSender().sendMessage(Colors.format(RemoteCoreSpigot.getInstance().getSettings().getPrefix() + "&cEr ging iets fout!"));
            LogHandler.getHandler().logException("CoreCommand", e);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

    public void registerCommand() {
        RemoteCoreSpigot.getInstance().getServer().getPluginCommand(name).setExecutor(this);
        RemoteCoreSpigot.getInstance().getServer().getPluginCommand(name).setTabCompleter(this);
    }

    public void registerSub(SubCommand sub) {
        this.subCommands.add(sub);
    }

    private SubCommand getSub(String command) {
        for (SubCommand sub : subCommands) {
            for (String c : sub.commands) {
                if (c.equalsIgnoreCase(command)) {
                    return sub;
                }
            }
        }
        return null;
    }

    public Player getPlayer() {
        if (!allowConsole) {
            return (Player) sender;
        }
        try {
            throw new CoreCommandException("getPlayer() is illegal for this command!");
        } catch (CoreCommandException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RemotePlayer getUser() {
        if (!allowConsole) {
            user = new RemotePlayer(getPlayer());
            return user;
        }
        try {
            throw new CoreCommandException("getUser() is illegal for this command!");
        } catch (CoreCommandException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CommandSender getSender() {
        if (allowConsole) {
            return sender;
        }
        return getPlayer();
    }

    public List<String> getSuggestions(String argument, String... array) {
        argument = argument.toLowerCase();
        List<String> suggestions = new ArrayList<String>();
        for (String suggestion : array) {
            if (suggestion.toLowerCase().startsWith(argument)) {
                suggestions.add(suggestion);
            }
        }
        return suggestions;
    }

    private void initHelp() {
        this.registerSub(new SubCommand("help", this.permission, "Help commando") {

            @Override
            public void execute(String[] args) {
                try {
                    try {
                        sendHelpPage(sender, Integer.parseInt(getArgs()[1]));
                    } catch (ArrayIndexOutOfBoundsException e) {
                        sendHelpPage(sender, 1);
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage(Colors.format(RemoteCoreSpigot.getInstance().getSettings().getPrefix() + "&cOnbekende pagina!"));
                }
            }
        });
    }

    public void sendHelpPage(CommandSender sender, int page) {
        if (page > 5) {
            sender.sendMessage(Colors.format(RemoteCoreSpigot.getInstance().getSettings().getPrefix() + "&cOnbekende pagina!"));
            return;
        }
        int pageNum = (5 * page) - 5;
        int maxHelp = 5 * page;
        sender.sendMessage(ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "------------------------------------");
        for (int i = pageNum; i < maxHelp; i++) {
            if (i < this.subCommands.size()) {
                SubCommand c = this.subCommands.get(i);
                if (!sender.hasPermission(c.getPermission()))
                    continue;
                if (c.getMainCommand().equalsIgnoreCase("help"))
                    continue;
                sender.sendMessage(ChatColor.GOLD + "/" + this.name + " " + ChatColor.GREEN + c.getMainCommand() + " "
                        + ChatColor.LIGHT_PURPLE + c.getInfo());

            } else {
                sender.sendMessage(
                        ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "------------------------------------");
                return;
            }
        }

        sender.sendMessage(ChatColor.BLUE + "Gebruik /" + this.name + " help " + (page + 1) + " om verder te kijken!");
        sender.sendMessage(ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "------------------------------------");

    }
}