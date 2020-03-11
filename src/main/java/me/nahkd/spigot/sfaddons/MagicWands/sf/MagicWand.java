package me.nahkd.spigot.sfaddons.MagicWands.sf;

import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.ChargableItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.ItemUseHandler;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;
import me.nahkd.spigot.sfaddons.MagicWands.MagicWands;

public class MagicWand extends ChargableItem implements ItemUseHandler {
	
	public static final SlimefunItemStack ITEM = new SlimefunItemStack(
			"MAGIC_WAND",
			Material.STICK,
			"&eMagic Wand",
			"",
			"&c&o&8\u21E8 &e\u26A1 &70 / 75 J", // r u kidding me?
			"",
			"&eRight Click &7to perform action",
			"&7Use &3Wand Programmer &7if you",
			"&7want to reprogram this wand."
			);
	public static final ItemStack[] RECIPE = {
			null, new ItemStack(Material.GOLD_INGOT), null,
			null, SlimefunItems.SYNTHETIC_SAPPHIRE, null,
			null, SlimefunItems.BATTERY, null,
	};
	
	public static NamespacedKey KEY_REPROGRAM_COUNT;
	
	public MagicWand() {
		super(MagicWands.PLUGIN_CATEGORY, ITEM, RecipeType.ENHANCED_CRAFTING_TABLE, RECIPE);
		PersistentDataAPI.setInt(ITEM.getItemMeta(), KEY_REPROGRAM_COUNT, 25);
		
		addItemHandler(this);
	}

	@Override
	public void onRightClick(PlayerRightClickEvent e) {
		if (isItem(e.getItem())) {
			int reprogramCount = PersistentDataAPI.getInt(e.getItem().getItemMeta(), KEY_REPROGRAM_COUNT);
		}
	}
	
}
