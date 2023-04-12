package com.goatee.tutorial;

import com.goatee.tutorial.proxy.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModVars.ID, name = ModVars.name, version = ModVars.version)
public class Main {
	@SidedProxy(clientSide = "com.goatee.tutorial.proxy.ClientProxy", serverSide = "com.goatee.tutorial.proxy.CommonProxy")
	public static CommonProxy proxy;
	@Instance
    public static Main instance = new Main();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);

	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}
}
