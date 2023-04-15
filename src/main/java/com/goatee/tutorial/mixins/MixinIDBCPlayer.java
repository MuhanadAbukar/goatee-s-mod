package com.goatee.tutorial.mixins;

import org.spongepowered.asm.mixin.Mixin;

import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.scripted.entity.ScriptPlayer;

import org.spongepowered.asm.mixin.Unique;

import com.goatee.tutorial.scripted.PlayerStats;


@SuppressWarnings("rawtypes")
@Mixin(IDBCPlayer.class)
public interface MixinIDBCPlayer extends IPlayer {
	@Unique
	public void exampleMethod(String str);
	@Unique
	public PlayerStats getPlayerStats();
	@Unique
	public void enableCombatMode(boolean bo);
	
	
	
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
	public void addAllStats(int[] Stats, boolean multiplyaddedStats, double multiValue);
	@Unique
	public void addAllStats(int Num, boolean setStatsToNum);
	@Unique
	public void addAllStats(int[] stats, boolean setStats);
	@Unique
	public void multiplyAllStats(double multi);
	@Unique
	public int getFullStat(int statid); //a "full stat" is a stat that has all factors calculated, like transformations, kaioken, server attribute and race multipliers, UI, majin/legendary/divine SEs
	@Unique
	public int[] getAllFullStats(); //returns an array with all full stats

	
	
	
	
	
	
	
	@Unique
	public String getFormName(int race, int form); //works
	@Unique
	public String getCurrentFormName(); //works
	@Unique
	public String[] getAllForms(int race, boolean nonRacial);
	public int getAllFormsLength(int race, boolean nonRacial);
	@Unique
	public void changeFormMastery(ScriptPlayer Player, String formName,
			double amount, boolean add); //works
	@Unique
	public double getFormMasteryValue(ScriptPlayer Player, String formName);
	@Unique
	public String getAllFormMasteries(); //works
	@Unique
	public String[] getAllFormMasteryData(int race, int formId);
	
	/**adds ALL masteries of both players first, then 
	if multiplyAddedStats is enabled, multiplies them by multivalue.*/
	@Unique
	public void addFusionFormMasteries(ScriptPlayer Controller, ScriptPlayer Spectator,
			boolean multiplyaddedStats, double multiValue, boolean addForBoth);	
	
	
	@Unique
	public void doUIDodge(byte chance);
	@Unique
	public boolean isDBCFusionSpectator();
	
}
