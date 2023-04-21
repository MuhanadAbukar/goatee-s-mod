//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.goatee.tutorial.utils;

import java.util.Random;

import JinRyuu.JRMCore.Ds;
import JinRyuu.JRMCore.JRMCoreH;
import JinRyuu.JRMCore.i.ExtendedPlayer;
import JinRyuu.JRMCore.server.config.dbc.JGConfigUltraInstinct;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;

public class UIDodge {
	public static ChatStyle color;
	public static boolean aw;
	public static boolean gk;
	public static boolean dt;

	public UIDodge() {
	}

	public static float getUltraInstinctStaminaCost(EntityPlayer targetPlayer, byte targetState2, float staminaCost) {
		if (JGConfigUltraInstinct.CONFIG_UI_PERCENTAGE_STAMINA_COST) {
			byte pwr = JRMCoreH.getByte(targetPlayer, "jrmcPwrtyp");
			byte rce = JRMCoreH.getByte(targetPlayer, "jrmcRace");
			byte cls = JRMCoreH.getByte(targetPlayer, "jrmcClass");
			int[] PlyrAttrbts = JRMCoreH.PlyrAttrbts(targetPlayer);
			int maxStamina = JRMCoreH.stat(targetPlayer, 2, pwr, 3, PlyrAttrbts[2], rce, cls, 0.0F);
			if (staminaCost > 100.0F) {
				staminaCost = (float) maxStamina;
			} else if (staminaCost == 0.0F) {
				staminaCost = 0.0F;
			} else {
				staminaCost *= (float) maxStamina / 100.0F;
			}
		}

		return staminaCost;
	}

	public static float getUltraInstinctCounterStaminaCost(EntityPlayer targetPlayer, byte targetState2) {
		return getUltraInstinctStaminaCost(targetPlayer, targetState2,
				(float) JGConfigUltraInstinct.CONFIG_UI_DODGE_STAMINA_COST[targetState2]);
	}

	public static float getUltraInstinctDodgeStaminaCost(EntityPlayer targetPlayer, byte targetState2) {
		return getUltraInstinctStaminaCost(targetPlayer, targetState2,
				(float) JGConfigUltraInstinct.CONFIG_UI_COUNTER_STAMINA_COST[targetState2]);
	}

	public static boolean hasUltraInstinctEnoughStaminaToDodge(EntityPlayer targetPlayer, byte targetState2) {
		float currentStamina = (float) JRMCoreH.getInt(targetPlayer, "jrmcStamina");
		float staminaCost = getUltraInstinctDodgeStaminaCost(targetPlayer, targetState2);
		return staminaCost <= currentStamina;
	}

	public static boolean UltraInstinctCounter(DamageSource source, EntityPlayer targetPlayer, byte targetState2) {
		if (!source.getDamageType().equals("UICounter")) {
			byte attackCurrent = JRMCoreH.getUltraInstinctCounterRate(targetPlayer, targetState2);
			if (source.getEntity() != null && (new Random()).nextInt(100) < attackCurrent) {
				DamageSource autoCounterAttack = Ds.causeUICounterDamage(targetPlayer);
				source.getEntity().attackEntityFrom(autoCounterAttack, 1.0F);
				ExtendedPlayer.get(targetPlayer).setUIAnimID((int) (Math.random() * 2.0) + 2);
				ExtendedPlayer.get(targetPlayer).setUIAnim(15);
				return true;
			}
		}

		return false;
	}

	public static boolean UltraInstinctDodge1(EntityPlayer targetPlayer, byte targetState2) {
		byte dodgeCurrent = JRMCoreH.getUltraInstinctDodgeRate(targetPlayer, targetState2);
		boolean dodge = UltraInstinctDodge(targetPlayer, targetState2, dodgeCurrent);
		return dodge;
	}

	public static boolean UltraInstinctDodge2(EntityPlayer targetPlayer, byte targetState2, byte attacker) {
		byte dodgeRate = JRMCoreH.getUltraInstinctDodgeRate(targetPlayer, targetState2);
		byte dodgeCurrent = (byte) getUILevelDodgeDivision(JGConfigUltraInstinct.CONFIG_UI_LEVELS, targetState2,
				attacker, dodgeRate);
		return UltraInstinctDodge(targetPlayer, targetState2, dodgeCurrent);
	}

	public static boolean UltraInstinctDodge(EntityPlayer targetPlayer, byte targetState2, byte dodgeCurrent) {
		int currentStamina = JRMCoreH.getInt(targetPlayer, "jrmcStamina");
		int staminaCost = (int) getUltraInstinctDodgeStaminaCost(targetPlayer, targetState2);
		if ((staminaCost <= 0 || staminaCost <= currentStamina) && (new Random()).nextInt(100) < dodgeCurrent) {
			int newStamina;
			if (staminaCost > 0) {
				newStamina = currentStamina - staminaCost;
				if (newStamina < 0) {
					newStamina = 0;
				}

				if (!JRMCoreH.isInCreativeMode(targetPlayer)) {
					JRMCoreH.setInt(newStamina, targetPlayer, "jrmcStamina");
				}
			}

			newStamina = (int) (Math.random() * 3.0) + 1;
			targetPlayer.worldObj.playSoundAtEntity(targetPlayer, "jinryuudragonbc:DBC4.dodge" + newStamina, 0.5F,
					0.9F / (targetPlayer.worldObj.rand.nextFloat() * 0.6F + 0.9F));
			ExtendedPlayer.get(targetPlayer).setUIAnimID((int) (Math.random() * 2.0));
			ExtendedPlayer.get(targetPlayer).setUIAnim(15);
			targetPlayer.addChatMessage(new ChatComponentText("dodge"));
			return true;
		} else {
			return false;
		}
	}

	public static int getUILevelDodgeDivision(int maxDivision, int target, int attacker, int dodgeRate) {
		float division = (float) getUILevelDivision(maxDivision, target, attacker);
		int result = division == 0.0F ? 0 : (int) ((float) dodgeRate / division);
		return result;
	}

	public static int getUILevelDivision(int maxDivision, int targetUILevel, int attackerUILevel) {
		if (targetUILevel > attackerUILevel) {
			return maxDivision - (targetUILevel - attackerUILevel);
		} else {
			return targetUILevel < attackerUILevel ? maxDivision - (targetUILevel - attackerUILevel) : 0;
		}
	}

	protected static void knockback(EntityLivingBase targetEntity, Entity attacker, int knockbackStrength) {
		if (knockbackStrength > 0) {
			float var25 = MathHelper
					.sqrt_double(attacker.motionX * attacker.motionX + attacker.motionZ * attacker.motionZ);
			if (var25 > 0.0F) {
				targetEntity.addVelocity(
						attacker.motionX * (double) knockbackStrength * 0.6000000238418579 / (double) var25, 0.1,
						attacker.motionZ * (double) knockbackStrength * 0.6000000238418579 / (double) var25);
			}
		}

	}

	protected static void damageEntity(EntityLivingBase targetEntity, DamageSource source, float amount) {
		if (!targetEntity.isEntityInvulnerable()) {
			if (amount <= 0.0F) {
				return;
			}

			if (amount != 0.0F) {
				JRMCoreH.jrmctAll(4, targetEntity.getEntityId() + ";take;" + amount);
				float f2 = targetEntity.getHealth();
				targetEntity.setHealth(f2 - amount);
				targetEntity.func_110142_aN().func_94547_a(source, f2, amount);
				targetEntity.setAbsorptionAmount(targetEntity.getAbsorptionAmount() - amount);
			}
		}

	}

}
