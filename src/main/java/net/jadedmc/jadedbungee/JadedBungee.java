package net.jadedmc.jadedbungee;

import net.jadedmc.jadedbungee.features.chat.ChannelManager;
import net.jadedmc.jadedbungee.features.chat.commands.ChatCMD;
import net.jadedmc.jadedbungee.features.party.PartyManager;
import net.jadedmc.jadedbungee.listeners.ChatListener;
import net.jadedmc.jadedbungee.listeners.PlayerDisconnectListener;
import net.jadedmc.jadedbungee.listeners.PostLoginListener;
import net.jadedmc.jadedbungee.player.CustomPlayerManager;
import net.md_5.bungee.api.plugin.Plugin;

public final class JadedBungee extends Plugin {
    private ChannelManager channelManager;
    private CustomPlayerManager customPlayerManager;
    private MySQL mySQL;
    private PartyManager partyManager;
    private SettingsManager settingsManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        settingsManager = new SettingsManager(this);

        mySQL = new MySQL(this);
        mySQL.openConnection();

        channelManager = new ChannelManager(this);
        customPlayerManager = new CustomPlayerManager(this);
        partyManager = new PartyManager(this);

        getProxy().getPluginManager().registerListener(this, new ChatListener(this));
        getProxy().getPluginManager().registerListener(this, new PlayerDisconnectListener(this));
        getProxy().getPluginManager().registerListener(this, new PostLoginListener(this));

        getProxy().getPluginManager().registerCommand(this, new ChatCMD(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ChannelManager channelManager() {
        return channelManager;
    }

    public CustomPlayerManager customPlayerManager() {
        return customPlayerManager;
    }

    public MySQL mySQL() {
        return mySQL;
    }

    public PartyManager partyManager() {
        return partyManager;
    }

    public SettingsManager settingsManager() {
        return settingsManager;
    }
}