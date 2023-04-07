package com.goatee.tutorial.Utils;

import com.goatee.tutorial.packets.PacketRegistry;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class ServerUtils {

	public static boolean isFlying(EntityPlayerMP player) {
		NBTTagCompound nbt = player.getEntityData().getCompoundTag("PlayerPersisted");

		return nbt.getBoolean("isFlying");
	}
	
	public static void setFlying(EntityPlayerMP player) {
		NBTTagCompound nbt = player.getEntityData().getCompoundTag("PlayerPersisted");

		nbt.setBoolean("isFlying", true);
		PacketRegistry.tellClient(player, 0);
	}
	
	public static void removeFlying(EntityPlayerMP player) {
		NBTTagCompound nbt = player.getEntityData().getCompoundTag("PlayerPersisted");

		nbt.setBoolean("isFlying", false);
		PacketRegistry.tellClient(player, 1);
	}
}
