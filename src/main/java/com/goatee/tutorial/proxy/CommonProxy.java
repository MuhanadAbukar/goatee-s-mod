package com.goatee.tutorial.proxy;

import com.goatee.tutorial.Events.ServerEvents;
import com.goatee.tutorial.packets.PacketRegistry;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
//	public static SimpleNetworkWrapper network;

	public void preInit(FMLPreInitializationEvent e) {
		FMLCommonHandler.instance().bus().register(new ServerEvents());
		MinecraftForge.EVENT_BUS.register(new ServerEvents());
		PacketRegistry.initPackets(e.getSide());
		

	}

	public void init(FMLInitializationEvent e) {

	}

	public void postInit(FMLPostInitializationEvent e) {
	}
}
