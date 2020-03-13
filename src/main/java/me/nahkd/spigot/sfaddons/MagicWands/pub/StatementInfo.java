package me.nahkd.spigot.sfaddons.MagicWands.pub;

import org.bukkit.entity.Player;

public class StatementInfo {
	
	public String input;
	public Statement statement;
	
	public StatementInfo(Statement statement) {
		this.input = "";
		this.statement = statement;
	}
	
	public boolean runStatement(Player player, RuntimeInfo info) {
		return statement.runStatement(player, info, input);
	}
	public double powerCost() {
		return statement.powerCost(input);
	}
	
}
