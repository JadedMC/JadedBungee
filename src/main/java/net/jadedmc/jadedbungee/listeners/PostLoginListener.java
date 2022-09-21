package net.jadedmc.jadedbungee.listeners;

import net.jadedmc.jadedbungee.JadedBungee;
import net.jadedmc.jadedbungee.player.CustomPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * This listens to the PostLoginEvent event, which is called every time a player successfully joins the server.
 * We use this to announce when a staff member joins to other staff members.
 */
public class PostLoginListener implements Listener {
    private final JadedBungee plugin;

    /**
     * To be able to access the configuration files, we need to pass an instance of the plugin to our listener.
     * This is known as Dependency Injection.
     * @param plugin Instance of the plugin.
     */
    public PostLoginListener(JadedBungee plugin) {
        this.plugin = plugin;
    }

    /**
     * Runs when the event is called.
     * @param event PostLoginEvent.
     */
    @EventHandler
    public void onLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

        plugin.customPlayerManager().addPlayer(player);
    }
}