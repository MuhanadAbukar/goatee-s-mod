package com.goatee.tutorial.scripted;

import com.goatee.tutorial.packets.PacketRegistry;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.scripted.entity.ScriptDBCPlayer;

public class PlayerStats<T extends EntityPlayerMP> {
	public EntityPlayerMP player;
	@SuppressWarnings("unchecked")
	ScriptDBCPlayer<T> playerSDBC = new ScriptDBCPlayer<T>((T)player);
	public static boolean floating;
    
	
	public PlayerStats(EntityPlayerMP player) {
		this.player = player;
		init();
	}
	public void init() {

	}

}