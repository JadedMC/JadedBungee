package net.jadedmc.jadedbungee.features.chat.commands;

import net.jadedmc.jadedbungee.JadedBungee;
import net.jadedmc.jadedbungee.features.chat.Channel;
import net.jadedmc.jadedbungee.utils.ChatUtils;
import net.jadedmc.jadedbungee.utils.StringUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class StaffChatCMD extends Command {
    private final JadedBungee plugin;

    /**
     * Creates the /staffchat command with the permission "staff.chat" and the alias /sc
     * @param plugin Instance of the plugin.
     */
    public StaffChatCMD(JadedBungee plugin) {
        super("staffchat", "staff.chat", "sc");
        this.plugin = plugin;
    }

    /**
     * This is the code that runs when the command is sent.
     * @param sender The player (or console) that sent the command.
     * @param args The arguments of the command.
     */
    @Override
    public void execute(CommandSender sender, String[] args) {

        // Makes sure the command isn't being run by the console.
        if(!(sender instanceof ProxiedPlayer player)) {
            return;
        }

        // Checks if the command was run with no arguments.
        if(args.length == 0) {
            // If so, toggles the channel on or off.
            if(plugin.channelManager().getChannel(player).getName().equalsIgnoreCase("STAFF")) {
                plugin.channelManager().setChannel(player, plugin.channelManager().getChannel("GLOBAL"));
                ChatUtils.chat(player, "&a&lChat &8» &aChannel set to &7" + plugin.channelManager().getChannel("GLOBAL").getName() + "&a.");
            }
            else {
                plugin.channelManager().setChannel(player, plugin.channelManager().getChannel("STAFF"));
                ChatUtils.chat(player, "&a&lChat &8» &aChannel set to &7" + plugin.channelManager().getChannel("STAFF").getName() + "&a.");
            }
        }
        else {
            // If not, sends the arguments to the channel as a chat message.
            Channel channel = plugin.channelManager().getChannel(player);
            String message = StringUtils.join(args, " ");

            plugin.channelManager().setChannel(player, plugin.channelManager().getChannel("STAFF"));
            plugin.channelManager().chat(player, message);
            plugin.channelManager().setChannel(player, channel);
        }
    }
}