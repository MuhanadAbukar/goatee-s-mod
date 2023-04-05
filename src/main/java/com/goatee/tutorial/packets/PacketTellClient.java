package com.goatee.tutorial.packets;

import JinRyuu.DragonBC.common.DBCKiTech;
import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.*;

public class PacketTellClient implements IMessageHandler<PacketTellClient.msg, IMessage> {

	public IMessage onMessage(msg message, MessageContext ctx) {

		int id = message.id;

//		EntityPlayerMP player = ctx.getServerHandler().playerEntity;
//		NBTTagCompound nbt = player.getEntityData().getCompoundTag("PlayerPersisted");
		switch (id) {
			case 0 :
				DBCKiTech.floating = true;
				System.out.println("flight is forced to true");
				break;
			case 1 :
				DBCKiTech.floating = false;
				System.out.println("flight is forced to false");

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