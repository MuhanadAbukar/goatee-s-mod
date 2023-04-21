package com.goatee.tutorial.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.entity.ScriptPlayer;

@SuppressWarnings("rawtypes")
@Mixin(IDBCPlayer.class)
public interface MixinIDBCPlayer {
	@Unique
	public void exampleMethod(String str);

	@Unique
	public void enableCombatMode(boolean bo); // my combat mode, WIP, press ` or check controls for "Toggle Combat Mode" bind, then Open GUI and set appropriate skills keys from there, not from
												// minecraft control settings

	@Unique
	public boolean isFlying(); // returns true if player is DBC flying

	@Unique
	public void setFlight(boolean bo); // sets player DBC flight

	@Unique
	public int getMaxBody(); // returns player max body

	@Unique
	public int getMaxEnergy(); // returns max energy

	@Unique
	public int getMaxStamina(); // returns max stamina

	@Unique
	public String StusEffectsMe(); // for testing purposes, but returns JRMCSE

	@Unique
	public void changeDBCAnim(int i); // also testing purposes, but changes player animation, like in UI dodging (animations are changed to default in a tick event so doesnt work)

	@Unique
	public int[] getAllStats(); // returns all player stats in an array, index 0:str, 1:dex, 2:con, 3:will, 4:

	@Unique
	public void addAllStats(int[] Stats, boolean multiplyaddedStats, double multiValue); // adds all player stats by an array of stats, THEN multiplies by multiValue if boolean is true

	@Unique
	public void addAllStats(int Num, boolean setStatsToNum); // adds all stats by a single value, if boolean true, set stats to value

	@Unique
	public void addAllStats(int[] stats, boolean setStats); // adds all stats by array, if true sets stats to array

	@Unique
	public void multiplyAllStats(double multi); // all stats multiplied by multi

	@Unique
	public int getFullStat(int statid); // a "full stat" is a stat that has all factors calculated, like transformations, kaioken, server attribute and race multipliers, UI, majin/legendary/divine SEs

	@Unique
	public int[] getAllFullStats(); // returns an array with all full stats

	@Unique
	public String getFormName(int race, int form); // returns form name for int form id of int race

	@Unique
	public String getCurrentFormName(); // gets current player's form

	@Unique
	public String[] getAllForms(int race, boolean nonRacial); // returns an array of a all int race's form names

	public int getAllFormsLength(int race, boolean nonRacial); // returns length of above

	@Unique
	public void changeFormMastery(String formName, // sets form master of formname to amount. if add is true, adds
			double amount, boolean add); //

	@Unique
	public double getFormMasteryValue(String formName); // returns form mastery value for formasnme

	@Unique
	public String getAllFormMasteries(); // returns all form masteries for players
	/*
	 * // returns an array containing all form mastery config data for form id for race, like index 0: max level, 1: instant transform unlock level 2: required masteries 3: auto learn on level 4: gain
	 * to other masteries
	 */

	@Unique
	public String[] getAllFormMasteryData(int race, int formId);

	/**
	 * adds ALL masteries of both players first, then if multiplyAddedStats is enabled, multiplies them by multivalue.
	 */
	@Unique
	public void addFusionFormMasteries(ScriptPlayer Controller, ScriptPlayer Spectator, boolean multiplyaddedStats, double multiValue, boolean addForBoth);

	@Unique
	public void doUIDodge(byte chance); // 100 is guranteed, use this in an player attack event with e.setCanceled(true) for an actual UI dodge

	@Unique
	public boolean isDBCFusionSpectator(); // true if player is a spectator in the vanilla DBC fusion, not my fusion

}
