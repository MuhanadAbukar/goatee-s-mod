package com.goatee.tutorial.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import JinRyuu.JRMCore.JRMCoreH;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

@Mixin(JRMCoreH.class)
public abstract class MixinJRMCoreH{
	@Overwrite
	public static boolean isFusionSpectator(Entity Player) {
		if (Player != null && Player instanceof EntityPlayer) {
			NBTTagCompound nbt = Player.getEntityData().getCompoundTag("PlayerPersisted");
			byte powerType = nbt.getByte("jrmcPwrtyp");
			boolean isSpectator = nbt.getBoolean("kiBlastsPassThrough");
			if (isSpectator) {
				return true;
			}
			if (JRMCoreH.isPowerTypeKi(powerType)) {
				String Fzn = nbt.getString("jrmcFuzion");
				if (Fzn.contains(",")) {
					String[] fusionMembers = Fzn.split(",");
					if (fusionMembers.length == 3) {
						return fusionMembers[1].equalsIgnoreCase(Player.getCommandSenderName());
					}
				}
			}
		}
		return false;
	}
}
