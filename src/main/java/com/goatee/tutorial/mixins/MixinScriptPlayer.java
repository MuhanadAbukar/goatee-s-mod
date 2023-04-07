package com.goatee.tutorial.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;


import com.goatee.tutorial.scripted.Player;
import com.goatee.tutorial.scripted.PlayerStats;

import JinRyuu.JRMCore.JRMCoreEH;
import JinRyuu.JRMCore.JRMCoreH;
import JinRyuu.JRMCore.i.ExtendedPlayer;
import JinRyuu.JRMCore.server.JGPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.scripted.NpcAPI;
import noppes.npcs.scripted.entity.ScriptPlayer;

@Mixin(ScriptPlayer.class)
public abstract class MixinScriptPlayer<T extends EntityPlayerMP> {
	@SuppressWarnings("unchecked")
	public ScriptPlayer<T> p = ((ScriptPlayer<T>) (Object) this);
	public T player = p.player;

	@Unique
	public boolean inAir() {
		return !player.onGround;
	}

	@SuppressWarnings("rawtypes")
	@Unique
	public PlayerStats getPlayerStats() {
		return Player.getAllPlayerStats().get(player.getUniqueID());
	}

	@Unique
	public EntityPlayerMP getEntityPlayerMP() {
		return player;
	}

	@Unique
	public ExtendedPlayer getExtendedPlayer() {
		return ExtendedPlayer.get(player);
	}

	@Unique
	public JGPlayerMP getJGPlayer() {
		JGPlayerMP JG = new JGPlayerMP(player);
		NBTTagCompound nbt = player.getEntityData().getCompoundTag("PlayerPersisted");
		JG.setNBT(nbt);
		return JG;
	}

	@Unique
	public JRMCoreH getJRMCoreH() {
	
		return new JRMCoreH();
	}
	@Unique
	public JRMCoreEH getJRMCoreEH() {
		return new JRMCoreEH();
	}

	@Unique
	public IPlayer<?> getPlayer(String playername) {
		return NpcAPI.Instance().getPlayer(playername);
	}

	@Unique
	public void executeCommand(String command) {
		NoppesUtilServer.runCommand(player.getEntityWorld(), "API at " + player.getCommandSenderName(), command);

	}
}
