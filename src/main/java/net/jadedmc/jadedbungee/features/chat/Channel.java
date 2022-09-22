package net.jadedmc.jadedbungee.features.chat;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a separated section of chat.
 */
public abstract class Channel {
    private final String name;
    private final String permission;
    private final List<String> aliases = new ArrayList<>();

    /**
     * Creates the channel object.
     * @param name Name of the channel.
     * @param permission Permission required to use the channel.
     * @param aliases Various aliases for the channel name.
     */
    public Channel(String name, String permission, String... aliases) {
        this.name = name;
        this.permission = permission;
        this.aliases.addAll(Arrays.asList(aliases));
    }

    /**
     * Gets a list of aliases for the channel.
     * @return A list of aliases for the channel name.
     */
    public List<String> getAliases() {
        return aliases;
    }

    /**
     * Gets the name of the channel.
     * @return Channel name is uppercase.
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if a player has permission to use a channel.
     * @param player Player to check permission of.
     * @return Whether they can use the channel.
     */
    public boolean canUse(ProxiedPlayer player) {
        // Returns true if the channel has no permission requirement.
        if(permission.equals("")) {
            return true;
        }

        return player.hasPermission(permission);
    }

    /**
     * Processes player messages to the channel.
     * @param player Player sending the message.
     * @param message Message being sent.
     * @return Whether the message should be passed to the sub-server.
     */
    public abstract boolean chat(ProxiedPlayer player, String message);
}