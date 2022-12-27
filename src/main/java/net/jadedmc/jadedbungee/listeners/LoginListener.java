package net.jadedmc.jadedbungee.listeners;

import net.jadedmc.jadedbungee.JadedBungee;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.protocol.Property;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class LoginListener implements Listener {
    private final JadedBungee plugin;

    public LoginListener(JadedBungee plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(LoginEvent event) {
        UUID uuid = event.getConnection().getUniqueId();
        String skin = "";

        for(Property property : event.getLoginResult().getProperties()) {
            if(property.getName().equals("textures")) {
                skin = property.getValue();
            }
        }

        if(skin.equals("")) {
            System.out.println("No skin found for " + event.getConnection().getName());
            return;
        }

        String finalSkin = skin;
        plugin.getProxy().getScheduler().runAsync(plugin, () -> {
            try {
                PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("REPLACE INTO skin_cache (uuid,skin) VALUES (?,?)");
                statement.setString(1, uuid.toString());
                statement.setString(2, finalSkin);
                statement.execute();
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }
}