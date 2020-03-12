package me.nahkd.spigot.sfaddons.MagicWands;

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
import me.nahkd.spigot.sfaddons.MagicWands.pub.statements.StatementTerminateProgram;
import me.nahkd.spigot.sfaddons.MagicWands.sf.Computer;
import me.nahkd.spigot.sfaddons.MagicWands.sf.HardDrive;
import me.nahkd.spigot.sfaddons.MagicWands.sf.MagicWand;

public class MagicWands extends JavaPlugin implements SlimefunAddon {
	
	public static MagicWands pluginInstance;
	
	public static final String CATEGORY_NAME = "§bMagicWands";
	
	public static Category PLUGIN_CATEGORY;
	public static MagicWand MAGIC_WAND;
	public static HardDrive HARD_DRIVE;
	public static Computer COMPUTER;
	
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
		MagicWand.KEY_REPROGRAM_COUNT = new NamespacedKey(this, "reprogramCount");
		PLUGIN_CATEGORY = new Category(new NamespacedKey(pluginInstance, "magicwands"), new CustomItem(Material.STICK, CATEGORY_NAME));
		MAGIC_WAND = new MagicWand(); MAGIC_WAND.register(this);
		HARD_DRIVE = new HardDrive(); HARD_DRIVE.register(this);
		COMPUTER = new Computer(); COMPUTER.register(this);
		
		// Setting up statements and stuffs
		new StatementTerminateProgram().register();
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
