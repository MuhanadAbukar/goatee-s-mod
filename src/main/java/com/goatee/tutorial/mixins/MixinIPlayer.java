package com.goatee.tutorial.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.goatee.tutorial.scripted.PlayerStats;

import JinRyuu.JRMCore.JRMCoreEH;
import JinRyuu.JRMCore.JRMCoreH;
import JinRyuu.JRMCore.i.ExtendedPlayer;
import JinRyuu.JRMCore.server.JGPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.entity.IEntityLivingBase;
import noppes.npcs.api.entity.IPlayer;

@Mixin(IPlayer.class)
public interface MixinIPlayer<T extends EntityPlayerMP> extends IEntityLivingBase<T> {

	@SuppressWarnings("rawtypes")
	@Unique
	public PlayerStats getPlayerStats();

	@Unique
	public EntityPlayerMP getEntityPlayerMP();

	@Unique
	public JGPlayerMP getJGPlayer();

	@Unique
	public ExtendedPlayer getJRMCExtendedPlayer();

	@Unique
	public T getPlayer(String playername);

	@Unique
	public void executeCommand(String command);

	@Unique
	public void addPotionEffect(int effect, int effect2, int effect3, int effect4, int duration, int strength,
			boolean hideParticles);

	@Unique
	public void disableSprinting(boolean bo);

	@Unique
	public void disableMovement(boolean bo);

	@Unique
	public void setBlind(boolean bo);

	@Unique
	public boolean inAir();

}
