package com.goatee.tutorial.mixins;

import org.spongepowered.asm.mixin.Mixin;

import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.api.entity.IPlayer;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import JinRyuu.JRMCore.server.JGPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;

@Mixin(IDBCPlayer.class)
public interface MixinIDBCPlayer extends IPlayer {
	@Unique
	public void exampleMethod(String str);
	@Unique
	public boolean isFlying();
	public void setFlight(boolean bo);
	public boolean inAir();
	
	public int getMaxBody();
	public int getMaxEnergy();
	public int getMaxStamina();
	
	public String StusEffectsMe();
	
	public void changeDBCAnim(int i);
	
	public int[] getAllStats();
	public int getFullStat(int statid);
	public int[] getAllFullStats();
	
	public JGPlayerMP JGPlayer();
	
	
	
	


}
