package com.goatee.tutorial.Events;

import com.goatee.tutorial.scripted.Player;
import com.goatee.tutorial.scripted.PlayerStats;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import java.util.Map;
import java.util.UUID;

public class ServerEvents {

	@SubscribeEvent
	public void onLogIn(PlayerLoggedInEvent e) {
		EntityPlayerMP p = (EntityPlayerMP) e.player;
		UUID id = p.getUniqueID();
		NBTTagCompound nbt = p.getEntityData().getCompoundTag("PlayerPersisted");
		if (nbt.getInteger("jrmcStrI") >= 1) {
			if(!Player.getAllPlayerStats().containsKey(id)) {
				@SuppressWarnings("rawtypes")
				PlayerStats ps = new PlayerStats(p);
				Player.getAllPlayerStats().put(id, ps);
				System.out.println("Added " + p.getDisplayName() + " to map");
			}
			
		}

	}

	@SubscribeEvent
	public void onLogOut(PlayerLoggedOutEvent e) {
		System.out.println("R");
		EntityPlayerMP p = (EntityPlayerMP) e.player;
		UUID id = p.getUniqueID();
		NBTTagCompound nbt = p.getEntityData().getCompoundTag("PlayerPersisted");
		if (nbt.getInteger("jrmcStrI") >= 1) {
			if(Player.getAllPlayerStats().containsKey(id)) {
				Player.getAllPlayerStats().remove(id);
				System.out.println("Removed " + p.getDisplayName() + " from map");
			}
			
		}
	}

}
