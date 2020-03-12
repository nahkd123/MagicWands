package me.nahkd.spigot.sfaddons.MagicWands.sf;

import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu.MenuClickHandler;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.nahkd.spigot.sfaddons.MagicWands.MagicWands;
import me.nahkd.spigot.sfaddons.MagicWands.pub.ComputerData;
import me.nahkd.spigot.sfaddons.MagicWands.pub.Program;
import me.nahkd.spigot.sfaddons.MagicWands.pub.Statement;
import me.nahkd.spigot.sfaddons.MagicWands.pub.StatementLocation;
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
	public static final ItemStack GUI_SELECTED = new CustomItem(
			Material.YELLOW_STAINED_GLASS_PANE,
			"&eSelected",
			"",
			"&fYou're currently selected",
			"&fthis slot."
	);
	// Is there is other way to store data? I think we'll have to use "useless" item
	public static final ItemStack GUI_NOTHING = new CustomItem(Material.BLACK_STAINED_GLASS_PANE, " ");
	// #End of GUI
	
	// HashMap<Location, ComputerData> datas;
	
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
		
		// this.datas = new HashMap<Location, ComputerData>();
		createPreset(this, "&4Program Editor", this::constructGUI);
	}
	
	void constructGUI(BlockMenuPreset preset) {
		preset.setSize(54);
		
		for (int i = 0; i < 3; i++) {
			preset.addItem(i, GUI_PREVIOUS);
			preset.addItem(i + 36, GUI_NEXT);
		}
		preset.addItem(47, GUI_NOTHING, ChestMenuUtils.getEmptyClickHandler());
	}

	@Override
	public void newInstance(BlockMenu menu, Block b) {
		ComputerData data = new ComputerData(b.getLocation(), menu);
		showAvailableStatements(menu, data.statementsPage);
		showCurrentProgram(menu, data);
		
		// Here we'll add click handers. We can't use menu.addItem, we must use menu.toInventory().setItem()
		for (int i = 0; i < 3; i++) {
			menu.addMenuClickHandler(i, new MenuClickHandler() {
				@Override
				public boolean onClick(Player p, int slot, ItemStack item, ClickAction action) {
					// Previous button
					if (data.statementsPage > 0) {
						data.statementsPage--;
						showAvailableStatements(menu, data.statementsPage);
					}
					return false;
				}
			});
			menu.addMenuClickHandler(i + 36, new MenuClickHandler() {
				@Override
				public boolean onClick(Player p, int slot, ItemStack item, ClickAction action) {
					// Next button
					data.statementsPage++;
					showAvailableStatements(menu, data.statementsPage);
					return false;
				}
			});
			for (int j = 0; j < 3; j++) {
				final int slot = (j + 1) * 9 + i;
				final int jj = j, ii = i;
				menu.addMenuClickHandler(slot, new MenuClickHandler() {
					@Override
					public boolean onClick(Player p, int slot, ItemStack item, ClickAction action) {
						click_statements(p, slot, item, action, (jj * 3) + ii, data);
						return false;
					}
				});
			}
		}
		
		for (int y = 0; y < 6; y++) for (int x = 0; x < 6; x++) {
			final int slot = (y * 9) + (x + 3);
			final int xx = x, yy = y;
			menu.addMenuClickHandler(slot, new MenuClickHandler() {
				@Override
				public boolean onClick(Player p, int slot, ItemStack item, ClickAction action) {
					click_program(p, slot, item, action, new StatementLocation(xx + data.viewLocation.x, yy + data.viewLocation.y), data);
					return false;
				}
			});
		}
	}
	public void showAvailableStatements(BlockMenu menu, int page) {
		for (int i = 0; i < 9; i++) {
			final int realSlot = i % 3 + ((i / 3) * 9) + 9;
			final int arrIndex = i + (page * 9);
			menu.replaceExistingItem(realSlot, arrIndex < Statement.getStatements().size()? Statement.getStatements().get(arrIndex).displayStatement() : GUI_NOTHING);
		}
	}
	public void showCurrentProgram(BlockMenu menu, ComputerData data) {
		StatementLocation loc = new StatementLocation();
		for (int y = 0; y < 6; y++) for (int x = 0; x < 6; x++) {
			final int displaySlot = y * 9 + (x + 3);
			loc.x = data.viewLocation.x + x; loc.y = data.viewLocation.y + y;
			if (loc.x == data.selectedLoc.x && loc.y == data.selectedLoc.y && data.selected) menu.replaceExistingItem(displaySlot, GUI_SELECTED);
			else if (data.program.statements.containsKey(loc)) menu.replaceExistingItem(displaySlot, data.program.statements.get(loc).displayStatement());
			else menu.replaceExistingItem(displaySlot, null);
		}
	}
	public void click_statements(Player p, int slot, ItemStack item, ClickAction action, int statementIndex, ComputerData data) {
		if (!data.selected) p.sendMessage("§7>> §cYou haven't selected a slot, have you?");
		data.selected = false;
		data.program.statements.put(new StatementLocation(data.selectedLoc), Statement.getStatements().get(statementIndex));
		showCurrentProgram(data.menu, data);
	}
	public void click_program(Player p, int slot, ItemStack item, ClickAction action, StatementLocation loc, ComputerData data) {
		if (loc.equals(data.selectedLoc) && data.selected) {
			data.selected = false;
			showCurrentProgram(data.menu, data);
		} else {
			data.selectedLoc.from(loc);
			data.selected = true;
			showCurrentProgram(data.menu, data);
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
