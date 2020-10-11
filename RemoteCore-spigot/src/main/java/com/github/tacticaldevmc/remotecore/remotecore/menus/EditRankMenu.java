package com.github.tacticaldevmc.remotecore.remotecore.menus;

import com.github.tacticaldevmc.remotecore.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.remotecore.bot.Main;
import com.github.tacticaldevmc.remotecore.remotecore.listeners.ListenerManager;
import com.github.tacticaldevmc.remotecore.remotecore.messages.enums.RemoteMessages;
import com.github.tacticaldevmc.remotecore.remotecore.player.RemotePlayer;
import com.github.tacticaldevmc.remotecore.remotecore.player.ranks.RankData;
import com.github.tacticaldevmc.remotecore.remotecore.utils.inventory.ItemBuilder;
import com.github.tacticaldevmc.remotecore.remotecore.utils.inventory.SkullBuilder;
import com.github.tacticaldevmc.remotecore.remotecore.utils.menu.MenuCore;
import com.github.tacticaldevmc.remotecore.remotecore.utils.menu.interfaces.MenuClick;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;

import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.github.tacticaldevmc.remotecore.remotecore.bot.Main.getRandomColor;
import static com.github.tacticaldevmc.remotecore.remotecore.utils.Colors.format;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class EditRankMenu implements Listener {

    private final String INVENTORY_NAME = format("&aEdit rank &2");
    private Player player;
    private MenuCore menu;
    private RankData rank;

    public EditRankMenu(Player player, RankData rank) {
        this.player = player;
        this.rank = rank;
        this.menu = new MenuCore(INVENTORY_NAME + rank.getName().join(), 5);

        new ListenerManager().loadListeners(this);
    }

    public void open() {
        menu.setUpdateHandler(() -> {
            // 18
            menu.addMenuClick(new ItemBuilder(Material.LEGACY_BOOK_AND_QUILL).setName("&6Rank Informatie:")
                    .addLoreLines(
                            "",
                            "&7Naam: &f" + rank.getName().join(),
                            "&7Prefix: " + rank.getPrefix().join(),
                            "&7Color: &f" + rank.getColor().join(),
                            "&7Discord ID: &f" + rank.getDiscordID().join(),
                            "&7Created At: &f" + rank.getCreatedAt().join(),
                            ""
                    ).toItemStack(), new MenuClick() {
                @Override
                public void onItemClick(Player player, ClickType clickType) {
                    player.performCommand("/rank info " + rank.getName().join());
                }
            }, 18);

            menu.addMenuClick(new ItemBuilder(Material.NAME_TAG).setName("&aChange Name")
                    .addLoreLines(
                            "",
                            "&7Current Name: &f" + rank.getName().join(),
                            nameMap.containsKey(player.getUniqueId()) ? "&7Next Name: &f" + nameMap.get(player.getUniqueId()) : "&7Next Name: &c&lUnknown",
                            ""
                    ).toItemStack(), new MenuClick() {
                @Override
                public void onItemClick(Player player, ClickType clickType) {
                    RemotePlayer user = new RemotePlayer(player);
                    if (uuidList.contains(player.getUniqueId()) && !nameMap.isEmpty()) {
                        player.closeInventory();
                        open();
                        return;
                    }

                    uuidList.add(player.getUniqueId());
                    name.add(player.getUniqueId());
                    user.sendMessage(RemoteMessages.RANK_EDIT_MENU_CHANGE_NAME_TEXT);

                    player.closeInventory();
                }
            }, 11);

            menu.addMenuClick(new ItemBuilder(Material.NAME_TAG).setName("&aChange Prefix")
                    .addLoreLines(
                            "",
                            "&7Current Prefix: &f" + rank.getPrefix().join(),
                            prefixMap.containsKey(player.getUniqueId()) ? "&7Next Prefix: &f" + prefixMap.get(player.getUniqueId()) : "&7Next Prefix: &c&lUnknown",
                            ""
                    ).toItemStack(), new MenuClick() {
                @Override
                public void onItemClick(Player player, ClickType clickType) {
                    RemotePlayer user = new RemotePlayer(player);
                    if (uuidList.contains(player.getUniqueId()) && !prefixMap.isEmpty()) {
                        player.closeInventory();
                        open();
                        return;
                    }

                    uuidList.add(player.getUniqueId());
                    prefix.add(player.getUniqueId());
                    user.sendMessage(RemoteMessages.RANK_EDIT_MENU_CHANGE_PREFIX_TEXT);

                    player.closeInventory();
                }
            }, 13);

            menu.addMenuClick(new ItemBuilder(Material.NAME_TAG).setName("&aChange Color")
                    .addLoreLines(
                            "",
                            "&7Current Color: &f" + rank.getColor().join(),
                            colorMap.containsKey(player.getUniqueId()) ? "&7Next Color: &f" + colorMap.get(player.getUniqueId()) : "&7Next Color: &c&lUnknown",
                            ""
                    ).toItemStack(), new MenuClick() {
                @Override
                public void onItemClick(Player player, ClickType clickType) {
                    RemotePlayer user = new RemotePlayer(player);
                    if (uuidList.contains(player.getUniqueId()) && !colorMap.isEmpty()) {
                        player.closeInventory();
                        open();
                        return;
                    }

                    uuidList.add(player.getUniqueId());
                    color.add(player.getUniqueId());
                    user.sendMessage(RemoteMessages.RANK_EDIT_MENU_CHANGE_COLOR_TEXT);

                    player.closeInventory();
                }
            }, 15);

            menu.addMenuClick(new ItemBuilder(Material.EMERALD_BLOCK).setName("&aAccepteer Bewerkingen")
                    .addLoreLines(
                            "",
                            "&7Klik om de bewerkingen te activeren.",
                            ""
                    ).toItemStack(), new MenuClick() {
                @Override
                public void onItemClick(Player player, ClickType clickType) {
                    RemotePlayer user = new RemotePlayer(player);
                    if (!uuidList.contains(player.getUniqueId())) {
                        user.sendMessage(RemoteMessages.RANK_EDIT_MENU_CHOOSE_SECTION);
                        return;
                    }

                    // TODO: Add edited history to 'history' table, history systeem wordt gemaakt.
                    // TODO: Send edited log naar de discord server.

                    if (RemoteCoreSpigot.getInstance().getSettings().minecraftDiscord()) {
                        String guildID = RemoteCoreSpigot.getInstance().getSettings().getGuildID();
                        String logChannel = RemoteCoreSpigot.getInstance().getSettings().getLogChannelID();
                        String discordID = rank.getDiscordID().join();
                        if (discordID.equals("0")) return;

                        Role role = Main.getJda().getGuildById(guildID).getRoleById(discordID);

                        EmbedBuilder embedBuilder = new EmbedBuilder().setTitle("Edit Role");

                        embedBuilder.setColor(getRandomColor());
                        embedBuilder.setFooter("RemoteCore Plugin", null);
                        embedBuilder.setTimestamp(Instant.now());

                        embedBuilder.addField("Old Name", rank.getName().join(), false);
                        embedBuilder.addField("Old Color", rank.getColor().join(), false);
                        embedBuilder.addField("Old Prefix", rank.getPrefix().join(), false);
                        embedBuilder.addField("", "", false);
                        embedBuilder.addField("New:", "", false);
                        embedBuilder.addField("Name", nameMap.get(player.getUniqueId()), false);
                        embedBuilder.addField("Color", prefixMap.get(player.getUniqueId()), false);
                        embedBuilder.addField("Prefix", colorMap.get(player.getUniqueId()), false);

                        Main.getJda().getGuildById(guildID).getTextChannelById(logChannel).sendMessage(embedBuilder.build()).queue();
                        role.getManager().setName(nameMap.get(player.getUniqueId())).queue();
                        role.getManager().setColor(Color.decode(colorMap.get(player.getUniqueId()))).queue();
                    }

                    rank.edit(nameMap.get(player.getUniqueId()), prefixMap.get(player.getUniqueId()), colorMap.get(player.getUniqueId()));
                    user.sendMessage(RemoteMessages.RANK_EDIT_EDITTED, nameMap.get(player.getUniqueId()));

                    nameMap.remove(player.getUniqueId());
                    prefixMap.remove(player.getUniqueId());
                    colorMap.remove(player.getUniqueId());
                    uuidList.remove(player.getUniqueId());
                    player.closeInventory();
                }
            }, 39);

            menu.addMenuClick(new ItemBuilder(Material.REDSTONE_BLOCK).setName("&aStop Bewerkingen")
                    .addLoreLines(
                            "",
                            "&7Klik om de bewerkingen te stoppen.",
                            ""
                    ).toItemStack(), new MenuClick() {
                @Override
                public void onItemClick(Player player, ClickType clickType) {
                    RemotePlayer user = new RemotePlayer(player);
                    UUID uuid = player.getUniqueId();
                    if (!uuidList.contains(uuid)) {
                        user.sendMessage(RemoteMessages.RANK_EDIT_MENU_CHOOSE_SECTION);
                        return;
                    }

                    user.sendMessage(RemoteMessages.RANK_EDIT_STOPPED);

                    nameMap.remove(uuid);
                    name.remove(uuid);
                    prefixMap.remove(uuid);
                    prefix.remove(uuid);
                    colorMap.remove(uuid);
                    color.remove(uuid);
                    uuidList.remove(uuid);
                    player.closeInventory();
                }
            }, 41);
        });

        menu.setAutoUpdate();
        menu.openMenu(player);
    }

    private ArrayList<UUID> uuidList = new ArrayList<>();
    private ArrayList<UUID> name = new ArrayList<>();
    private ArrayList<UUID> prefix = new ArrayList<>();
    private ArrayList<UUID> color = new ArrayList<>();

    private Map<UUID, String> nameMap = new HashMap<>();
    private Map<UUID, String> prefixMap = new HashMap<>();
    private Map<UUID, String> colorMap = new HashMap<>();

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        RemotePlayer user = new RemotePlayer(player);

        if (uuidList.contains(player.getUniqueId())) {
            if (message.equals("Stop")) {
                uuidList.remove(player.getUniqueId());
                user.sendMessage(RemoteMessages.RANK_EDIT_MENU_STOPPED);
                event.setCancelled(true);
                return;
            }

            if (name.contains(player.getUniqueId())) {
                RankData rData = new RankData(message);

                if (rData.exists()) {
                    user.sendMessage(RemoteMessages.RANK_ALREADY_EXISTS, message);
                    event.setCancelled(true);
                    return;
                }

                nameMap.put(player.getUniqueId(), message);
                event.setCancelled(true);
                open();
                name.remove(player.getUniqueId());
            } else if (prefix.contains(player.getUniqueId())) {
                prefixMap.put(player.getUniqueId(), message);
                event.setCancelled(true);
                open();
                prefix.remove(player.getUniqueId());
            } else if (color.contains(player.getUniqueId())) {
                if (!message.startsWith("#")) {
                    user.sendMessage(RemoteMessages.RANK_CREATE_COLOR_MUST_START_WITH);
                    event.setCancelled(true);
                    return;
                }
                colorMap.put(player.getUniqueId(), message);
                event.setCancelled(true);
                open();
                color.remove(player.getUniqueId());
            }
        }
    }
}
