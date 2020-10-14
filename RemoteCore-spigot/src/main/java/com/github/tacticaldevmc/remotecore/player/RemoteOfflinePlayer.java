package com.github.tacticaldevmc.remotecore.player;

import com.github.tacticaldevmc.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.logs.LogHandler;
import com.github.tacticaldevmc.remotecore.storage.impl.MySQLImplemention;
import com.github.tacticaldevmc.remotecore.utils.Prefix;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

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

public class RemoteOfflinePlayer {

    private final OfflinePlayer base;

    public RemoteOfflinePlayer(OfflinePlayer base) {
        this.base = base;
    }

    public RemoteOfflinePlayer(String name) {
        base = Bukkit.getOfflinePlayer(name);
    }

    public RemoteOfflinePlayer(UUID uuid) {
        base = Bukkit.getOfflinePlayer(uuid);
    }

    public OfflinePlayer getBase() {
        return base;
    }

    public UUID getUUID() {
        return getBase().getUniqueId();
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

    public CompletableFuture<String> getRank() {
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

    public void setRank(String rank) {
        try (Connection connection = MySQLImplemention.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE `" + table + "players` SET rank=? WHERE uuid=?")) {
                statement.setString(1, rank);
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
