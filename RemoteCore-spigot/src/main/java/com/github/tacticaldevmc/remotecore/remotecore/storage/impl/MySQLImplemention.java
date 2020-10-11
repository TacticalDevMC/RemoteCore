package com.github.tacticaldevmc.remotecore.remotecore.storage.impl;

import com.github.tacticaldevmc.remotecore.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.remotecore.config.Config;
import com.github.tacticaldevmc.remotecore.remotecore.logs.LogHandler;
import com.github.tacticaldevmc.remotecore.remotecore.settings.interfaces.ISettings;
import com.github.tacticaldevmc.remotecore.remotecore.storage.interfaces.IStorage;
import com.github.tacticaldevmc.remotecore.remotecore.utils.Prefix;
import com.github.tacticaldevmc.remotecore.remotecore.utils.ThreadUtil;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class MySQLImplemention implements IStorage {

    private static HikariDataSource hikariDataSource;
    private ISettings settings = RemoteCoreSpigot.getInstance().getSettings();

    @Override
    public void start() {
        long start = System.currentTimeMillis();
        hikariDataSource = new HikariDataSource();

        Config config = settings.getConfig();
        ConfigurationSection mysql = config.getConfigurationSection("mysql");

        String host = mysql.getString("host");
        String user = mysql.getString("user");
        String password = mysql.getString("password");
        String database = mysql.getString("database");
        int port = mysql.getInt("port");

        checkForEmpty(host, user, password, database);

        hikariDataSource.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikariDataSource.addDataSourceProperty("serverName", host);
        hikariDataSource.addDataSourceProperty("user", user);
        hikariDataSource.addDataSourceProperty("password", password);
        hikariDataSource.addDataSourceProperty("databaseName", database);
        hikariDataSource.addDataSourceProperty("port", port);

        hikariDataSource.addDataSourceProperty("autoReconnect", true);
        hikariDataSource.addDataSourceProperty("cachePrepStmts", true);
        hikariDataSource.addDataSourceProperty("prepStmtCacheSize", 250);
        hikariDataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        hikariDataSource.addDataSourceProperty("useServerPrepStmts", true);
        hikariDataSource.addDataSourceProperty("cacheResultSetMetadata", true);

        hikariDataSource.setConnectionTimeout(30000);
        hikariDataSource.setMaximumPoolSize(30);
        hikariDataSource.setPoolName("RemoteCore Pool");

        if (hikariDataSource != null || getConnection() != null) {
            // CREATE TABLES
            createPlayers();
            createRanks();

            if (settings.isDebug()) {
                LogHandler.getHandler().logMessage("Database logged in! Took " + (start - System.currentTimeMillis()) + "ms!");
            } else {
                LogHandler.getHandler().logMessage("Database logged in!");
            }
        }
    }

    private void createPlayers() {
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS `" + getTable() + "players` " +
                            "(id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "uuid varchar(255), " +
                            "name varchar(255), " +
                            "ip varchar(255), " +
                            "rank varchar(255), " +
                            "prefix varchar(255), " +
                            "suffix varchar(255), " +
                            "money INT, " +
                            "homes INT, " +
                            "fly boolean, " +
                            "freezed boolean, " +
                            "afk boolean, " +
                            "active boolean, " +
                            "first_login varchar(255), " +
                            "last_login varchar(255), " +
                            "last_location varchar(255), " +
                            "geoip_location varchar(255), " +
                            "created_at datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(), " +
                            "UNIQUE INDEX (uuid));")) {
                statement.execute();
                statement.close();
                connection.close();

            }
        } catch (SQLException e) {
            LogHandler.getHandler().logException(Prefix.ERROR, e);
        }
    }

    private void createRanks() {
        try (Connection connection = getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS `" + getTable() + "ranks` " +
                            "(id INT AUTO_INCREMENT PRIMARY KEY, " +
                            "name varchar(255), " +
                            "prefix varchar(255), " +
                            "color varchar(255), " +
                            "discord_id varchar(255), " +
                            "created_at datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(), " +
                            "UNIQUE INDEX (name));")) {
                statement.execute();
                statement.close();
                connection.close();

            }
        } catch (SQLException e) {
            LogHandler.getHandler().logException(Prefix.ERROR, e);
        }
    }

    @Override
    public void stop() {
        try {
            hikariDataSource.getConnection().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void restart() {
        final int[] time = {0};
        ThreadUtil.async(new Runnable() {
            @Override
            public void run() {
                time[0]++;

                if (time[0] == 10) {
                    stop();
                } else if (time[0] == 20) {
                    start();
                    time[0] = 0;
                }
            }
        });
    }

    @Override
    public String backEndName() {
        return "MySQL";
    }

    @Override
    public String getTable() {
        return settings.getConfig().getString("mysql.table");
    }

    public static Connection getConnection() {
        try {
            return hikariDataSource.getConnection();
        } catch (SQLException e) {
            LogHandler.getHandler().logException(Prefix.ERROR, e);
        }
        return null;
    }

    public HikariDataSource getHikariDataSource() {
        return hikariDataSource;
    }

    private boolean checkForEmpty(String... db) {
        Arrays.asList(db).forEach(s -> {
            if (s.equals("")) {
                RemoteCoreSpigot.getInstance().getLogger().severe(s + " must been valid.");
            }
        });
        return false;
    }

}
