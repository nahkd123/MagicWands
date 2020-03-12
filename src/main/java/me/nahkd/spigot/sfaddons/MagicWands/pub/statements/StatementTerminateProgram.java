package me.nahkd.spigot.sfaddons.MagicWands.pub.statements;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.nahkd.spigot.sfaddons.MagicWands.pub.RuntimeInfo;
import me.nahkd.spigot.sfaddons.MagicWands.pub.Statement;

public class StatementTerminateProgram extends Statement {
	
	@Override
	public boolean runStatement(Player player, RuntimeInfo info) {return true;}
	
	@Override
	public ItemStack displayStatement() {
		return new CustomItem(
				Material.RED_STAINED_GLASS_PANE,
				"&cTerminate",
				"&8General",
				"&7This will end your program"
				);
	}

}
