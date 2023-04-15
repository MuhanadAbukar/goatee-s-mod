package com.goatee.tutorial.proxy;

import com.goatee.tutorial.CombatMode.KeyHandler;
import com.goatee.tutorial.Events.ClientEvents;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		FMLCommonHandler.instance().bus().register(new ClientEvents());
		MinecraftForge.EVENT_BUS.register(new ClientEvents());
		
	}

	public void init(FMLInitializationEvent e) {
		super.init(e);
		KeyHandler.registerKeys();
	}

	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}
}
