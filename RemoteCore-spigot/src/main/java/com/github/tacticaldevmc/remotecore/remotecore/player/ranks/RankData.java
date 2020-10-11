package com.github.tacticaldevmc.remotecore.remotecore.player.ranks;

import com.github.tacticaldevmc.remotecore.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.remotecore.logs.LogHandler;
import com.github.tacticaldevmc.remotecore.remotecore.storage.impl.MySQLImplemention;
import com.github.tacticaldevmc.remotecore.remotecore.utils.Prefix;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class RankData {

    private static String rank;

    public RankData(String rank) {
        this.rank = rank;
    }

    static String table = RemoteCoreSpigot.getInstance().getStorageManager().manager().getTable();

    public boolean exists() {
        try (Connection connection = MySQLImplemention.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + table + "ranks` WHERE name=?")) {
                statement.setString(1, rank);

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

    public void createRank(String prefix, String color) {
        try (Connection connection = MySQLImplemention.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO `" + table + "ranks` " +
                    "(name, " +
                    "prefix, " +
                    "color) " +
                    " VALUES " +
                    "(?, " +
                    "?, " +
                    "?);")) {
                statement.setString(1, rank);
                statement.setString(2, prefix);
                statement.setString(3, color);

                statement.execute();
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            LogHandler.getHandler().logException(Prefix.ERROR, e);
        }
    }

    public void deleteRank() {
        try (Connection connection = MySQLImplemention.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM `" + table + "ranks` WHERE name=?")) {
                statement.setString(1, rank);

                statement.executeUpdate();
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            LogHandler.getHandler().logException(Prefix.ERROR, e);
        }
    }

    public void edit(String name, String prefix, String color) {
        try (Connection connection = MySQLImplemention.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE `" + table + "ranks` SET name=?, prefix=?, color=? WHERE name=?")) {
                statement.setString(1, name);
                statement.setString(2, prefix);
                statement.setString(3, color);
                statement.setString(4, rank);

                statement.executeUpdate();
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            LogHandler.getHandler().logException(Prefix.ERROR, e);
        }
    }

    public CompletableFuture<String> getCreatedAt() {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = MySQLImplemention.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + table + "ranks` WHERE name=?")) {
                    statement.setString(1, rank);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            return resultSet.getString("created_at");
                        }
                        resultSet.close();
                        statement.close();
                        connection.close();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "Unknown";
        });
    }

    public CompletableFuture<String> getDiscordID() {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = MySQLImplemention.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + table + "ranks` WHERE name=?")) {
                    statement.setString(1, rank);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            return resultSet.getString("discord_id");
                        }
                        resultSet.close();
                        statement.close();
                        connection.close();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "Unknown";
        });
    }

    public void setDiscordID(String id) {
        try (Connection connection = MySQLImplemention.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE `" + table + "ranks` SET discord_id=? WHERE name=?")) {
                statement.setString(1, id);
                statement.setString(2, rank);

                statement.executeUpdate();
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            LogHandler.getHandler().logException(Prefix.ERROR, e);
        }
    }

    public CompletableFuture<String> getName() {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = MySQLImplemention.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + table + "ranks` WHERE name=?")) {
                    statement.setString(1, rank);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            return resultSet.getString("name");
                        }
                        resultSet.close();
                        statement.close();
                        connection.close();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "Unknown";
        });
    }

    public CompletableFuture<String> getPrefix() {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = MySQLImplemention.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + table + "ranks` WHERE name=?")) {
                    statement.setString(1, rank);

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
            return "Unknown";
        });
    }

    public CompletableFuture<String> getColor() {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection connection = MySQLImplemention.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + table + "ranks` WHERE name=?")) {
                    statement.setString(1, rank);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            return resultSet.getString("color");
                        }
                        resultSet.close();
                        statement.close();
                        connection.close();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "Unknown";
        });
    }

    public Integer getRegisteredRanks() {
        int i = 0;
        try (Connection connection = MySQLImplemention.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + table + "ranks`")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        i++;
                        return i;
                    }
                    resultSet.close();
                    statement.close();
                    connection.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static final ArrayList<String> ranks = new ArrayList<>();

    public static ArrayList<String> getRanks() {
        try (Connection connection = MySQLImplemention.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM `" + table + "ranks`")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        ranks.add(resultSet.getString("name"));
                    }
                    resultSet.close();
                    statement.close();
                    connection.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ranks;
    }
}
