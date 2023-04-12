package com.goatee.tutorial.packets;

import com.goatee.tutorial.Events.ClientEvents;

import JinRyuu.DragonBC.common.DBCKiTech;
import JinRyuu.JRMCore.JRMCoreKeyHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.settings.KeyBinding;

public class PacketTellClient implements IMessageHandler<PacketTellClient.msg, IMessage> {

	public IMessage onMessage(msg message, MessageContext ctx) {

		String idString = message.idMsg;
		System.out.println("Tell Client idMsg = " + message.idMsg);

		// EntityClientPlayerMP p = Minecraft.getMinecraft().thePlayer;
		// NBTTagCompound nbt = p.getEntityData().getCompoundTag("PlayerPersisted");

		if (idString.startsWith("isFlying:")) {
			Boolean value = Boolean.parseBoolean(idString.split(":")[1]);

			if (value) {
				if (!DBCKiTech.floating) {
					KeyBinding.setKeyBindState(JRMCoreKeyHandler.KiFlight.getKeyCode(), true);
				}
			} else {
				if (DBCKiTech.floating) {
					DBCKiTech.floating = false;
				}

			}
		}

		if (idString.startsWith("isSprinting:")) {
			Boolean value = Boolean.parseBoolean(idString.split(":")[1]);
			if (value) {
				ClientEvents.isSprintDisabled = true;
			} else {
				ClientEvents.isSprintDisabled = false;

			}
		}

		if (idString.startsWith("isMovementDisabled:")) {
			Boolean value = Boolean.parseBoolean(idString.split(":")[1]);
			if (value) {
				ClientEvents.isMovementDisabled = true;
			} else {
				ClientEvents.isMovementDisabled = false;

			}
		}
		switch (idString) {

		case "100":
			if (!ClientEvents.isSprintDisabled) {
				ClientEvents.isSprintDisabled = true;
			} else {
				ClientEvents.isSprintDisabled = false;
			}
			PacketRegistry.tellServer("100");
			break;
		case "101":
			if (!ClientEvents.isMovementDisabled) {
				ClientEvents.isMovementDisabled = true;
			} else {
				ClientEvents.isMovementDisabled = false;
			}
			PacketRegistry.tellServer("101");
			break;
		case "102":
			break;

		}

		// EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		// NBTTagCompound nbt =
		// player.getEntityData().getCompoundTag("PlayerPersisted");

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