package me.nahkd.spigot.sfaddons.MagicWands.pub.statements;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.nahkd.spigot.sfaddons.MagicWands.pub.RuntimeInfo;
import me.nahkd.spigot.sfaddons.MagicWands.pub.Statement;

public class StatementInfo extends Statement {
	
	@Override
	public boolean runStatement(Player player, RuntimeInfo info, String input) {
		player.sendMessage(" §bi §3" + ((input.length() > 0)? input : "Info logging"));
		return false;
	}
	@Override
	public double powerCost(String input) {
		return 0;
	}
	
	@Override
	public ItemStack displayStatement() {
		return new CustomItem(
				Material.LIGHT_BLUE_STAINED_GLASS_PANE,
				"&bInfo",
				"&8General",
				"&7Gives you information"
				);
	}

}
