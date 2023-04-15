package com.goatee.tutorial.packets;

import com.goatee.tutorial.CombatMode.KeyHandler;
import com.goatee.tutorial.Events.ClientEvents;

import JinRyuu.DragonBC.common.DBCKiTech;
import JinRyuu.JRMCore.JRMCoreKeyHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;

public class PacketTellClient implements IMessageHandler<PacketTellClient.msg, IMessage> {

	public IMessage onMessage(msg message, MessageContext ctx) {

		String idString = message.idMsg;
		System.out.println("At Client idMsg = " + message.idMsg);

		// EntityClientPlayerMP p = Minecraft.getMinecraft().thePlayer;
		// NBTTagCompound nbt = p.getEntityData().getCompoundTag("PlayerPersisted");
		goThroughStringIDs(idString);
		switch (idString) {
		case "1":
			break;
		}
		return null;
	}

	public void goThroughStringIDs(String idString) {
		if (idString.contains(":")) {
			Boolean value = Boolean.parseBoolean(idString.split(":")[1]);
			if (idString.startsWith("isFlying:")) {
				if (value && !DBCKiTech.floating) {
					KeyBinding.setKeyBindState(JRMCoreKeyHandler.KiFlight.getKeyCode(), true);
				} else if (!value && DBCKiTech.floating) {
					DBCKiTech.floating = false;
				}
			}
			if(idString.equals("checkFlying")) {
				if(!DBCKiTech.floating) {
					PacketRegistry.tellServer("2");					
				}
			}
			if (idString.startsWith("isSprintDisabled:")) {
				if (value) {
					ClientEvents.isSprintDisabled = true;
				} else {
					ClientEvents.isSprintDisabled = false;
				}
			}
			if (idString.startsWith("isMovementDisabled:")) {
				if (value) {
					ClientEvents.isMovementDisabled = true;

				} else {
					ClientEvents.isMovementDisabled = false;

				}
			}
			if (idString.startsWith("CombatMode:")) {
				// System.out.println("reached");
				if (value && !ClientEvents.isCombatModeEnabled) {
					// System.out.println("combat mode enabled");
					KeyHandler.saveAllKeybinds();
					KeyHandler.unbindNonCombatKeys();
					ClientEvents.isCombatModeEnabled = true;
					Minecraft.getMinecraft().thePlayer
							.addChatMessage(new ChatComponentText("§lCombat Mode has been enabled!"));
				} else if (!value && ClientEvents.isCombatModeEnabled) {
					// System.out.println("combat mode disabled");
					KeyHandler.reAddSavedKeys();
					ClientEvents.isCombatModeEnabled = false;
					Minecraft.getMinecraft().thePlayer
							.addChatMessage(new ChatComponentText("§lCombat Mode has been disabled!"));
				}
			}
			if (idString.startsWith("isBlind:")) {
				if (value) {
					ClientEvents.isBlind = true;
				} else {
					ClientEvents.isBlind = false;
				}
			}
		}
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