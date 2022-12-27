package net.jadedmc.jadedbungee.commands.misc;

import net.jadedmc.jadedbungee.JadedBungee;
import net.jadedmc.jadedbungee.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SeenCMD extends Command {
    private final JadedBungee plugin;

    public SeenCMD(JadedBungee plugin) {
        super("seen", "", "");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        // Makes sure the sender has included a username to check.
        if(args.length == 0) {
            return;
        }

        ProxiedPlayer target = plugin.getProxy().getPlayer(args[0]);

        if(target != null) {
            ChatUtils.chat(sender, "&a&lFind &8» &f" + target.getName() + " &ais currently online.");
            return;
        }

        // Checks the database.
        plugin.getProxy().getScheduler().runAsync(plugin, () -> {
            try {
                PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("SELECT * FROM player_info WHERE username = ?");
                statement.setString(1, args[0]);
                ResultSet results = statement.executeQuery();

                if (results.next()) {
                    String username = results.getString("username");
                    Timestamp lastOnline = results.getTimestamp("lastOnline");
                    Timestamp currentTime = new Timestamp(System.currentTimeMillis());

                    long difference = currentTime.getTime() - lastOnline.getTime();

                    double days = (double) difference / 86400000.0;
                    double hours = (days - (int) days) * 24.0;
                    double minutes = (hours - (int) hours) * 60.0;
                    double seconds = (minutes - (int) minutes) * 60.0;

                    String convertedDifference = "";
                    convertedDifference += ((int) days) + "d ";
                    convertedDifference += ((int) hours) + "hr ";
                    convertedDifference += ((int) minutes) + "m ";
                    convertedDifference += ((int) seconds) + "s ";

                    ChatUtils.chat(sender, "&a&lFind &8» &f" + username + " &awas last online &f" + convertedDifference + "&aago.");
                }
                else {
                    ChatUtils.chat(sender, "&cError &8» &cThat player has not played.");
                    return;
                }
            }
            catch (SQLException exception) {
                exception.printStackTrace();
                ChatUtils.chat(sender, "&c&lError &8» &cCould not read the database!");
            }
        });
    }
}