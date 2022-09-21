package net.jadedmc.jadedbungee.features.chat.channels;

import net.jadedmc.jadedbungee.features.chat.Channel;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class GlobalChannel extends Channel {

    public GlobalChannel() {
        super("GLOBAL");
    }


    @Override
    public boolean chat(ProxiedPlayer player, String message) {
        return false;
    }
}