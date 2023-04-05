package com.goatee.tutorial.scripted;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.scripted.entity.ScriptDBCPlayer;

public class Player {
	
	public static Map<UUID, PlayerStats> PlayerStats = new HashMap<UUID, PlayerStats>();

	
	
	public class PlayerStats<T extends EntityPlayerMP>{
		public T player;
		ScriptDBCPlayer<T> playerSDBC = new ScriptDBCPlayer<T>(player);
		public static  boolean floating;


		public boolean isFlying(boolean bo) {
			return floating;
		}

	}
}
 