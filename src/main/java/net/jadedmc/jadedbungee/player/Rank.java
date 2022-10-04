package net.jadedmc.jadedbungee.player;

import net.jadedmc.jadedbungee.utils.ChatUtils;
import net.md_5.bungee.api.ChatColor;

public enum Rank {
    OWNER("&c&lOwner ", ChatColor.RED),
    ADMIN("&c&lAdmin ", ChatColor.RED),
    MOD("&6&lMod ", ChatColor.GOLD),
    TRIAL("&6&lTrial ", ChatColor.GOLD),
    BUILDER("&e&lBuilder ", ChatColor.YELLOW),
    DEVELOPER("&e&lDeveloper ", ChatColor.YELLOW),
    JADED("&a&lJaded ", ChatColor.GREEN),
    SAPPHIRE("&9&lSapphire ", ChatColor.BLUE),
    AMETHYST("&5&lAmethyst ", ChatColor.DARK_PURPLE),
    DEFAULT("", ChatColor.GRAY);

    private final String prefix;
    private final ChatColor rankColor;

    Rank(String prefix, ChatColor rankColor) {
        this.prefix = prefix;
        this.rankColor = rankColor;
    }

    public String getPrefix() {
        return ChatUtils.translate(prefix);
    }

    public ChatColor getRankColor() {
        return rankColor;
    }
}