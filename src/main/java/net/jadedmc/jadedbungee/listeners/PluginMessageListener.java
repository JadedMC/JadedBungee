package net.jadedmc.jadedbungee.listeners;

import net.jadedmc.jadedbungee.JadedBungee;
import net.jadedmc.jadedbungee.features.party.Party;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * This listens to the PluginMessageEvent event, which is called every time a plugin sends a message to bungeecord.
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
    public void onMessage(PluginMessageEvent event) throws IOException {
        String tag = event.getTag();

        if(!tag.contains("jadedmc")) {
            return;
        }

        // Used to accept the message from the tournament server.
        DataInputStream input = new DataInputStream(new ByteArrayInputStream(event.getData()));
        String subChannel = input.readUTF();

        switch (tag.toLowerCase()) {
            case "jadedmc:party" -> {
                if(subChannel.equalsIgnoreCase("summon")) {
                    String uuid = input.readUTF();

                    System.out.println("[Party Summon] " + uuid);

                    Party party = plugin.partyManager().getParty(uuid);

                    if(party == null) {
                        return;
                    }

                    ProxiedPlayer leader = party.getLeader();

                    for(ProxiedPlayer player : party.getMembers()) {
                        if(player.equals(leader)) {
                            continue;
                        }

                        player.connect(leader.getServer().getInfo());
                    }
                }
            }
        }
    }
}