package me.nahkd.spigot.sfaddons.MagicWands.pub;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Statement {
	
	/**
	 * Called from {@link Program}. Return true to stop program
	 * @param player The player executed this program
	 * @return true to stop program, otherwise false
	 */
	public abstract boolean runStatement(Player player, RuntimeInfo info);
	public abstract ItemStack displayStatement();
	public void register() {register(this);}
	
	private static ArrayList<Statement> statements;
	public static void register(Statement statement) {
		if (statements == null) statements = new ArrayList<Statement>();
		statements.add(statement);
	}
	public static ArrayList<Statement> getStatements() {
		if (statements == null) statements = new ArrayList<Statement>();
		return statements;
	}
	
}
