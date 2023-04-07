package com.goatee.tutorial.scripted;

import java.util.UUID;
import java.util.WeakHashMap;


public class Player {

	@SuppressWarnings("rawtypes")
	private static WeakHashMap<UUID, PlayerStats> PlayerStats = new WeakHashMap<UUID, PlayerStats>();
	
	
	@SuppressWarnings("rawtypes")
	public static WeakHashMap<UUID, PlayerStats> getAllPlayerStats(){
		return PlayerStats;
	}

}
