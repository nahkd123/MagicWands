package me.nahkd.spigot.sfaddons.MagicWands.pub;

import java.util.UUID;

public class WandTempData {
	
	public UUID uuid;
	public Program program;
	
	public WandTempData(UUID uuid, Program program) {
		this.program = program;
		this.uuid = uuid;
	}
	
}
