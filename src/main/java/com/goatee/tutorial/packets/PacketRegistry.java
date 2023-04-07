package com.goatee.tutorial.packets;

import com.goatee.tutorial.ModVars;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketRegistry {

	public static SimpleNetworkWrapper tellS;
	public static SimpleNetworkWrapper tellC;

	//private static int nextPacketId;

	public static void initPackets(Side side) {

		tellS = NetworkRegistry.INSTANCE.newSimpleChannel(ModVars.MOD_ID + "tellS");
		tellC = NetworkRegistry.INSTANCE.newSimpleChannel(ModVars.MOD_ID + "tellC");

		if (side == Side.CLIENT) {
			registerMessage(tellC, PacketTellClient.class, PacketTellClient.msg.class, 0, Side.CLIENT);
			registerMessage(tellS, PacketTellServer.class, PacketTellServer.msg.class, 1, Side.SERVER);
		}
		
		if (side == Side.SERVER) {
			registerMessage(tellC, PacketTellServer.class, PacketTellClient.msg.class, 0, Side.SERVER);
			registerMessage(tellS, PacketTellServer.class, PacketTellServer.msg.class, 1, Side.SERVER);
		}
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	private static void registerMessage(SimpleNetworkWrapper netw, Class packet, Class message, int discriminator,
			Side side) {

		netw.registerMessage(packet, message, discriminator, side);
		//nextPacketId++;
	}
	
	public static void tellClient(EntityPlayerMP player, int id) {
		IMessage msg = new PacketTellClient.msg(id);

		tellC.sendTo(msg, player);
	}

	public static void tellServer(int id) {

		IMessage msg = new PacketTellServer.msg(id);

		tellS.sendToServer(msg);
	}
}