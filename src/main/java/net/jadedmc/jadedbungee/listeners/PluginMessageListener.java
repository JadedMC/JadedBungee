package net.jadedmc.jadedbungee.listeners;

import net.jadedmc.jadedbungee.JadedBungee;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * This listens to the PluginMessageEvent event, which is called every time a plugin sends a message to bungeecord.
 * We use this to have the tournament plugin tell bungeecord when an event is created in order to announce it to all
 * players on the network.
 */
public class PluginMessageListener implements Listener {
    private final JadedBungee plugin;

    /**
     * To be able to access the configuration files, we need to pass an instance of the plugin to our listener.
     * This is known as Dependency Injection.
     * @param plugin Instance of the plugin.
     */
    public PluginMessageListener(JadedBungee plugin) {
        this.plugin = plugin;
    }

    /**
     * Runs when the event is called.
     * @param event PluginMessageEvent.
     */
    @EventHandler
    public void onMessage(PluginMessageEvent event) {
        String[] message;

        // Used to accept the message from the tournament server.
        DataInputStream input = new DataInputStream(new ByteArrayInputStream(event.getData()));
        try {
            // Reads the message and splits it into an array.
            message = input.readUTF().split(":");
        }
        catch (IOException exception) {
            exception.printStackTrace();
            message = null;
        }

        if(message == null || message.length == 0) {
            return;
        }

        switch (event.getTag().toLowerCase()) {

            case "tournament" -> {

            }

            case "party" -> {

            }
        }
    }
}