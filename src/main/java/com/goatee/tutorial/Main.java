package com.goatee.tutorial;

import com.goatee.tutorial.proxy.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Main.ID, name = Main.name, version = Main.version)
public class Main {
	@SidedProxy(clientSide = "com.goatee.tutorial.proxy.ClientProxy", serverSide = "com.goatee.tutorial.proxy.CommonProxy")
	public static CommonProxy proxy;
	public static final String name = "Utilities for Dragon Block C";
	public static final String version = "1.0 - 1.7.10";
	public static final String ID = "dbccnpcutils";
	@Instance
	public static Main instance = new Main();

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);

	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {

	}
}
