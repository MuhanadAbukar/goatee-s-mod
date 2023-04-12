package com.goatee.tutorial.mixins;

import java.lang.reflect.Field;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.goatee.tutorial.scripted.Player;
import com.goatee.tutorial.scripted.PlayerStats;

import JinRyuu.JRMCore.JRMCoreEH;
import JinRyuu.JRMCore.JRMCoreH;
import JinRyuu.JRMCore.i.ExtendedPlayer;
import JinRyuu.JRMCore.server.JGPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.api.entity.IPlayer;
import noppes.npcs.scripted.NpcAPI;
import noppes.npcs.scripted.entity.ScriptPlayer;

@Mixin(ScriptPlayer.class)
public abstract class MixinScriptPlayer<T extends EntityPlayerMP> {
	@SuppressWarnings("unchecked")
	public ScriptPlayer<T> p = ((ScriptPlayer<T>) (Object) this);
	public T player = p.player;
	NBTTagCompound nbt = player.getEntityData().getCompoundTag("PlayerPersisted");

	@Unique
	public boolean inAir() {
		return !player.onGround;
	}

	@SuppressWarnings("rawtypes")
	@Unique
	public PlayerStats getPlayerStats() {
		return Player.getAllPlayerStats().get(player.getUniqueID());
	}

	@Unique
	public EntityPlayerMP getEntityPlayerMP() {
		return player;
	}

	@Unique
	public JRMCoreH getJRMCoreH() {

		return new JRMCoreH();
	}

	@Unique
	public JGPlayerMP getJGPlayer() {
		JGPlayerMP JG = new JGPlayerMP(player);
		JG.setNBT(nbt);
		return JG;
	}

	@Unique
	public JRMCoreEH getJRMCoreEH() {
		return new JRMCoreEH();
	}

	@Unique
	public ExtendedPlayer getJRMCExtendedPlayer() {
		return ExtendedPlayer.get(player);
	}

	@Unique
	public IPlayer<?> getPlayer(String playername) {
		return NpcAPI.Instance().getPlayer(playername);
	}

	@Unique
	public void executeCommand(String command) {
		NoppesUtilServer.runCommand(player.getEntityWorld(), "API at " + player.getCommandSenderName(), command);

	}

	@Unique
	public void addPotionEffect(int effect, int effect2, int effect3, int effect4, int duration, int strength,
			boolean hideParticles) {
		if (effect < 0 || effect >= Potion.potionTypes.length || Potion.potionTypes[effect] == null)
			return;

		if (strength < 0)
			strength = 0;

		if (duration < 0)
			duration = 0;

		if (!Potion.potionTypes[effect].isInstant())
			duration *= 20;

		if (duration == 0)
			player.removePotionEffect(effect);
		else
			player.addPotionEffect(new PotionEffect(effect, duration, strength, hideParticles));
		if (effect2 != 0 && effect2 != effect && effect2 != effect3 && effect2 != effect4) {
			player.addPotionEffect(new PotionEffect(effect2, duration, strength, hideParticles));
		}
		if (effect3 != 0 && effect3 != effect && effect3 != effect2 && effect3 != effect4) {
			player.addPotionEffect(new PotionEffect(effect3, duration, strength, hideParticles));
		}
		if (effect4 != 0 && effect4 != effect && effect4 != effect2 && effect4 != effect3) {
			player.addPotionEffect(new PotionEffect(effect4, duration, strength, hideParticles));
		}
		// TODO in 1.8 add hideParticles option
	}

	@Unique
	public void disableSprinting(boolean bo) {
		nbt.setBoolean("isSprintDisabled", bo);

	}

	@Unique
	public void disableMovement(boolean bo) {
		nbt.setBoolean("isMovementDisabled", bo);
		
	}

	@Unique
	public void stuff() {
		// Field campo = KeyBinding.class.getDeclaredField("keybindArray");
		// campo.setAccessible(true);
		Field[] bindfields = KeyBinding.class.getDeclaredFields();
		for (Field field : bindfields) {
			System.out.println(field.getName());
		}

		// return KeyBinding.getKeybinds();
		// return

	}
}
