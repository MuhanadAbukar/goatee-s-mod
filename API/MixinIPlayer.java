package com.goatee.tutorial.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.goatee.tutorial.scripted.PlayerStats;

import JinRyuu.JRMCore.i.ExtendedPlayer;
import JinRyuu.JRMCore.server.JGPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.entity.IPlayer;

@Mixin(IPlayer.class)
public interface MixinIPlayer {

	/*
	 * a list of nbt getters and setters, for easier NBT access
	 */

	@Unique
	public void setStr(String key, String value);

	@Unique
	public void setByte(String key, byte value);

	@Unique
	public void setBo(String key, boolean value);

	@Unique
	public void setInt(String key, int value);

	@Unique
	public void setFloat(String key, float value);

	@Unique
	public void setDouble(String key, double value);

	@Unique
	public String getStr(String key);

	@Unique
	public byte getByte(String key);

	@Unique
	public boolean getBo(String key);

	@Unique
	public int getInt(String key);

	@Unique
	public float getFloat(String key);

	@Unique
	public double getDouble(String key);

	@SuppressWarnings("rawtypes")
	@Unique
	public PlayerStats getPlayerStats();

	@Unique
	public EntityPlayerMP getEntityPlayerMP();

	@Unique
	public JGPlayerMP getJGPlayer(); // JRMC JGPlayer

	@Unique
	public ExtendedPlayer getJRMCExtendedPlayer(); // JRMC extended player

	@Unique
	public IPlayer<?> getPlayer(String playername); // stolen from abstract API, idk thought would be easier here

	@Unique
	public void executeCommand(String command);

	/*
	 * adds up to 4 effects at once with same strength/duration, if only 2 needed, set effect 3 and 4 to 0
	 */
	@Unique
	public void addPotionEffect(int effect, int effect2, int effect3, int effect4, int duration, int strength, boolean hideParticles);

	@Unique
	public void disableSprinting(boolean bo); // disables sprinting keys, regardless of where it is binded

	@Unique
	public void disableMovement(boolean bo);// isables ALL movement keys, forward back left right sprint sneak flight, regardless of where they are binded

	@Unique
	public void setBlind(boolean bo); // non potion effect blindness, this is stronger than potion version

	@Unique
	public boolean inAir(); // checks if player is not touching ground

	@Unique
	public void disableMenu(boolean bo); // disables V jrmc stat sheet, wont ever be used, but changing form masteries while in stat sheet sometimes crashes players,
	// so I added it for my own usage

}
