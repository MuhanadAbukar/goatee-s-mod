package com.goatee.tutorial.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class PacketTellServer implements IMessageHandler<PacketTellServer.msg, IMessage> {

	public IMessage onMessage(msg message, MessageContext ctx) {

		String idString = message.idMsg;
		System.out.println("Tell Server idMsg = " + message.idMsg);


		EntityPlayerMP p = ctx.getServerHandler().playerEntity;
		NBTTagCompound nbt = p.getEntityData().getCompoundTag("PlayerPersisted");

		if (idString.startsWith("lastFlyPacket:")) {
			// Boolean value = Boolean.parseBoolean(idString.split(":")[1]);
			// nbt.setBoolean("lastFlyPacket", value);
		}

		switch (idString) {
		case "1":
			if (!nbt.getBoolean("isFlying")) {
				nbt.setBoolean("isFlying", true);
			}

			break;
		case "2":
			if (nbt.getBoolean("isFlying")) {
				nbt.setBoolean("isFlying", false);

			}

			break;
		case "100":
			if (!nbt.getBoolean("isSprintDisabled")) {
				nbt.setBoolean("isSprintDisabled", true);
			} else {
				nbt.setBoolean("isSprintDisabled", false);
			}
			break;

		case "101":
			if (!nbt.getBoolean("isMovementDisabled")) {
				nbt.setBoolean("isMovementDisabled", true);
			} else {
				nbt.setBoolean("isMovementDisabled", false);
			}
			break;

		}

		return null;
	}

	public static class msg implements IMessage {

		private String idMsg;

		public msg() {
		}

		public msg(String info) {
			this.idMsg = info;
		}

		@Override
		public void fromBytes(ByteBuf buf) {
			this.idMsg = ByteBufUtils.readUTF8String(buf);
		}

		@Override
		public void toBytes(ByteBuf buf) {
			ByteBufUtils.writeUTF8String(buf, idMsg);
		}
	}
}