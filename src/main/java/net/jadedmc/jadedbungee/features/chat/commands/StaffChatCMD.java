package net.jadedmc.jadedbungee.features.chat.commands;

import net.jadedmc.jadedbungee.JadedBungee;
import net.jadedmc.jadedbungee.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatCMD extends Command {
    private final JadedBungee plugin;

    public StaffChatCMD(JadedBungee plugin) {
        super("staffchat", "staff.chat", "sc");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer player)) {
            return;
        }

        if(plugin.channelManager().getChannel(player).getName().equalsIgnoreCase("STAFF")) {
            plugin.channelManager().setChannel(player, plugin.channelManager().getChannel("GLOBAL"));
            ChatUtils.chat(player, "&a&lChat &8» &aChannel set to &7" + plugin.channelManager().getChannel("GLOBAL").getName() + "&a.");
        }
        else {
            plugin.channelManager().setChannel(player, plugin.channelManager().getChannel("STAFF"));
            ChatUtils.chat(player, "&a&lChat &8» &aChannel set to &7" + plugin.channelManager().getChannel("STAFF").getName() + "&a.");
        }
    }
}