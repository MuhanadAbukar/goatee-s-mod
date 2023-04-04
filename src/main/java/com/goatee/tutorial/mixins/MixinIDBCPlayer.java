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
	public int getMaxBody();
	@Unique
	public int getMaxEnergy();
	@Unique
	public int getMaxStamina();

	
	
	@Unique
	public String StusEffectsMe();
	@Unique
	public void changeDBCAnim(int i);

	
	
	@Unique
	public int[] getAllStats();
	@Unique
	public int getFullStat(int statid);
	@Unique
	public int[] getAllFullStats();
	
	
	
	@Unique
	public EntityPlayer getEntityPlayer();
	@Unique
	public EntityPlayerMP getEntityPlayerMP();
	@Unique
	public JGPlayerMP getJGPlayer();
	@Unique
	public JRMCoreH getJRMCoreH();
	
	
	
	@Unique
	public String getFormName(int race, int form);
	@Unique
	public String getCurrentFormName();
	@Unique
	public void changeFormMastery(EntityPlayer player, String formName,
			double amount, boolean add);
	@Unique
	public double getFormMastery(EntityPlayer player, String formName);
	@Unique
	public String getAllFormMasteries();

	
	
}
