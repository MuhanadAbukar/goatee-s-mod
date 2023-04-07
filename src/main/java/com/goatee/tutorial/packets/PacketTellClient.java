package com.goatee.tutorial.packets;

import JinRyuu.DragonBC.common.DBCKiTech;
import JinRyuu.JRMCore.JRMCoreKeyHandler;
import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.*;
import net.minecraft.client.settings.KeyBinding;


public class PacketTellClient implements IMessageHandler<PacketTellClient.msg, IMessage> {

	public IMessage onMessage(msg message, MessageContext ctx) {

		int id = message.id;

		//EntityPlayerMP player = ctx.getServerHandler().playerEntity;
		// NBTTagCompound nbt =
		// player.getEntityData().getCompoundTag("PlayerPersisted");
		switch (id) {
		case 0: //enable DBC Flight
			KeyBinding k1 = JRMCoreKeyHandler.KiFlight;
			if (DBCKiTech.floating == false) {
				KeyBinding.setKeyBindState(k1.getKeyCode(), true);
				PacketRegistry.tellServer(0);
			}//disable DBC Flight
			if (DBCKiTech.floating == true) {
				DBCKiTech.floating = false;
				PacketRegistry.tellServer(1);
			}
			break;
		case 1:

			break;
		}

		return null;
	}

	public static class msg implements IMessage {

		private int id;

		public msg() {
		}

		public msg(int info) {

			this.id = info;
		}

		@Override
		public void fromBytes(ByteBuf buf) {

			this.id = buf.readInt();
		}

		@Override
		public void toBytes(ByteBuf buf) {

			buf.writeInt(id);
		}
	}
}