package net.jadedmc.jadedbungee;

import net.jadedmc.jadedbungee.commands.misc.LobbyCMD;
import net.jadedmc.jadedbungee.commands.misc.SeenCMD;
import net.jadedmc.jadedbungee.features.messaging.MessageManager;
import net.jadedmc.jadedbungee.features.messaging.commands.MessageCMD;
import net.jadedmc.jadedbungee.features.messaging.commands.ReplyCMD;
import net.jadedmc.jadedbungee.features.messaging.commands.SocialSpyCMD;
import net.jadedmc.jadedbungee.listeners.*;
import net.jadedmc.jadedbungee.player.CustomPlayerManager;
import net.md_5.bungee.api.plugin.Plugin;

public final class JadedBungee extends Plugin {
    private CustomPlayerManager customPlayerManager;
    private MessageManager messageManager;
    private MySQL mySQL;
    private SettingsManager settingsManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        settingsManager = new SettingsManager(this);

        mySQL = new MySQL(this);
        mySQL.openConnection();

        customPlayerManager = new CustomPlayerManager(this);
        messageManager = new MessageManager(this);

        getProxy().getPluginManager().registerListener(this, new LoginListener(this));
        getProxy().getPluginManager().registerListener(this, new PlayerDisconnectListener(this));
        getProxy().getPluginManager().registerListener(this, new PostLoginListener(this));

        // Message Commands
        getProxy().getPluginManager().registerCommand(this, new MessageCMD(this));
        getProxy().getPluginManager().registerCommand(this, new ReplyCMD(this));
        getProxy().getPluginManager().registerCommand(this, new SocialSpyCMD(this));

        // Misc Commands
        getProxy().getPluginManager().registerCommand(this, new LobbyCMD(this));
        getProxy().getPluginManager().registerCommand(this, new SeenCMD(this));
    }

    public CustomPlayerManager customPlayerManager() {
        return customPlayerManager;
    }

    public MessageManager messageManager() {
        return messageManager;
    }

    public MySQL mySQL() {
        return mySQL;
    }

    public SettingsManager settingsManager() {
        return settingsManager;
    }
}