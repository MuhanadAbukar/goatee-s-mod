package com.goatee.tutorial.mixins;

import org.spongepowered.asm.mixin.Mixin;

import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.api.entity.IPlayer;

import org.spongepowered.asm.mixin.Unique;
import JinRyuu.JRMCore.server.JGPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import JinRyuu.JRMCore.JRMCoreH;

@Mixin(IDBCPlayer.class)
public interface MixinIDBCPlayer extends IPlayer {
	@Unique
	public void exampleMethod(String str);
	
	
	@Unique
	public boolean isFlying();
	
	
	@Unique
	public void setFlight(boolean bo);
	
	@Unique
	public boolean inAir();
	

	
	
	@Unique
	public int getMaxBody(); //works
	@Unique
	public int getMaxEnergy(); //works
	@Unique
	public int getMaxStamina(); //works

	
	
	@Unique
	public String StusEffectsMe(); //works
	@Unique
	public void changeDBCAnim(int i); //works
	

	
	
	@Unique
	public int[] getAllStats();
	@Unique
	public int getFullStat(int statid);
	@Unique
	public int[] getAllFullStats();
	
	
	
	
	@Unique
	public EntityPlayerMP getEntityPlayerMP();//works
	@Unique
	public JGPlayerMP getJGPlayer(); //works
	@Unique
	public JRMCoreH getJRMCoreH(); //works
	
	
	
	@Unique
	public String getFormName(int race, int form); //works
	@Unique
	public String getCurrentFormName(); //works
	@Unique
	public void changeFormMastery(EntityPlayer player, String formName,
			double amount, boolean add); //works
	@Unique
	public double getFormMastery(EntityPlayer player, String formName);
	@Unique
	public String getAllFormMasteries(); //works
	@Unique
	public void addFusionFormMasteries(EntityPlayer controller, EntityPlayer spectator, boolean multiplyaddedStats, double multiValue);
	
	
	
}
