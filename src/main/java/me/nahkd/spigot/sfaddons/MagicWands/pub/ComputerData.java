package me.nahkd.spigot.sfaddons.MagicWands.pub;

import org.bukkit.Location;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

public class ComputerData {
	
	public Location computerLoc;
	public Program program;
	public StatementLocation viewLocation;
	public BlockMenu menu;
	
	public boolean selected;
	public StatementLocation selectedLoc;
	
	public int statementsPage;
	
	public ComputerData(Location computerLoc, BlockMenu menu) {
		this.computerLoc = computerLoc;
		this.menu = menu;
		program = new Program(-1); // TODO change max statements
		viewLocation = new StatementLocation();
		selected = true; // TODO change this to false as soon as we've done testing
		selectedLoc = new StatementLocation();
		
		statementsPage = 0;
	}
	
}
