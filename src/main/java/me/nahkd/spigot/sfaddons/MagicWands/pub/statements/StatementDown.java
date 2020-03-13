package me.nahkd.spigot.sfaddons.MagicWands.pub.statements;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.nahkd.spigot.sfaddons.MagicWands.pub.ProgramSteppingDirection;
import me.nahkd.spigot.sfaddons.MagicWands.pub.RuntimeInfo;
import me.nahkd.spigot.sfaddons.MagicWands.pub.Statement;

public class StatementDown extends Statement {
	
	@Override
	public boolean runStatement(Player player, RuntimeInfo info, String input) {
		info.direction = ProgramSteppingDirection.DOWN;
		return false;
	}
	@Override
	public double powerCost(String input) {
		return 0.1;
	}
	
	@Override
	public ItemStack displayStatement() {
		return new CustomItem(
				Material.BLUE_STAINED_GLASS_PANE,
				"&9Move Down",
				"&8General",
				"&7Move program pointer downward"
				);
	}

}
