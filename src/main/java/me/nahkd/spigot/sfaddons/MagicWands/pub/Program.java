package me.nahkd.spigot.sfaddons.MagicWands.pub;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Program {
	
	public final int maxStatements;
	public ArrayList<Statement> statements;
	
	public Program(int maxStatement) {
		this.maxStatements = maxStatement;
		this.statements = new ArrayList<Statement>();
	}
	public void runProgram(Player player) {
		run(player, statements);
	}
	
	private boolean run(Player player, ArrayList<Statement> statements) {
		for (Statement statement : statements) if (statement.runStatement(player)) return true;
		return false;
	}
	
}
