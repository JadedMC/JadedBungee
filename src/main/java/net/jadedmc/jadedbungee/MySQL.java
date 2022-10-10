package net.jadedmc.jadedbungee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * Manages the connection process to MySQL.
 */
public class MySQL {
    private final JadedBungee plugin;
    private Connection connection;
    private final String host;
    private final String database;
    private final String username;
    private final String password;
    private final int port;

    /**
     * Loads the MySQL database connection info.
     * @param plugin Instance of the plugin.
     */
    public MySQL(JadedBungee plugin) {
        this.plugin = plugin;
        host = plugin.settingsManager().getConfig().getString("MySQL.host");
        database = plugin.settingsManager().getConfig().getString("MySQL.database");
        username = plugin.settingsManager().getConfig().getString("MySQL.username");
        password = plugin.settingsManager().getConfig().getString("MySQL.password");
        port = plugin.settingsManager().getConfig().getInt("MySQL.port");
    }

    /**
     * Close a connection.
     */
    public void closeConnection() {
        if(isConnected()) {
            try {
                connection.close();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get the connection.
     * @return Connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Get if plugin is connected to the database.
     * @return Connected
     */
    private boolean isConnected() {
        return (connection != null);
    }

    /**
     * Open a MySQL Connection
     */
    public void openConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return;
            }

            synchronized(JadedBungee.class) {
                if (connection != null && !connection.isClosed()) {
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false&characterEncoding=utf8", username, password);
            }

            PreparedStatement player_ips = connection.prepareStatement("CREATE TABLE IF NOT EXISTS player_ips (" +
                    "uuid VARCHAR(36)," +
                    "ip VARCHAR(16)," +
                    "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "PRIMARY KEY (uuid)" +
                    ");");
            player_ips.execute();

            PreparedStatement player_usernames = connection.prepareStatement("CREATE TABLE IF NOT EXISTS player_usernames (" +
                    "uuid VARCHAR(36)," +
                    "username VARCHAR(16)," +
                    "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "PRIMARY KEY (uuid)" +
                    ");");
            player_usernames.execute();

            // Prevents losing connection to MySQL.
            plugin.getProxy().getScheduler().schedule(plugin, () -> {
                try {
                    connection.isValid(0);
                }
                catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }, 7, 7, TimeUnit.HOURS);
        }
        catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}