package net.jadedmc.jadedbungee.features.chat.commands;

import net.jadedmc.jadedbungee.JadedBungee;
import net.jadedmc.jadedbungee.features.chat.Channel;
import net.jadedmc.jadedbungee.utils.ChatUtils;
import net.jadedmc.jadedbungee.utils.StringUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class AdminChatCMD extends Command {
    private final JadedBungee plugin;

    /**
     * Creates the /adminchat command with the permission "admin.chat" and the alias /ac
     * @param plugin Instance of the plugin.
     */
    public AdminChatCMD(JadedBungee plugin) {
        super("adminchat", "admin.chat", "ac");
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
            if(plugin.channelManager().getChannel(player).getName().equalsIgnoreCase("ADMIN")) {
                plugin.channelManager().setChannel(player, plugin.channelManager().getChannel("GLOBAL"));
                ChatUtils.chat(player, "&a&lChat &8» &aChannel set to &7" + plugin.channelManager().getChannel("GLOBAL").getName() + "&a.");
            }
            else {
                plugin.channelManager().setChannel(player, plugin.channelManager().getChannel("ADMIN"));
                ChatUtils.chat(player, "&a&lChat &8» &aChannel set to &7" + plugin.channelManager().getChannel("ADMIN").getName() + "&a.");
            }
        }
        else {
            // If not, sends the arguments to the channel as a chat message.
            Channel channel = plugin.channelManager().getChannel(player);
            String message = StringUtils.join(args, " ");

            plugin.channelManager().setChannel(player, plugin.channelManager().getChannel("ADMIN"));
            plugin.channelManager().chat(player, message);
            plugin.channelManager().setChannel(player, channel);
        }
    }
}