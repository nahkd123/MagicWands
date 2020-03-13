package me.nahkd.spigot.sfaddons.MagicWands.sf;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Lists.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.ChargableItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.ItemUseHandler;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.energy.ItemEnergy;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;
import me.nahkd.spigot.sfaddons.MagicWands.MagicWands;
import me.nahkd.spigot.sfaddons.MagicWands.pub.Program;
import me.nahkd.spigot.sfaddons.MagicWands.pub.WandTempData;

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
	protected MagicWands plugin;
	static HashMap<UUID, WandTempData> data;
	
	public MagicWand(MagicWands plugin) {
		super(MagicWands.PLUGIN_CATEGORY, ITEM, RecipeType.ENHANCED_CRAFTING_TABLE, RECIPE);
		this.plugin = plugin;
		data = new HashMap<UUID, WandTempData>();
		PersistentDataAPI.setInt(ITEM.getItemMeta(), KEY_REPROGRAM_COUNT, 25);
		
		addItemHandler(this);
	}

	@Override
	public void onRightClick(PlayerRightClickEvent e) {
		ItemStack item = e.getItem();
		ItemMeta meta = item.getItemMeta();
		if (meta.getPersistentDataContainer().has(plugin.KEY_PROGRAM, plugin.STRING_ARRAY)) {
			UUID uuid;
			if (!PersistentDataAPI.hasString(meta, plugin.KEY_UUID)) {
				PersistentDataAPI.setString(meta, plugin.KEY_UUID, (uuid = UUID.randomUUID()).toString());
				item.setItemMeta(meta);
			}
			else uuid = UUID.fromString(PersistentDataAPI.getString(meta, plugin.KEY_UUID));
			
			WandTempData data;
			if (MagicWand.data.containsKey(uuid)) data = MagicWand.data.get(uuid);
			else MagicWand.data.put(uuid, data = new WandTempData(uuid, Program.fromStringArray(meta.getPersistentDataContainer().get(plugin.KEY_PROGRAM, plugin.STRING_ARRAY))));
			
			final double pwd = data.program.runProgram(e.getPlayer(), ItemEnergy.getStoredEnergy(item));
			ItemEnergy.addStoredEnergy(item, (float) -pwd);
			
			if (e.getHand() == EquipmentSlot.HAND) e.getPlayer().getInventory().setItemInMainHand(item);
			else e.getPlayer().getInventory().setItemInOffHand(item);
		} else e.getPlayer().sendMessage("§7>> §cThis wand does not have any program inside");
	}
	
}
