package com.goatee.tutorial.packets;

import com.goatee.tutorial.mixins.MixinScriptDBCPlayer;
import com.goatee.tutorial.scripted.Player;
import com.goatee.tutorial.scripted.Player.PlayerStats;

import JinRyuu.DragonBC.common.DBCKiTech;
import JinRyuu.JRMCore.JRMCoreKeyHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.settings.KeyBinding;
import noppes.npcs.scripted.entity.ScriptDBCPlayer;

public class ServerPackets implements IMessage {
	private int id;

	public ServerPackets() {
	}

	public ServerPackets(int message) {
		this.id = message;
		System.out.println("packet constructed");

	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.id);
		System.out.println("toBytes");
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.id = buf.readInt();
		System.out.println("fromBytes");
	}

	public static class ServerPacketHandler implements IMessageHandler<ServerPackets, IMessage> {
		public ServerPacketHandler() {
			System.out.println("inpackethandlers");
		}

		@Override
		public IMessage onMessage(ServerPackets message, MessageContext ctx) {
			System.out.println("onMessage");
			int idh = message.id;
			if (idh == 0) {
				System.out.println("id " + idh);
				KeyBinding k1 = JRMCoreKeyHandler.KiFlight;
				KeyBinding.setKeyBindState(k1.getKeyCode(), true);
			}
			if (idh == 1) {
				System.out.println("id " + idh);
				PlayerStats.floating = DBCKiTech.floating;
			}

			if (idh == 2) {
				System.out.println(idh);
			}

			return null;
		}

	}
}
