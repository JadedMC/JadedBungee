package net.jadedmc.jadedbungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.jadedmc.jadedbungee.commands.misc.LobbyCMD;
import net.jadedmc.jadedbungee.commands.misc.SeenCMD;
import net.jadedmc.jadedbungee.features.chat.ChannelManager;
import net.jadedmc.jadedbungee.features.chat.commands.AdminChatCMD;
import net.jadedmc.jadedbungee.features.chat.commands.ChatCMD;
import net.jadedmc.jadedbungee.features.chat.commands.PartyChatCMD;
import net.jadedmc.jadedbungee.features.chat.commands.StaffChatCMD;
import net.jadedmc.jadedbungee.features.messaging.MessageManager;
import net.jadedmc.jadedbungee.features.messaging.commands.MessageCMD;
import net.jadedmc.jadedbungee.features.messaging.commands.ReplyCMD;
import net.jadedmc.jadedbungee.features.messaging.commands.SocialSpyCMD;
import net.jadedmc.jadedbungee.features.party.PartyManager;
import net.jadedmc.jadedbungee.features.party.commands.PartyCMD;
import net.jadedmc.jadedbungee.listeners.*;
import net.jadedmc.jadedbungee.player.CustomPlayerManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Collection;

public final class JadedBungee extends Plugin {
    private ChannelManager channelManager;
    private CustomPlayerManager customPlayerManager;
    private MessageManager messageManager;
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
        messageManager = new MessageManager();

        getProxy().getPluginManager().registerListener(this, new ChatListener(this));
        getProxy().getPluginManager().registerListener(this, new PlayerDisconnectListener(this));
        getProxy().getPluginManager().registerListener(this, new PluginMessageListener(this));
        getProxy().getPluginManager().registerListener(this, new PostLoginListener(this));
        getProxy().getPluginManager().registerListener(this, new ServerSwitchListener(this));

        getProxy().getPluginManager().registerCommand(this, new AdminChatCMD(this));
        getProxy().getPluginManager().registerCommand(this, new ChatCMD(this));
        getProxy().getPluginManager().registerCommand(this, new PartyCMD(this));
        getProxy().getPluginManager().registerCommand(this, new PartyChatCMD(this));
        getProxy().getPluginManager().registerCommand(this, new StaffChatCMD(this));

        // Message Commands
        getProxy().getPluginManager().registerCommand(this, new MessageCMD(this));
        getProxy().getPluginManager().registerCommand(this, new ReplyCMD(this));
        getProxy().getPluginManager().registerCommand(this, new SocialSpyCMD(this));

        // Misc Commands
        getProxy().getPluginManager().registerCommand(this, new LobbyCMD(this));
        getProxy().getPluginManager().registerCommand(this, new SeenCMD(this));

        // Plugin Messaging Channel
        getProxy().registerChannel("jadedmc:party");
    }

    public void sendCustomData(ProxiedPlayer player, String subChannel, String message) {
        Collection<ProxiedPlayer> networkPlayers = ProxyServer.getInstance().getPlayers();

        if (networkPlayers == null || networkPlayers.isEmpty()) {
            return;
        }

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(subChannel);
        out.writeUTF(message);

        player.getServer().getInfo().sendData( "jadedmc:party", out.toByteArray() );
    }

    public ChannelManager channelManager() {
        return channelManager;
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

    public PartyManager partyManager() {
        return partyManager;
    }

    public SettingsManager settingsManager() {
        return settingsManager;
    }
}