package com.goatee.tutorial.Events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class x {
	@SideOnly(Side.CLIENT)
	public static boolean x = false;
	
	
	@SubscribeEvent
	public void onTick(PlayerTickEvent e) {
		if(x) {
			//do stuff
		}
	}
	

}
