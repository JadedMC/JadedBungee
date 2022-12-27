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

            PreparedStatement chat_logs = connection.prepareStatement("CREATE TABLE IF NOT EXISTS chat_logs (" +
                    "id INT AUTO_INCREMENT," +
                    "server VARCHAR(45) DEFAULT 'null'," +
                    "channel VARCHAR(45) DEFAULT 'global'," +
                    "uuid VARCHAR(45)," +
                    "username VARCHAR(16)," +
                    "message VARCHAR(256)," +
                    "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "PRIMARY KEY (id)" +
                    ");");
            chat_logs.execute();

            PreparedStatement command_logs = connection.prepareStatement("CREATE TABLE IF NOT EXISTS command_logs (" +
                    "id INT AUTO_INCREMENT," +
                    "server VARCHAR(45) DEFAULT 'null'," +
                    "uuid VARCHAR(45)," +
                    "username VARCHAR(16)," +
                    "command VARCHAR(256)," +
                    "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "PRIMARY KEY (id)" +
                    ");");
            command_logs.execute();

            PreparedStatement player_info = connection.prepareStatement("CREATE TABLE IF NOT EXISTS player_info (" +
                    "uuid VARCHAR(36)," +
                    "username VARCHAR(16)," +
                    "ip VARCHAR(16)," +
                    "level INT DEFAULT 1," +
                    "experience INT DEFAULT 1," +
                    "firstOnline TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "lastOnline TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "PRIMARY KEY (uuid)" +
                    ");");
            player_info.execute();

            PreparedStatement player_logins = connection.prepareStatement("CREATE TABLE IF NOT EXISTS player_logins (" +
                    "uuid VARCHAR(36)," +
                    "username VARCHAR(16)," +
                    "ip VARCHAR(16)," +
                    "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "PRIMARY KEY (time)" +
                    ");");
            player_logins.execute();

            PreparedStatement skin_cache = connection.prepareStatement("CREATE TABLE IF NOT EXISTS skin_cache (" +
                    "uuid VARCHAR(36)," +
                    "skin VARCHAR(500)," +
                    "PRIMARY KEY (uuid)" +
                    ");");
            skin_cache.execute();

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