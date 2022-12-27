package net.jadedmc.jadedbungee.listeners;

import net.jadedmc.jadedbungee.JadedBungee;
import net.jadedmc.jadedbungee.features.party.Party;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * This listens to the PlayerDisconnectEvent event, which is called every time a player leaves the server.
 * We use this to announce when a staff member leaves to other staff members.
 */
public class PlayerDisconnectListener implements Listener {
    private final JadedBungee plugin;

    /**
     * To be able to access the configuration files, we need to pass an instance of the plugin to our listener.
     * This is known as Dependency Injection.
     * @param plugin Instance of the plugin.
     */
    public PlayerDisconnectListener(JadedBungee plugin) {
        this.plugin = plugin;
    }

    /**
     * Runs when the event is called.
     * @param event PlayerDisconnectEvent.
     */
    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();

        // Update the last time the player was online.
        plugin.customPlayerManager().getPlayer(player).updateLastOnline();

        plugin.customPlayerManager().removePlayer(player);
        plugin.channelManager().removePlayer(player);

        Party party = plugin.partyManager().getParty(player);
        if(party != null) {
            party.removePlayer(player);
            party.sendMessage("&aParty &8» &f" + player.getName() + " &adisconnected.");
        }
    }
}