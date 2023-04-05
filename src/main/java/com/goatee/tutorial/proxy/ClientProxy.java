package com.goatee.tutorial.proxy;

import com.goatee.tutorial.Events.ClientEvents;
import com.goatee.tutorial.scripted.PlayerStats;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
		FMLCommonHandler.instance().bus().register(new ClientEvents());

	}

	public void init(FMLInitializationEvent e) {
		super.init(e);
	}

	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}
}
