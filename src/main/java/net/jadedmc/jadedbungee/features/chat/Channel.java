package net.jadedmc.jadedbungee.features.chat;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public abstract class Channel {
    private final String name;

    public Channel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract boolean chat(ProxiedPlayer player, String message);
}