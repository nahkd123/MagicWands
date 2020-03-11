package me.nahkd.spigot.sfaddons.MagicWands.sf;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.nahkd.spigot.sfaddons.MagicWands.MagicWands;

public class HardDrive extends SlimefunItem {
	
	public static final SlimefunItemStack ITEM = new SlimefunItemStack(
			"HARD_DRIVE",
			Material.BOOK,
			"&eHard Drive",
			"",
			"&fStore program on hard drive",
			"&fso you can't lose any awesome",
			"&fprograms, right?",
			"",
			"&e100 Writes &7left"
			);
	public static final ItemStack[] RECIPE = {
			SlimefunItems.ZINC_INGOT, SlimefunItems.ZINC_INGOT, SlimefunItems.ZINC_INGOT,
			SlimefunItems.SYNTHETIC_SAPPHIRE, SlimefunItems.SYNTHETIC_SAPPHIRE, SlimefunItems.SYNTHETIC_SAPPHIRE,
			SlimefunItems.ZINC_INGOT, SlimefunItems.MAGNESIUM_INGOT, SlimefunItems.ZINC_INGOT,
	};
	
	public HardDrive() {
		super(MagicWands.PLUGIN_CATEGORY, ITEM, RecipeType.ENHANCED_CRAFTING_TABLE, RECIPE);
	}
	
}
