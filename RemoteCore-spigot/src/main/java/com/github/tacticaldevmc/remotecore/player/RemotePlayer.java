package com.github.tacticaldevmc.remotecore.player;

import com.github.tacticaldevmc.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.messages.enums.RemoteMessages;
import com.github.tacticaldevmc.remotecore.logs.LogHandler;
import com.github.tacticaldevmc.remotecore.storage.impl.MySQLImplemention;
import com.github.tacticaldevmc.remotecore.utils.Prefix;
import com.github.tacticaldevmc.remotecore.utils.utils.DateUtil;
import com.github.tacticaldevmc.remotecore.utils.Colors;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class RemotePlayer {

    private final Player base;

    public RemotePlayer(Player base) {
        this.base = base;
    }

    public RemotePlayer(String name) {
        base = Bukkit.getPlayer(name);
    }

    public RemotePlayer(UUID uuid) {
        base = Bukkit.getPlayer(uuid);
    }

    public Player getBase() {
        return base;
    }

    public UUID getUUID() {
        return getBase().getUniqueId();
    }

    public void sendMessage(RemoteMessages message) {
        message.send(base);
    }

    public void sendMessage(RemoteMessages message, String replacement) {
        message.send(base, replacement);
    }

    public void sendMessage(RemoteMessages message, Object... object) {
        message.send(base, object);
    }

    public void sendMessage(String message) {
        base.sendMessage(Colors.format(message));
    }

    public void sendWithHex(String hex, String message) {
        base.sendMessage(ChatColor.of(hex) + message);
    }

    public void sendWithHex(Color hex, String message) {
        sendMessage(hex.asRGB() + message);
    }

    public void teleportTo(Player player) {
        base.teleport(player.getLocation());
    }

    public void teleportPlayerToPlayer(Player player, Player target) {
        player.teleport(target.getLocation());
    }

    public void teleportPlayerToBase(Player target) {
        target.teleport(base);
    }

    String table = RemoteCoreSpigot.getInstance().getStorageManager().manager().getTable();

    public boolean exists() {
        try (Connection connection = MySQLImplemention.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + table + "players` WHERE uuid=?")) {
                statement.setString(1, getUUID().toString());

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            LogHandler.getHandler().logException(Prefix.ERROR, e);
        }
        return false;
    }

    public void create() {
        try (Connection connection = MySQLImplemention.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO `" + table + "players` " +
                            "(uuid, " +
                            "name, " +
                            "ip, " +
                            "rank, " +
                            "prefix, " +
                            "suffix, " +
                            "money, " +
                            "homes, " +
                            "fly, " +
                            "freezed, " +
                            "afk, " +
                            "active, " +
                            "first_login, " +
                            "last_login, " +
                            "last_location, " +
                            "geoip_location)" +
                            " VALUES " +
                            "(?, " +
                            "?, " +
                            "?, " +
                            "?, " +
                            "?, " +
                            "?, " +
                            "?, " +
                            "?, " +
                            "?, " +
                            "?, " +
                            "?, " +
                            "?, " +
                            "?, " +
                            "?, " +
                            "?, " +
                            "?);")) {
                statement.setString(1, getUUID().toString());
                statement.setString(2, getBase().getName());
                statement.setString(3, getBase().getAddress().getAddress().toString().replace("/", ""));
                statement.setString(4, "Geen");
                statement.setString(5, "Geen");
                statement.setString(6, "Geen");
                statement.setInt(7, 0);
                statement.setInt(8, 0);
                statement.setBoolean(9, false);
                statement.setBoolean(10, false);
                statement.setBoolean(11, false);
                statement.setBoolean(12, true);
                statement.setString(13, DateUtil.nowDate(false));
                statement.setString(14, "Unknown");
                statement.setString(15, "Unknown");
                statement.setString(16, "Unknown");

                statement.execute();
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            LogHandler.getHandler().logException(Prefix.ERROR, e);
        }
    }

    public CompletableFuture<String> getRank() {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = MySQLImplemention.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + table + "players` WHERE uuid=?")) {
                    statement.setString(1, getUUID().toString());

                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            return resultSet.getString("rank");
                        }
                        resultSet.close();
                        statement.close();
                        connection.close();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "Geen";
        });
    }

    public void setRank(String value) {
        try (Connection connection = MySQLImplemention.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE `" + table + "players` SET rank=? WHERE uuid=?")) {
                statement.setString(1, value);
                statement.setString(2, getUUID().toString());

                statement.executeUpdate();
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CompletableFuture<String> getPrefix() {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = MySQLImplemention.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + table + "players` WHERE uuid=?")) {
                    statement.setString(1, getUUID().toString());

                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            return resultSet.getString("prefix");
                        }
                        resultSet.close();
                        statement.close();
                        connection.close();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "Geen";
        });
    }

    public void setPrefix(String value) {
        try (Connection connection = MySQLImplemention.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE `" + table + "players` SET prefix=? WHERE uuid=?")) {
                statement.setString(1, value);
                statement.setString(2, getUUID().toString());

                statement.executeUpdate();
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CompletableFuture<String> getSuffix() {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = MySQLImplemention.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + table + "players` WHERE uuid=?")) {
                    statement.setString(1, getUUID().toString());

                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            return resultSet.getString("suffix");
                        }
                        resultSet.close();
                        statement.close();
                        connection.close();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "Geen";
        });
    }

    public void setSuffix(String value) {
        try (Connection connection = MySQLImplemention.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE `" + table + "players` SET suffix=? WHERE uuid=?")) {
                statement.setString(1, value);
                statement.setString(2, getUUID().toString());

                statement.executeUpdate();
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CompletableFuture<Boolean> isActive() {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = MySQLImplemention.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + table + "players` WHERE uuid=?")) {
                    statement.setString(1, getUUID().toString());

                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            return resultSet.getBoolean("active");
                        }
                        resultSet.close();
                        statement.close();
                        connection.close();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    public void active(boolean bool) {
        try (Connection connection = MySQLImplemention.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE `" + table + "players` SET active=? WHERE uuid=?")) {
                statement.setBoolean(1, bool);
                statement.setString(2, getUUID().toString());

                statement.executeUpdate();
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
