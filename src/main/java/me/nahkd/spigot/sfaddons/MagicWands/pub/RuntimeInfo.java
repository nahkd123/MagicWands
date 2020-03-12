package me.nahkd.spigot.sfaddons.MagicWands.pub;

public class RuntimeInfo {
	
	public int executedSteps;
	public StatementLocation currentLoc;
	public ProgramSteppingDirection direction;
	
	public RuntimeInfo() {
		executedSteps = 0;
		currentLoc = new StatementLocation();
		direction = ProgramSteppingDirection.RIGHT;
	}
	public void step() {
		currentLoc.x += direction.addX;
		currentLoc.y += direction.addY;
		executedSteps++;
	}
	
}
