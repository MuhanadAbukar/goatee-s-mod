package com.goatee.tutorial.proxy;

import com.goatee.tutorial.ModVars;
import com.goatee.tutorial.Events.ServerEvents;
import com.goatee.tutorial.packets.ServerPackets;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy {
	public static SimpleNetworkWrapper network;

	public void preInit(FMLPreInitializationEvent e) {
		FMLCommonHandler.instance().bus().register(new ServerEvents());
		network = NetworkRegistry.INSTANCE.newSimpleChannel(ModVars.MOD_ID + "Channel 1");

		network.registerMessage(ServerPackets.ServerPacketHandler.class, ServerPackets.class, 1, Side.SERVER);
		network.registerMessage(ServerPackets.ServerPacketHandler.class, ServerPackets.class, 2, Side.CLIENT);

	}

	public void init(FMLInitializationEvent e) {

	}

	public void postInit(FMLPostInitializationEvent e) {
	}
}
