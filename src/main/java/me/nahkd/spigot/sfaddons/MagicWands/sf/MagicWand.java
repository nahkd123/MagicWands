package me.nahkd.spigot.sfaddons.MagicWands.sf;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.ChargableItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.nahkd.spigot.sfaddons.MagicWands.MagicWands;

public class MagicWand extends ChargableItem {
	
	public static final SlimefunItemStack ITEM = new SlimefunItemStack(
			"MAGIC_WAND",
			Material.STICK,
			"&eMagic Wand",
			"",
			"&c&o&8\u21E8 &e\u26A1 &70 / 75 J", // r u kidding me?
			"",
			"&eClick &7to perform action",
			"&7Use &bWand Programmer &7if you want to",
			"&7reprogram this wand."
			);
	public static final ItemStack[] RECIPE = {
			null, new ItemStack(Material.GOLD_INGOT), null,
			null, SlimefunItems.SYNTHETIC_SAPPHIRE, null,
			null, SlimefunItems.BATTERY, null,
	};
	
	public MagicWand() {
		super(MagicWands.PLUGIN_CATEGORY, ITEM, RecipeType.ENHANCED_CRAFTING_TABLE, RECIPE);
	}
	
}
