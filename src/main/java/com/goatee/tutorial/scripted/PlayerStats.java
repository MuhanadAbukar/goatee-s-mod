package com.goatee.tutorial.scripted;

import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.scripted.entity.ScriptPlayer;

//stores any stat you want for whichever player. This is always the same for each player, instantiated on login. Recall using ScriptDBCPlayer.getPlayerStats()
public class PlayerStats<T extends EntityPlayerMP> {
	@SuppressWarnings("rawtypes")
	public ScriptPlayer player;
	private boolean floating;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PlayerStats(EntityPlayerMP player) {
		this.player = new ScriptPlayer(player);
		init();
	}

	public boolean isFlying() {
		return floating;
	}

	public void setFlying(boolean bo) {
		this.floating = bo;

	}

	public void init() {

	}

}