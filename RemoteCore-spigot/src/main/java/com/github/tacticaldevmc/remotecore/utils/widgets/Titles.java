package com.github.tacticaldevmc.remotecore.utils.widgets;

import com.github.tacticaldevmc.remotecore.utils.Colors;
import net.minecraft.server.v1_15_R1.IChatBaseComponent;
import net.minecraft.server.v1_15_R1.PacketPlayOutChat;
import net.minecraft.server.v1_15_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_15_R1.PlayerConnection;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class Titles {

    public static void sendTitle(Player player, String message, int fadeIn, int duration, int fadeOut) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn, duration, fadeOut);
        connection.sendPacket(packetPlayOutTimes);

        IChatBaseComponent title = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Colors.format(message) + "\"}");
        PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, title);
        connection.sendPacket(packetPlayOutSubTitle);
    }

    public static void sendSubTitle(Player player, String message, int fadeIn, int duration, int fadeOut) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn, duration, fadeOut);
        connection.sendPacket(packetPlayOutTimes);

        IChatBaseComponent subTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Colors.format(message) + "\"}");
        PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subTitle);
        connection.sendPacket(packetPlayOutSubTitle);
    }

    public static void sendCompleteTitle(Player player, String title, String subTitle, int fadein, int duration, int fadeout) {
        sendTitle(player, title, fadein, duration, fadeout);
        sendSubTitle(player, subTitle, fadein, duration, fadeout);
    }

    public static void sendActionBar(Player player, String message) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        PacketPlayOutChat packet = new PacketPlayOutChat(
                IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + Colors.format(message) + "\"}"));

        connection.sendPacket(packet);
    }
}
