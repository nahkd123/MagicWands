package me.nahkd.spigot.sfaddons.MagicWands.pub;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.entity.Player;

public class Program {
	
	private static final int MAX_STEPS = 250;
	private static final double POWER_PER_STEP = 0.05;
	
	public final int maxStatements;
	public HashMap<StatementLocation, StatementInfo> statements;
	public int startX;
	public int startY;
	
	public Program(int maxStatement) {
		this.maxStatements = maxStatement;
		this.statements = new HashMap<StatementLocation, StatementInfo>();
		
		this.startX = 0;
		this.startY = 0;
	}
	public double runProgram(Player player, double powerLeft) {
		RuntimeInfo info = new RuntimeInfo();
		double powerCost = 0;
		boolean running = true;
		while (running) {
			if (powerLeft <= 0) {
				player.sendMessage("§7>> §cOut of power!");
				return powerCost;
			}
			if (info.executedSteps >= MAX_STEPS) {
				player.sendMessage("§7>> §cSteps exceed! (" + MAX_STEPS + ")");
				return powerCost;
			}
			final StatementInfo st = statements.get(info.currentLoc);
			if (st == null) {
				player.sendMessage("§7>> Empty statement slot. Program terminated");
				return powerCost;
			} else {
				running = !st.runStatement(player, info);
				info.step();
				powerCost += st.powerCost() + POWER_PER_STEP;
				powerLeft -= st.powerCost() + POWER_PER_STEP;
				if (!running) return powerCost;
			}
		}
		return powerCost;
	}
	
	public String[] toStringArray() {
		String[] out = new String[statements.size()];
		int i = 0;
		for (Entry<StatementLocation, StatementInfo> entry : statements.entrySet()) {
			out[i] = entry.getKey().x + ":" + entry.getKey().y + ":" + Statement.getStatements().indexOf(entry.getValue().statement) + ":" + entry.getValue().input;
			i++;
		}
		return out;
	}
	
	public static Program fromStringArray(String... program) {
		Program p = new Program(-1);
		for (String ss : program) {
			final String[] ssarr = ss.split("\\:");
			final int x = Integer.parseInt(ssarr[0]);
			final int y = Integer.parseInt(ssarr[1]);
			final int s = Integer.parseInt(ssarr[2]);
			StatementInfo sts = new StatementInfo(Statement.getStatements().get(s));
			sts.input = ss.substring(3 + ssarr[0].length() + ssarr[1].length() + ssarr[2].length());
			p.statements.put(new StatementLocation(x, y), sts);
		}
		return p;
	}
	
}
