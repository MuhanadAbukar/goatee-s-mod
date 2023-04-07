package com.goatee.tutorial.packets;

import com.goatee.tutorial.scripted.Player;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class PacketTellServer implements IMessageHandler<PacketTellServer.msg, IMessage> {

	public IMessage onMessage(msg message, MessageContext ctx) {

		int id = message.id;

		EntityPlayerMP p = ctx.getServerHandler().playerEntity;
		NBTTagCompound nbt = p.getEntityData().getCompoundTag("PlayerPersisted");
		switch (id) {
			case 0 : //set isFlying status to true
				if(nbt.getBoolean("isFlying") == false) {
					nbt.setBoolean("isFlying", true);
					 //System.out.println("flying is true");
					 Player.getAllPlayerStats().get(p.getUniqueID()).setFlying(true);
				}
				break;
			case 1 : //set isFlying status to false
				if(nbt.getBoolean("isFlying") == true) {
					nbt.setBoolean("isFlying", false);
					 Player.getAllPlayerStats().get(p.getUniqueID()).setFlying(false);
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