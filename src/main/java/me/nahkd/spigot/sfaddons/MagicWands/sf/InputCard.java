package me.nahkd.spigot.sfaddons.MagicWands.sf;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.nahkd.spigot.sfaddons.MagicWands.MagicWands;

public class InputCard extends SlimefunItem {
	
	public static final SlimefunItemStack ITEM = new SlimefunItemStack(
			"INPUT_CARD",
			Material.PAPER,
			"&fInput Card",
			"",
			"&fSend input to computer.",
			"",
			"&eChat &7a value to set input",
			"&eCurrent input:",
			"&7&o<empty>"
			);
	public static final ItemStack[] RECIPE = {
			null, null, null,
			new ItemStack(Material.STONE_BUTTON), SlimefunItems.BASIC_CIRCUIT_BOARD, null,
			SlimefunItems.ALUMINUM_INGOT, SlimefunItems.ALUMINUM_INGOT, SlimefunItems.ALUMINUM_INGOT
	};
	
	public InputCard() {
		super(MagicWands.PLUGIN_CATEGORY, ITEM, RecipeType.ENHANCED_CRAFTING_TABLE, RECIPE);
	}
	
	public static ItemStack getInputCard(String input) {
		ItemStack out = new ItemStack(ITEM);
		ItemMeta m = out.getItemMeta();
		List<String> lore = m.getLore();
		lore.set(5, "§7§o" + input);
		m.setLore(lore);
		out.setItemMeta(m);
		return out;
	}
	public static String getInput(ItemStack inputCard) {
		ItemMeta m = inputCard.getItemMeta();
		return m.getLore().get(5).substring(4);
	}
	
}
