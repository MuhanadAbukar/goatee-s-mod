package com.goatee.tutorial.Events;

import java.util.UUID;

import com.goatee.tutorial.packets.PacketRegistry;
import com.goatee.tutorial.scripted.Player;
import com.goatee.tutorial.scripted.PlayerStats;

import JinRyuu.JRMCore.JRMCoreH;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class ServerEvents {

	@SubscribeEvent
	public void onEntityJoin(EntityJoinWorldEvent event) {
		checkForDmgRed(event.entity);
	}

	private void checkForDmgRed(Entity entity) {
		NBTTagCompound nbt;
		if (entity instanceof EntityPlayer) {
			nbt = entity.getEntityData().getCompoundTag("PlayerPersisted");
			if (!nbt.hasKey("dmgred")) {
				nbt.setFloat("dmgred", 100);
			}
		} else {
			nbt = JRMCoreH.nbt(entity);
			if (!nbt.hasKey("dmgred")) {
				nbt.setFloat("dmgred", 100);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void hurtEvent(LivingAttackEvent e) {
		isSpectator(e, e.entity);
	}
	// LivingAttackEvent e

	public void isSpectator(LivingAttackEvent e, Entity p) {
		if (p instanceof EntityPlayer) {
			NBTTagCompound nbt = p.getEntityData().getCompoundTag("PlayerPersisted");
			if (!nbt.getString("ControllerName").isEmpty()) {
				e.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onTick(PlayerTickEvent e) {
		if (e.phase.equals(Phase.START)) {
			NBTTagCompound nbt = e.player.getEntityData().getCompoundTag("PlayerPersisted");
			handleFlight(nbt, e.player);
			handleSprinting(nbt, e.player);
			handleMovementDisabled(nbt, e.player);
			handleCombatMode(nbt, e.player);
			handleIsBlind(nbt, e.player);
			handleMenuDisabled(nbt, e.player);
		}
	}

	public void handleFlight(NBTTagCompound nbt, EntityPlayer p) {
		if (nbt.getBoolean("lastFlyPacket") != nbt.getBoolean("isFlying")) {
			boolean isFlying = nbt.getBoolean("isFlying");
			nbt.setBoolean("lastFlyPacket", isFlying);
			PacketRegistry.tellClient((EntityPlayerMP) p, "isFlying:" + isFlying);
		}
		//
//		if (nbt.getBoolean("onGroundLastTick") != p.onGround) {
//			nbt.setBoolean("onGroundLastTick", p.onGround);
//			System.out.println("last on ground " + nbt.getBoolean("onGroundLastTick"));
//			System.out.println("new last on ground " + nbt.getBoolean("onGroundLastTick"));
//		}
//			//
//			if (p.onGround && nbt.getBoolean("isFlying")) {
//				System.out.println("on ground " + p.onGround);
//				nbt.setBoolean("isFlying", false);
//				PacketRegistry.tellClient((EntityPlayerMP) p, "checkFlying");
//			}

	}

	public void handleSprinting(NBTTagCompound nbt, EntityPlayer p) {
		if (nbt.getBoolean("lastSprintPacket") != nbt.getBoolean("isSprintDisabled")) {
			boolean isSprintDisabled = nbt.getBoolean("isSprintDisabled");
			nbt.setBoolean("lastSprintPacket", isSprintDisabled);
			PacketRegistry.tellClient((EntityPlayerMP) p, "isSprintDisabled:" + isSprintDisabled);
		}
	}

	public void handleMenuDisabled(NBTTagCompound nbt, EntityPlayer p) {
		if (nbt.getBoolean("lastMenuPacket") != nbt.getBoolean("isMenuDisabled")) {
			boolean isMenuDisabled = nbt.getBoolean("isMenuDisabled");
			nbt.setBoolean("lastMenuPacket", isMenuDisabled);
			PacketRegistry.tellClient((EntityPlayerMP) p, "isMenuDisabled:" + isMenuDisabled);
		}
	}

	public void handleMovementDisabled(NBTTagCompound nbt, EntityPlayer p) {
		if (nbt.getBoolean("lastMDPacket") != nbt.getBoolean("isMovementDisabled")) {
			boolean isMovementDisabled = nbt.getBoolean("isMovementDisabled");
			nbt.setBoolean("lastMDPacket", isMovementDisabled);
			PacketRegistry.tellClient((EntityPlayerMP) p, "isMovementDisabled:" + isMovementDisabled);
		}
	}

	public void handleCombatMode(NBTTagCompound nbt, EntityPlayer p) {
		if (nbt.getBoolean("lastCMPacket") != nbt.getBoolean("CombatMode")) {
			boolean CombatMode = nbt.getBoolean("CombatMode");
			nbt.setBoolean("lastCMPacket", CombatMode);
			PacketRegistry.tellClient((EntityPlayerMP) p, "CombatMode:" + CombatMode);
		}
	}

	public void handleIsBlind(NBTTagCompound nbt, EntityPlayer p) {
		if (nbt.getBoolean("lastIsBlindPacket") != nbt.getBoolean("isBlind")) {
			boolean isBlind = nbt.getBoolean("isBlind");
			nbt.setBoolean("lastIsBlindPacket", isBlind);
			PacketRegistry.tellClient((EntityPlayerMP) p, "isBlind:" + isBlind);
		}
	}

	@SubscribeEvent
	public void onLogIn(PlayerLoggedInEvent e) {
		EntityPlayerMP p = (EntityPlayerMP) e.player;
		UUID id = p.getUniqueID();
		NBTTagCompound nbt = p.getEntityData().getCompoundTag("PlayerPersisted");
		if (nbt.getInteger("jrmcStrI") >= 2) {
			PlayerStats<?> ps = new PlayerStats<>(p);
			Player.getAllPlayerStats().put(id, ps);
			/// ClientEvents.onGroundLT.put(id, p.onGround);

		}
		initialiseNbts(nbt);
		removeTemps(nbt);
	}

	@SubscribeEvent
	public void onLogOut(PlayerLoggedOutEvent e) {
		EntityPlayerMP p = (EntityPlayerMP) e.player;
		UUID id = p.getUniqueID();
		NBTTagCompound nbt = p.getEntityData().getCompoundTag("PlayerPersisted");
		System.out.println("Sd");
		if (nbt.getInteger("jrmcStrI") >= 2) {
			Player.getAllPlayerStats().remove(id);
			// ClientEvents.onGroundLT.remove(id);
		}
		removeTemps(nbt);

	}

	public void initialiseNbts(NBTTagCompound nbt) {
		if (!nbt.hasKey("isFlying")) {
			nbt.setBoolean("isFlying", false);
		}
		if (!nbt.hasKey("lastFlyPacket")) {
			nbt.setBoolean("lastFlyPacket", false);
		}
		if (!nbt.hasKey("isSprintDisabled")) {
			nbt.setBoolean("isSprintDisabled", false);
		}
		if (!nbt.hasKey("lastSprintPacket")) {
			nbt.setBoolean("lastSprintPacket", false);
		}
		if (!nbt.hasKey("isMovementDisabled")) {
			nbt.setBoolean("isMovementDisabled", false);
		}
		if (!nbt.hasKey("lastMDPacket")) {
			nbt.setBoolean("lastMDPacket", false);
		}
		if (!nbt.hasKey("isBlind")) {
			nbt.setBoolean("isBlind", false);
		}
		if (!nbt.hasKey("lastIsBlindPacket")) {
			nbt.setBoolean("lastIsBlindPacket", false);
		}
		if (!nbt.hasKey("CombatMode")) {
			nbt.setBoolean("CombatMode", false);
		}
		if (!nbt.hasKey("lastCMPacket")) {
			nbt.setBoolean("lastCMPacket", false);
		}
		if (!nbt.hasKey("kiBlastsPassThrough")) {
			nbt.setBoolean("kiBlastsPassThrough", false);
		}
		if (!nbt.hasKey("lastMenuPacket")) {
			nbt.setBoolean("lastMenuPacket", false);
		}

	}

	public void removeTemps(NBTTagCompound nbt) {
		if (nbt.getBoolean("lastFlyPacket")) {
			nbt.setBoolean("lastFlyPacket", false);
		}
		if (nbt.getBoolean("lastSprintPacket")) {
			nbt.setBoolean("lastSprintPacket", false);
		}
		if (nbt.getBoolean("lastMDPacket")) {
			nbt.setBoolean("lastMDPacket", false);
		}
		if (nbt.getBoolean("lastMenuPacket")) {
			nbt.setBoolean("lastMenuPacket", false);
		}
		if (nbt.getBoolean("CombatMode")) {
			nbt.setBoolean("CombatMode", false);
		}
		if (nbt.getBoolean("isBlind")) {
			nbt.setBoolean("isBlind", false);
		}
		if (!nbt.getString("ControllerName").isEmpty()) {
			nbt.setString("ControllerName", "");
		}

	}

}
