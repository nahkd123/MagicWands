package me.nahkd.spigot.sfaddons.MagicWands.sf;

import org.bukkit.Material;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.nahkd.spigot.sfaddons.MagicWands.MagicWands;

public class MagicWand extends SlimefunItem {
	
	public static final SlimefunItemStack ITEM = new SlimefunItemStack(
			"MAGIC_WAND",
			Material.STICK,
			"&eMagic Wand",
			"",
			"&fA programmable wand...",
			"",
			"&eClick &7to perform action",
			"&7Use &bWand Programmer &7if you want to",
			"&7reprogram this wand."
			);
	
	public MagicWand() {
		super(MagicWands.PLUGIN_CATEGORY, ITEM, RecipeType.ENHANCED_CRAFTING_TABLE, null);
	}
	
}
