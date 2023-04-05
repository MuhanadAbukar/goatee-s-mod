package com.goatee.tutorial.packets;

import com.goatee.tutorial.Utils.ServerUtils;
import com.goatee.tutorial.scripted.Player;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class PacketTellServer implements IMessageHandler<PacketTellServer.msg, IMessage> {

	public IMessage onMessage(msg message, MessageContext ctx) {

		int id = message.id;

		EntityPlayerMP player = ctx.getServerHandler().playerEntity;
		NBTTagCompound nbt = player.getEntityData().getCompoundTag("PlayerPersisted");
		switch (id) {
			case 0 :
				if(nbt.getBoolean("isFlying") == false) {
					nbt.setBoolean("isFlying", true);
					 System.out.println("flying is true");
				}
				break;
			case 1 :
				if(nbt.getBoolean("isFlying") == true) {
					nbt.setBoolean("isFlying", false);
					 System.out.println("flying is false");
				}
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