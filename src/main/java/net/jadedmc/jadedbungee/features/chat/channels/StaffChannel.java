package net.jadedmc.jadedbungee.features.chat.channels;

import net.jadedmc.jadedbungee.JadedBungee;
import net.jadedmc.jadedbungee.features.chat.Channel;
import net.jadedmc.jadedbungee.player.CustomPlayer;
import net.jadedmc.jadedbungee.utils.ChatUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;
import java.util.HashSet;

/**
 * A chat channel only visible to staff members.
 */
public class StaffChannel extends Channel {
    private final JadedBungee plugin;

    /**
     * Creates the channel.
     * Name: STAFF
     * Permission: staff.chat
     * Aliases:
     *   - s
     *   - sc
     * @param plugin Instance of the plugin.
     */
    public StaffChannel(JadedBungee plugin) {
        super(plugin, "STAFF", "staff.chat", "s", "sc");

        this.plugin = plugin;
    }

    /**
     * Formats a message sent to this channel.
     * @param player Player who sent the message.
     * @param message Message being formatted.
     * @return Formatted message.
     */
    @Override
    public String formatMessage(ProxiedPlayer player, String message) {
        CustomPlayer customPlayer = plugin.customPlayerManager().getPlayer(player);

        String rank  = customPlayer.getRank().toString();
        rank = rank.substring(0,1).toUpperCase() + rank.substring(1).toLowerCase();

        return "&4&l(&c&lStaff&4&l) &c&l" + rank + " &f" + player.getDisplayName() + " &8» &c" + message;
    }

    /**
     * Gets the list of players who should receive a message from a player.
     * @param player Player to get viewers of.
     * @return All staff members who can see the message.
     */
    @Override
    public Collection<ProxiedPlayer> getViewers(ProxiedPlayer player) {
        Collection<ProxiedPlayer> viewers = new HashSet<>();

        for(ProxiedPlayer viewer : plugin.getProxy().getPlayers()) {
            if(viewer.hasPermission("staff.chat")) {
                viewers.add(viewer);
            }
        }

        return viewers;
    }
}