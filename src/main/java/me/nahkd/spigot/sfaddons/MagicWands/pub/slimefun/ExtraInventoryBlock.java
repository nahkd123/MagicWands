package me.nahkd.spigot.sfaddons.MagicWands.pub.slimefun;

import java.util.function.Consumer;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import me.mrCookieSlime.Slimefun.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;

public interface ExtraInventoryBlock extends InventoryBlock {
	
	// kms
	/**
	 * createPreset with additions. See {@link InventoryBlock#createPreset(SlimefunItem, String, Consumer)}
	 */
	default void createPreset(SlimefunItem item, String title, Consumer<BlockMenuPreset> setup) {
        new BlockMenuPreset(item.getID(), title) {

            @Override
            public void init() {
                setup.accept(this);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                if (flow == ItemTransportFlow.INSERT) return getInputSlots();
                else return getOutputSlots();
            }

            @Override
            public boolean canOpen(Block b, Player p) {
                return p.hasPermission("slimefun.inventory.bypass") || (SlimefunPlugin.getProtectionManager().hasPermission(p, b.getLocation(), ProtectableAction.ACCESS_INVENTORIES) && Slimefun.hasUnlocked(p, item, false));
            }
            
            @Override
            public void newInstance(BlockMenu menu, Block b) {
            	ExtraInventoryBlock.this.newInstance(menu, b);
            }
        };
    }
	
	/**
	 * Indirect access to {@link BlockMenuPreset#newInstance(BlockMenu, Block)}.
	 * Also next time please put more JavaDocs, it will help other people, like "To
	 * change block data, you must override {@link BlockMenuPreset#newInstance(BlockMenu, Block)}"
	 * @param menu 
	 * @param b
	 */
	public void newInstance(BlockMenu menu, Block b);
	
}
