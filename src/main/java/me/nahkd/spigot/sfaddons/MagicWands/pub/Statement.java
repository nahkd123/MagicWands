package me.nahkd.spigot.sfaddons.MagicWands.pub;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Statement {
	
	/**
	 * Called from {@link Program}. Return true to stop program
	 * @param player The player executed this program
	 * @return true to stop program, otherwise false
	 */
	public abstract boolean runStatement(Player player);
	public abstract ItemStack[] displayStatement();
	
}
