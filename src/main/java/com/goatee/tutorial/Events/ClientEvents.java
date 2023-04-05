package com.goatee.tutorial.Events;

import com.goatee.tutorial.packets.PacketRegistry;

import JinRyuu.DragonBC.common.DBCKiTech;
import JinRyuu.JRMCore.JRMCoreKeyHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class ClientEvents {

	public boolean isFlying = false;
	
	@SubscribeEvent
	public void onKeyPress(InputEvent.KeyInputEvent e) {
		if (JRMCoreKeyHandler.KiFlight.getIsKeyPressed()) {
			boolean floating = DBCKiTech.floating;
			if (floating) {
//				PlayerStats.floating = true;
			} else {
//				PlayerStats.floating = false;
			}
			System.out.println("flight is pressed");
		}
	}

	@SubscribeEvent
	public void onTick(PlayerTickEvent e) {
		// System.out.println("tick - flight is pressed");
			if(DBCKiTech.floating == true) {
				PacketRegistry.tellServer(0);
			}
			else {
				PacketRegistry.tellServer(1);
			}
	}

}
