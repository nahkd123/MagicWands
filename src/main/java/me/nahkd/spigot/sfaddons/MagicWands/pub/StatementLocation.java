package me.nahkd.spigot.sfaddons.MagicWands.pub;

public class StatementLocation {
	
	public int x;
	public int y;
	
	public StatementLocation(final int x, final int y) {this.x = x; this.y = y;}
	public StatementLocation() {this(0, 0);}
	public StatementLocation(StatementLocation loc) {from(loc);}
	
	public void from(StatementLocation loc) {
		x = loc.x;
		y = loc.y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof StatementLocation)) return false;
		StatementLocation ll = (StatementLocation) obj;
		return ll.x == x && ll.y == y;
	}
	@Override
	public int hashCode() {
		return x * 10000 + y;
	}
	
}
