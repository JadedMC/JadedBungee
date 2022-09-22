package net.jadedmc.jadedbungee.features.chat.channels;

import net.jadedmc.jadedbungee.JadedBungee;
import net.jadedmc.jadedbungee.features.chat.Channel;
import net.jadedmc.jadedbungee.player.CustomPlayer;
import net.jadedmc.jadedbungee.utils.ChatUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * A chat channel only visible to administrators.
 */
public class AdminChannel extends Channel {
    private final JadedBungee plugin;

    /**
     * Creates the channel.
     * Name: ADMIN
     * Permission: admin.chat
     * Aliases:
     *   - a
     * @param plugin Instance of the plugin.
     */
    public AdminChannel(JadedBungee plugin) {
        super("ADMIN", "admin.chat", "a");

        this.plugin = plugin;
    }

    /**
     * Processes a chat message sent to the channel.
     * @param player Player sending the message.
     * @param message Message being sent.
     * @return true, which prevents the chat message from being passed to sub-servers.
     */
    @Override
    public boolean chat(ProxiedPlayer player, String message) {
        CustomPlayer customPlayer = plugin.customPlayerManager().getPlayer(player);

        String rank  = customPlayer.getRank().toString();
        rank = rank.substring(0,1).toUpperCase() + rank.substring(1).toLowerCase();

        for(ProxiedPlayer viewer : ProxyServer.getInstance().getPlayers()) {
            if(viewer.hasPermission("admin.chat")) {
                ChatUtils.chat(viewer, "&4&l(&6&lAdmin&4&l) &6&l" + rank + " &f" + player.getDisplayName() + " &8» &6" + message);
            }
        }

        return true;
    }
}