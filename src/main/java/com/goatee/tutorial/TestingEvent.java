package com.goatee.tutorial;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class TestingEvent {
	
	
	@SubscribeEvent
	public void test(PlayerTickEvent e) {
		//e.player.addChatMessage(new ChatComponentText("A tick has passed!"));
		
	}
	
	
	


}
