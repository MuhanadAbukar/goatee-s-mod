package com.goatee.tutorial.Events;

import com.goatee.tutorial.packets.PacketRegistry;

import JinRyuu.DragonBC.common.DBCKiTech;
import JinRyuu.JRMCore.JRMCoreKeyHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;


import java.util.UUID;
import java.util.WeakHashMap;

public class ClientEvents {
	public static WeakHashMap<UUID, Boolean> isOnGroundTriggeredMap = new WeakHashMap<UUID, Boolean>();
	

	@SubscribeEvent
	public void onKeyPress(InputEvent.KeyInputEvent e) {

		if (JRMCoreKeyHandler.KiFlight.getIsKeyPressed() && JRMCoreKeyHandler.KiFlight.isPressed()) {
			boolean floating = DBCKiTech.floating;
			if (!floating) { //sends to server that player isFlying on flight keybind press.
				PacketRegistry.tellServer(0);

			} else {
				PacketRegistry.tellServer(1);
			}
		}
	}
	
	/*
	 * there's a DBC bug in where when flying, if you land due to flight gravity, not
	 * by pressing F, you are still flying as DBCKiTech.floating doesn't get set to false. This
	 * fixes that
	 */
	@SubscribeEvent
	public void onTick(PlayerTickEvent e) {
		//checks if player is on ground once, then stops. This "once" resets once player is no longer on ground, then checks again after player lands. If on ground, disables DBC flight and tells server isFlying is false.
		if (e.player.onGround && !isOnGroundTriggeredMap.get(e.player.getUniqueID()) && !DBCKiTech.floating) {
			PacketRegistry.tellServer(1);
			DBCKiTech.floating = false;
			ClientEvents.isOnGroundTriggeredMap.put(e.player.getUniqueID(),true);
		}
		if (!e.player.onGround) {
			ClientEvents.isOnGroundTriggeredMap.put(e.player.getUniqueID(),false);
		}
		// System.out.println("tick - flight is pressed");
	}

}
