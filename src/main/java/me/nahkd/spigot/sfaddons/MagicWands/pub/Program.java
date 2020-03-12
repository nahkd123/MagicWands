package me.nahkd.spigot.sfaddons.MagicWands.pub;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class Program {
	
	public final int maxStatements;
	public HashMap<StatementLocation, Statement> statements;
	public int startX;
	public int startY;
	
	public Program(int maxStatement) {
		this.maxStatements = maxStatement;
		this.statements = new HashMap<StatementLocation, Statement>();
		
		this.startX = 0;
		this.startY = 0;
	}
	public void runProgram(Player player) {
		RuntimeInfo info = new RuntimeInfo();
		boolean running = true;
		while (running) {
			final Statement st = statements.get(info.currentLoc);
			if (st == null) running = false;
			else {
				running = !st.runStatement(player, info);
				info.step();
			}
		}
		player.sendMessage("§7>> §aProgram finished!");
	}
	
}
