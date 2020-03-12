package me.nahkd.spigot.sfaddons.MagicWands.sf;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu.MenuClickHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockUseHandler;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.nahkd.spigot.sfaddons.MagicWands.MagicWands;
import me.nahkd.spigot.sfaddons.MagicWands.pub.slimefun.ExtraInventoryBlock;

public class Computer extends SlimefunItem implements ExtraInventoryBlock, EnergyNetComponent {
	
	// #GUI
	public static final ItemStack GUI_ADDSTATEMENT = new CustomItem(
			Material.LIME_STAINED_GLASS_PANE,
			"&a+ Add Statement",
			"",
			"&fAdd a statement in here"
			);
	public static final ItemStack GUI_BACK = new CustomItem(
			Material.ARROW,
			"&e<< Back",
			"",
			"&fGo back"
			);
	public static final ItemStack GUI_PREVIOUS = new CustomItem(
			Material.YELLOW_STAINED_GLASS_PANE,
			"&e<< Previous",
			"",
			"&fGo to previous page"
			);
	public static final ItemStack GUI_NEXT = new CustomItem(
			Material.YELLOW_STAINED_GLASS_PANE,
			"&eNext >>",
			"",
			"&fGo to next page"
			);
	// #End of GUI BlockBreakHandler
	
	public static final SlimefunItemStack ITEM = new SlimefunItemStack(
			"COMPUTER",
			Material.IRON_BLOCK,
			"&eComputer",
			"",
			"&fComputer is important, especially",
			"&ffor programmers. Do you want to",
			"&fbecome a epic programmer?",
			"",
			LoreBuilder.powerBuffer(128),
			LoreBuilder.power(16, "/Hard drive write")
			);
	public static final ItemStack[] RECIPE = {
			SlimefunItems.STEEL_INGOT, new ItemStack(Material.GLASS_PANE), SlimefunItems.STEEL_INGOT,
			SlimefunItems.STEEL_INGOT, SlimefunItems.ADVANCED_CIRCUIT_BOARD, SlimefunItems.STEEL_INGOT,
			SlimefunItems.STEEL_INGOT, SlimefunItems.POWER_CRYSTAL, SlimefunItems.STEEL_INGOT,
	};
	
	public Computer() {
		super(MagicWands.PLUGIN_CATEGORY, ITEM, RecipeType.ENHANCED_CRAFTING_TABLE, RECIPE);
		
		createPreset(this, "&4Program Editor", this::constructGUI);
	}
	
	void constructGUI(BlockMenuPreset preset) {
		preset.setSize(54);
		// Put GUI in here uwu
	}

	@Override
	public void newInstance(BlockMenu menu, Block b) {
		b.setType(Material.STONE); // this works
		for (int i = 0; i < 3; i++) {
			menu.addItem(i, GUI_PREVIOUS, new MenuClickHandler() {
				
				@Override
				public boolean onClick(Player arg0, int slot, ItemStack arg2, ClickAction arg3) {
					b.setType(Material.DIAMOND_BLOCK); // this works tho
					menu.addItem(slot, GUI_PREVIOUS); // this doesn't work
					return false;
				}
			});
			menu.addItem(i + 36, GUI_NEXT);
		}
	}

	@Override
	public EnergyNetComponentType getEnergyComponentType() {return EnergyNetComponentType.CONSUMER;}

	@Override
	public int getCapacity() {return 128;}

	@Override
	public int[] getInputSlots() {return new int [] {};}

	@Override
	public int[] getOutputSlots() {return new int [] {};}
	
}
