package me.nahkd.spigot.sfaddons.MagicWands.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import me.nahkd.spigot.sfaddons.MagicWands.MagicWands;
import me.nahkd.spigot.sfaddons.MagicWands.sf.InputCard;

public class PlayerListener implements Listener {
	
	MagicWands plugin;
	
	public PlayerListener(MagicWands plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
	public void chat(AsyncPlayerChatEvent event) {
		ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
		if (item != null && item.getType() != Material.AIR && plugin.INPUT_CARD.isItem(item)) {
			if (event.isAsynchronous()) Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					syncEventChat(event);
				}
			}); else syncEventChat(event);
			event.setCancelled(true);
		}
	}
	
	private void syncEventChat(AsyncPlayerChatEvent event) {
		ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
		if (item != null && item.getType() != Material.AIR && plugin.INPUT_CARD.isItem(item)) {
			event.getPlayer().getInventory().setItemInMainHand(InputCard.getInputCard(event.getMessage()));
		}
	}
	
}
