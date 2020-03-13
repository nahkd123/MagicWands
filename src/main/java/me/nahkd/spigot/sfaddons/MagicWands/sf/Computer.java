package me.nahkd.spigot.sfaddons.MagicWands.sf;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
import me.mrCookieSlime.Slimefun.api.energy.ChargableBlock;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.nahkd.spigot.sfaddons.MagicWands.MagicWands;
import me.nahkd.spigot.sfaddons.MagicWands.pub.ComputerData;
import me.nahkd.spigot.sfaddons.MagicWands.pub.Program;
import me.nahkd.spigot.sfaddons.MagicWands.pub.Statement;
import me.nahkd.spigot.sfaddons.MagicWands.pub.StatementInfo;
import me.nahkd.spigot.sfaddons.MagicWands.pub.StatementLocation;
import me.nahkd.spigot.sfaddons.MagicWands.pub.persistent.StringArrayItemTagType;
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
	public static final ItemStack GUI_NOTHING = new CustomItem(Material.BLACK_STAINED_GLASS_PANE, " ");
	public static final ItemStack GUI_PLACEHOLDER = new CustomItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ");
	public static final ItemStack GUI_SAVE = new CustomItem(Material.LIME_STAINED_GLASS_PANE, "&aSave", "", "&fSave program by writting to", "&fhard drive", "", "&7You have to put the hard", "&7drive on 2nd slot.");
	public static final ItemStack GUI_LOAD = new CustomItem(Material.YELLOW_STAINED_GLASS_PANE, "&eLoad", "", "&fLoad program from your", "&fhard drive", "", "&7You have to put the hard", "&7drive on 2nd slot");
	
	public static final ItemStack GUI_INPUT = new CustomItem(Material.PAPER, "&fInput", "", "&fPlace a Input Card in here", "&fto change input.");
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
	
	protected MagicWands plugin;
	
	public Computer(MagicWands plugin) {
		super(MagicWands.PLUGIN_CATEGORY, ITEM, RecipeType.ENHANCED_CRAFTING_TABLE, RECIPE);
		
		this.plugin = plugin;
		// this.datas = new HashMap<Location, ComputerData>();
		createPreset(this, "&4Program Editor", this::constructGUI);
	}
	
	void constructGUI(BlockMenuPreset preset) {
		preset.setSize(54);
		
		preset.addItem(0, GUI_INPUT);
		preset.addItem(1, GUI_NOTHING);
		preset.addItem(2, GUI_NOTHING);
		
		preset.addItem(36, GUI_PREVIOUS);
		preset.addItem(37, GUI_NOTHING);
		preset.addItem(38, GUI_NEXT);
		
		preset.addItem(45, GUI_SAVE);
		preset.addItem(47, GUI_LOAD);
	}

	@Override
	public void newInstance(BlockMenu menu, Block b) {
		ComputerData data = new ComputerData(b.getLocation(), menu);
		showAvailableStatements(menu, data.statementsPage);
		showCurrentProgram(menu, data);
		
		// Here we'll add click handers. We can't use menu.addItem, we must use menu.toInventory().setItem()
		menu.addMenuClickHandler(0, new MenuClickHandler() {
			@Override
			public boolean onClick(Player p, int slot, ItemStack item, ClickAction action) {
				ItemStack inputCard = p.getItemOnCursor();
				if (inputCard == null || inputCard.getType() == Material.AIR || !plugin.INPUT_CARD.isItem(inputCard)) {
					p.sendMessage("§7>> You must put Input Card on your cursor first!");
					return false;
				}
				if (!data.selected) {
					p.sendMessage("§7>> §cYou haven't selected a slot, have you?");
					return false;
				}
				if (data.program.statements.containsKey(data.selectedLoc)) {
					data.program.statements.get(data.selectedLoc).input = InputCard.getInput(inputCard);
					showCurrentProgram(menu, data);
				} else p.sendMessage("§7>> §cThat selected slot is empty!");
				return false;
			}
		});
		
		menu.addMenuClickHandler(36, new MenuClickHandler() {
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
		menu.addMenuClickHandler(38, new MenuClickHandler() {
			@Override
			public boolean onClick(Player p, int slot, ItemStack item, ClickAction action) {
				// Next button
				data.statementsPage++;
				showAvailableStatements(menu, data.statementsPage);
				return false;
			}
		});
			
		for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) {
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
		
		menu.addMenuClickHandler(45, new MenuClickHandler() {
			@Override
			public boolean onClick(Player p, int slot, ItemStack item, ClickAction action) {
				saveProgram(p, slot, item, action, data);
				return false;
			}
		});
		menu.addMenuClickHandler(47, new MenuClickHandler() {
			@Override
			public boolean onClick(Player p, int slot, ItemStack item, ClickAction action) {
				loadProgram(p, slot, item, action, data);
				return false;
			}
		});
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
			ItemStack out;
			loc.x = data.viewLocation.x + x; loc.y = data.viewLocation.y + y;
			if (data.program.statements.containsKey(loc)) out = data.program.statements.get(loc).statement.displayStatement();
			else out =  GUI_PLACEHOLDER;
			if (loc.x == data.selectedLoc.x && loc.y == data.selectedLoc.y && data.selected) {
				out.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
			}
			
			menu.replaceExistingItem(displaySlot, out);
		}
	}
	public void click_statements(Player p, int slot, ItemStack item, ClickAction action, int statementIndex, ComputerData data) {
		if (!data.selected) {
			p.sendMessage("§7>> §cYou haven't selected a slot, have you?");
			return;
		}
		if (data.program.statements.containsKey(data.selectedLoc)) data.program.statements.get(data.selectedLoc).statement = Statement.getStatements().get(statementIndex);
		else data.program.statements.put(new StatementLocation(data.selectedLoc), new StatementInfo(Statement.getStatements().get(statementIndex)));
		showCurrentProgram(data.menu, data);
	}
	public void click_program(Player p, int slot, ItemStack item, ClickAction action, StatementLocation loc, ComputerData data) {
		if (action.isRightClicked()) {
			data.selected = false;
			if (data.program.statements.containsKey(loc)) data.program.statements.remove(loc);
			showCurrentProgram(data.menu, data);
		} else {
			if (loc.equals(data.selectedLoc) && data.selected) {
				data.selected = false;
				showCurrentProgram(data.menu, data);
			} else {
				data.selectedLoc.from(loc);
				data.selected = true;
				showCurrentProgram(data.menu, data);
			}
		}
	}
	public void saveProgram(Player p, int slot, ItemStack item, ClickAction action, ComputerData data) {
		ItemStack harddrive = data.menu.getItemInSlot(46);
		if (ChargableBlock.getCharge(data.computerLoc) < 16) {
			p.sendMessage("§7>> §cOut of power!");
			return;
		}
		if (harddrive == null || harddrive.getType() == Material.AIR || !plugin.HARD_DRIVE.isItem(harddrive)) {
			p.sendMessage("§7>> §cI don't think that's a hard drive...");
			return;
		}
		
		ItemMeta meta = harddrive.getItemMeta();
		final int writeLimit = Integer.parseInt(meta.getLore().get(5).split(" ")[0].substring(2));
		if (writeLimit <= 0) {
			p.sendMessage("§7>> §cYou've exceed the write limit of the hard drive :(");
			return;
		}
		meta.getPersistentDataContainer().set(plugin.KEY_PROGRAM, plugin.STRING_ARRAY, data.program.toStringArray());
		List<String> lore = meta.getLore();
		lore.set(5, "§e" + (writeLimit - 1) + " Writes §7left");
		meta.setLore(lore);
		harddrive.setItemMeta(meta);
		
		data.menu.replaceExistingItem(46, harddrive);
		ChargableBlock.addCharge(data.computerLoc, -16);
	}
	public void loadProgram(Player p, int slot, ItemStack item, ClickAction action, ComputerData data) {
		ItemStack harddrive = data.menu.getItemInSlot(46);
		if (harddrive == null || harddrive.getType() == Material.AIR || !plugin.HARD_DRIVE.isItem(harddrive)) {
			p.sendMessage("§7>> §cI don't think that's a hard drive...");
			return;
		}
		
		ItemMeta meta = harddrive.getItemMeta();
		data.program = Program.fromStringArray(meta.getPersistentDataContainer().get(plugin.KEY_PROGRAM, plugin.STRING_ARRAY));
		showCurrentProgram(data.menu, data);
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
