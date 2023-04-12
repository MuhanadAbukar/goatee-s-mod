package com.goatee.tutorial.Events;

import java.util.UUID;

import com.goatee.tutorial.packets.PacketRegistry;
import com.goatee.tutorial.scripted.Player;
import com.goatee.tutorial.scripted.PlayerStats;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class ServerEvents {

	@SubscribeEvent
	public void onTick(PlayerTickEvent e) {
		if (e.phase.equals(Phase.START)) {

			handleFlight(e);
			handleSprinting(e);
			handleMovementDisabled(e);

		}

	}

	@SubscribeEvent
	public void onLogIn(PlayerLoggedInEvent e) { // instantiate PlayerStats && isTriggered used in ClientEvents on login
													// and put it in HashMap so it's always the same for player
		EntityPlayerMP p = (EntityPlayerMP) e.player;
		UUID id = p.getUniqueID();
		NBTTagCompound nbt = p.getEntityData().getCompoundTag("PlayerPersisted");
		if (nbt.getInteger("jrmcStrI") >= 2) {
			@SuppressWarnings("rawtypes")
			PlayerStats ps = new PlayerStats(p);
			Player.getAllPlayerStats().put(id, ps);

		}
		initialiseNbts(nbt);

	}

	@SubscribeEvent
	public void onLogOut(PlayerLoggedOutEvent e) { // removes PlayerStats instance && isTriggered from HashMap
		System.out.println("R");
		EntityPlayerMP p = (EntityPlayerMP) e.player;
		UUID id = p.getUniqueID();
		NBTTagCompound nbt = p.getEntityData().getCompoundTag("PlayerPersisted");
		if (nbt.getInteger("jrmcStrI") >= 2) {
			Player.getAllPlayerStats().remove(id);

		}

	}

	public void initialiseNbts(NBTTagCompound nbt) {
		if (!nbt.hasKey("isFlying")) {
			nbt.setBoolean("isFlying", false);
		}
		if (!nbt.hasKey("lastFlyPacket")) {
			nbt.setBoolean("lastFlyPacket", false);
		}
		if (!nbt.hasKey("isSprinting")) {
			nbt.setBoolean("isSprinting", false);
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
	}

	public void handleFlight(PlayerTickEvent e) {
		if (e.player.getEntityData().getCompoundTag("PlayerPersisted").getBoolean("lastFlyPacket") != e.player
				.getEntityData().getCompoundTag("PlayerPersisted").getBoolean("isFlying")) {
			boolean isFlying = e.player.getEntityData().getCompoundTag("PlayerPersisted").getBoolean("isFlying");
			e.player.getEntityData().getCompoundTag("PlayerPersisted").setBoolean("lastFlyPacket", isFlying);
			PacketRegistry.tellClient((EntityPlayerMP) e.player, "isFlying:" + isFlying);
		}

	}

	public void handleSprinting(PlayerTickEvent e) {
		if (e.player.getEntityData().getCompoundTag("PlayerPersisted").getBoolean("lastSprintPacket") != e.player
				.getEntityData().getCompoundTag("PlayerPersisted").getBoolean("isSprinting")) {
			boolean isSprinting = e.player.getEntityData().getCompoundTag("PlayerPersisted").getBoolean("isSprinting");
			e.player.getEntityData().getCompoundTag("PlayerPersisted").setBoolean("lastSprintPacket", isSprinting);
			PacketRegistry.tellClient((EntityPlayerMP) e.player, "isSprinting:" + isSprinting);
		}
	}

	public void handleMovementDisabled(PlayerTickEvent e) {
		if (e.player.getEntityData().getCompoundTag("PlayerPersisted").getBoolean("lastMDPacket") != e.player
				.getEntityData().getCompoundTag("PlayerPersisted").getBoolean("isMovementDisabled")) {
			boolean isMovementDisabled = e.player.getEntityData().getCompoundTag("PlayerPersisted").getBoolean("isMovementDisabled");
			e.player.getEntityData().getCompoundTag("PlayerPersisted").setBoolean("lastMDPacket", isMovementDisabled);
			PacketRegistry.tellClient((EntityPlayerMP) e.player, "isMovementDisabled:" + isMovementDisabled);
		}

	}

}
