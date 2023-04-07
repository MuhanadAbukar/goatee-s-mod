package com.goatee.tutorial.scripted;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class Player {

	@SuppressWarnings("rawtypes")
	private static Map<UUID, PlayerStats> PlayerStats = new HashMap<UUID, PlayerStats>();
	
	
	@SuppressWarnings("rawtypes")
	public static Map<UUID, PlayerStats> getAllPlayerStats(){
		return PlayerStats;
	}

}
