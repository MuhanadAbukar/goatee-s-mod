package com.goatee.tutorial.mixins;

import org.spongepowered.asm.mixin.Mixin;

import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.api.entity.IPlayer;

import org.spongepowered.asm.mixin.Unique;

import com.goatee.tutorial.scripted.PlayerStats;

import net.minecraft.entity.player.EntityPlayer;

@SuppressWarnings("rawtypes")
@Mixin(IDBCPlayer.class)
public interface MixinIDBCPlayer extends IPlayer {
	@Unique
	public void exampleMethod(String str);
	@Unique
	public PlayerStats getPlayerStats();
	
	
	
	@Unique
	public boolean isFlying();
	@Unique
	public void setFlight(boolean bo);

	
	
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
	public int getFullStat(int statid); //a "full stat" is a stat that has all factors calculated, like transformations, kaioken, server attribute and race multipliers, UI, majin/legendary/divine SEs
	@Unique
	public int[] getAllFullStats(); //returns an array with all full stats
	
	
	
	
	//@Unique
	//public String getFormName(int race, int form); //works
	@Unique
	public String getCurrentFormName(); //works
	@Unique
	public void changeFormMastery(EntityPlayer player, String formName,
			double amount, boolean add); //works
	@Unique
	public double getFormMasteryValue(EntityPlayer player, String formName);
	@Unique
	public String getAllFormMasteries(); //works
	@Unique
	public void addFusionFormMasteries(EntityPlayer controller, EntityPlayer spectator, boolean multiplyaddedStats, double multiValue);
	
	
	
}
