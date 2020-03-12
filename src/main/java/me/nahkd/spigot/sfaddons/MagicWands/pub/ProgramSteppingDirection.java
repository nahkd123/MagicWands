package me.nahkd.spigot.sfaddons.MagicWands.pub;

public enum ProgramSteppingDirection {
	
	UP(0, -1),
	DOWN(0, 1),
	LEFT(-1, 0),
	RIGHT(1, 0);
	
	public final int addX;
	public final int addY;
	private ProgramSteppingDirection(int addX, int addY) {
		this.addX = addX;
		this.addY = addY;
	}
	
}
