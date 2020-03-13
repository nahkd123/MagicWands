package me.nahkd.spigot.sfaddons.MagicWands.sf;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;
import me.nahkd.spigot.sfaddons.MagicWands.MagicWands;

public class WandProgrammer extends SlimefunItem implements InventoryBlock {

	public static final SlimefunItemStack ITEM = new SlimefunItemStack(
			"WAND_PROGRAMMER",
			Material.IRON_BLOCK,
			"&bWand Programmer",
			"",
			"&fidk"
			);
	public static final ItemStack[] RECIPE = {
			SlimefunItems.DAMASCUS_STEEL_INGOT, SlimefunItems.ENDER_LUMP_2, SlimefunItems.DAMASCUS_STEEL_INGOT,
			SlimefunItems.ENDER_LUMP_2, SlimefunItems.GOLD_24K, SlimefunItems.ENDER_LUMP_2,
			SlimefunItems.DAMASCUS_STEEL_INGOT, SlimefunItems.ENDER_LUMP_2, SlimefunItems.DAMASCUS_STEEL_INGOT
	};
	
	protected MagicWands plugin;
	
	public WandProgrammer(MagicWands plugin) {
		super(MagicWands.PLUGIN_CATEGORY, ITEM, RecipeType.ENHANCED_CRAFTING_TABLE, RECIPE);
		this.plugin = plugin;
		
		createPreset(this, "&3Wand Programmer", this::preset);
		addItemHandler(new BlockTicker() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void tick(Block b, SlimefunItem item, Config data) {
				WandProgrammer.this.tick(b, item, data);
			}
			
			@Override
			public boolean isSynchronized() {return false;}
		});
	}
	
	void preset(BlockMenuPreset preset) {
		preset.setSize(54);
		for (int i = 0; i < 54; i++) if (!conflict(i)) preset.addItem(i, Computer.GUI_NOTHING, ChestMenuUtils.getEmptyClickHandler());
	}
	@SuppressWarnings("deprecation")
	void tick(Block b, SlimefunItem item, Config data) {
		BlockMenu inv = BlockStorage.getInventory(b);
		if (hasInputs(inv) && (inv.getItemInSlot(40) == null || inv.getItemInSlot(40).getType() == Material.AIR)) {
			ItemStack wand = inv.getItemInSlot(11);
			ItemStack drive = inv.getItemInSlot(15);
			
			ItemMeta wandMeta = wand.getItemMeta();
			String[] prg = drive.getItemMeta().getPersistentDataContainer().get(plugin.KEY_PROGRAM, plugin.STRING_ARRAY);
			wandMeta.getPersistentDataContainer().set(plugin.KEY_PROGRAM, plugin.STRING_ARRAY, prg);
			if (PersistentDataAPI.hasString(wandMeta, plugin.KEY_UUID)) MagicWand.data.remove(UUID.fromString(PersistentDataAPI.getString(wandMeta, plugin.KEY_UUID)));
			wand.setItemMeta(wandMeta);
			
			inv.replaceExistingItem(40, wand);
			inv.replaceExistingItem(11, null);
		}
	}
	
	private static final int[] conflicts = {11, 15, 40}; 
	private boolean conflict(int slot) {
		for (int s : conflicts) if (s == slot) return true;
		return false;
	}
	private boolean hasInputs(BlockMenu menu) {
		return plugin.MAGIC_WAND.isItem(menu.getItemInSlot(11)) && plugin.HARD_DRIVE.isItem(menu.getItemInSlot(15));
	}

	@Override
	public int[] getInputSlots() {
		return new int[] {11, 15};
	}

	@Override
	public int[] getOutputSlots() {
		return new int[] {40};
	}

}
