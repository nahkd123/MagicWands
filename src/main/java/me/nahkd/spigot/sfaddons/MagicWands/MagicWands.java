package me.nahkd.spigot.sfaddons.MagicWands;

import java.nio.charset.Charset;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.bstats.bukkit.Metrics;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.nahkd.spigot.sfaddons.MagicWands.events.PlayerListener;
import me.nahkd.spigot.sfaddons.MagicWands.pub.persistent.StringArrayItemTagType;
import me.nahkd.spigot.sfaddons.MagicWands.pub.statements.StatementDown;
import me.nahkd.spigot.sfaddons.MagicWands.pub.statements.StatementInfo;
import me.nahkd.spigot.sfaddons.MagicWands.pub.statements.StatementTerminate;
import me.nahkd.spigot.sfaddons.MagicWands.pub.statements.StatementUp;
import me.nahkd.spigot.sfaddons.MagicWands.sf.Computer;
import me.nahkd.spigot.sfaddons.MagicWands.sf.HardDrive;
import me.nahkd.spigot.sfaddons.MagicWands.sf.InputCard;
import me.nahkd.spigot.sfaddons.MagicWands.sf.MagicWand;
import me.nahkd.spigot.sfaddons.MagicWands.sf.WandProgrammer;

public class MagicWands extends JavaPlugin implements SlimefunAddon {
	
	public static MagicWands pluginInstance;
	
	public static final String CATEGORY_NAME = "§bMagicWands";
	
	public static Category PLUGIN_CATEGORY;
	public MagicWand MAGIC_WAND;
	public HardDrive HARD_DRIVE;
	public Computer COMPUTER;
	public WandProgrammer WAND_PROGRAMMER;
	public InputCard INPUT_CARD;

	public NamespacedKey KEY_PROGRAM;
	public NamespacedKey KEY_UUID;
	
	public StringArrayItemTagType STRING_ARRAY;
	
	@Override
	public void onEnable() {
		pluginInstance = this;
		
		// Read something from your config.yml
		Config cfg = new Config(this);
		
//		if (cfg.getBoolean("options.auto-update")) {
//			// You could start an Auto-Updater for example
//		}SlimefunItems
		
		// Slimefun4 also already comes with a bundled version of bStats
		// You can use bStats to collect usage data about your plugin
		// More info: https://bstats.org/getting-started
		// Set bStatsId to the id of your plugin
//		int bStatsId = -1;
//		new Metrics(this, bStatsId);
		
		// Create a new Category
		// This Category will use this ItemStack
//		ItemStack categoryItem = new CustomItem(Material.DIAMOND, "&4Addon Category", "", "&a> Click to open");
//		
//		// Give your Category a unique id.
//		NamespacedKey categoryId = new NamespacedKey(this, "addon_category");
//		Category category = new Category(categoryId, categoryItem);
//		
//		// Create a new Slimefun ItemStack
//		// This class has many constructors, it is very important that you give each item a unique id.
//		SlimefunItemStack slimefunItem = new SlimefunItemStack("COOL_DIAMOND", Material.DIAMOND, "&4Cool Diamond", "&c+20% Coolness");
//		
//		// The Recipe is an ItemStack Array with a length of 9.
//		// It represents a Shaped Recipe in a 3x3 crafting grid
//		// The machine in which this recipe is crafted in is specified further down
//		ItemStack[] recipe = {
//			new ItemStack(Material.EMERALD), null, new ItemStack(Material.EMERALD),
//			null, new ItemStack(Material.DIAMOND), null,
//			new ItemStack(Material.EMERALD), null, new ItemStack(Material.EMERALD)
//		};
//		
//		// Now you just have to register the item
//		// RecipeType.ENHANCED_CRAFTING_TABLE refers to the machine in which this item is crafted in.
//		// Recipy Types from Slimefun itself will automatically add the recipe to that machine
//		SlimefunItem item = new SlimefunItem(category, slimefunItem, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
//		item.register(this);
		
		// NamespacedKey
		KEY_PROGRAM = new NamespacedKey(this, "program");
		KEY_UUID = new NamespacedKey(this, "uuid");
		
		// Persistent thing
		STRING_ARRAY = new StringArrayItemTagType(Charset.forName("UTF-8"));
		
		// Slimefun
		MagicWand.KEY_REPROGRAM_COUNT = new NamespacedKey(this, "reprogramCount");
		PLUGIN_CATEGORY = new Category(new NamespacedKey(pluginInstance, "magicwands"), new CustomItem(Material.STICK, CATEGORY_NAME));
		MAGIC_WAND = new MagicWand(this); MAGIC_WAND.register(this);
		HARD_DRIVE = new HardDrive(); HARD_DRIVE.register(this);
		COMPUTER = new Computer(this); COMPUTER.register(this);
		WAND_PROGRAMMER = new WandProgrammer(this); WAND_PROGRAMMER.register(this);
		INPUT_CARD = new InputCard(); INPUT_CARD.register(this);
		
		// Setting up statements and stuffs
		new StatementTerminate().register();
		new StatementUp().register();
		new StatementDown().register();
		new StatementInfo().register();
		
		// Events
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
	}
	
	@Override
	public void onDisable() {
		// Logic for disabling the plugin...
	}

	@Override
	public String getBugTrackerURL() {
		// You can return a link to your Bug Tracker instead of null here
		return null;
	}

	@Override
	public JavaPlugin getJavaPlugin() {
		return this;
	}

}
