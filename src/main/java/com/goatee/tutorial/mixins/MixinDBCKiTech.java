package com.goatee.tutorial.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.goatee.tutorial.Events.ClientEvents;

import JinRyuu.DragonBC.common.DBCClient;
import JinRyuu.DragonBC.common.DBCConfig;
import JinRyuu.DragonBC.common.DBCKiTech;
import JinRyuu.JRMCore.JRMCoreCliTicH;
import JinRyuu.JRMCore.JRMCoreConfig;
import JinRyuu.JRMCore.JRMCoreH;
import JinRyuu.JRMCore.JRMCoreKeyHandler;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

@Mixin(value = DBCKiTech.class, remap = false)
public class MixinDBCKiTech {
	@Shadow
	public static int ascend = 0;
	@Shadow
	public static int pup = 0;
	@Shadow
	public static int pup2 = 0;
	@Shadow
	public static int ptime = 0;
	@Shadow
	public static int inSuperTime = 0;
	@Shadow
	public static boolean jumping = false;
	@Shadow
	public static boolean jumpRel = false;
	@Shadow
	public static boolean floating = false;
	@Shadow
	public static boolean releasing = false;
	@Shadow
	public static boolean ascending = false;
	@Shadow
	public static boolean ascendingK = false;
	@Shadow
	private static float sec = 0.0F;
	@Shadow
	private static boolean jumpToFly = false;
	@Shadow
	public static float flyY = 0.0F;
	@Shadow
	public static int floatTime = 0;
	@Shadow
	public static boolean floatMultAdded = false;
	@Shadow
	private static boolean dodge_forwDash_STE;
	@Shadow
	private static float sent;
	@Shadow
	public static final int SWOOP_FORWARD = 0;
	@Shadow
	public static final int SWOOP_LEFT = 1;
	@Shadow
	public static final int SWOOP_BACK = 2;
	@Shadow
	public static final int SWOOP_RIGHT = 3;
	@Shadow
	public static int swoopDirection = -1;
	@Shadow
	public static int tickTime = 0;
	@Shadow
	public static int prevTickTime = 0;
	@Shadow
	public static int dash = 0;
	@Shadow
	public static int dodge_per;
	@Shadow
	public static long dodge_recently;
	@Shadow
	public static boolean dodge_forwHold;
	@Shadow
	public static int dodge_forwSwTm;
	@Shadow
	public static boolean turbo = false;
	@Shadow
	public static int ton = 0;
	@Shadow
	public static boolean wasTransformPressed = false;

	@Overwrite(remap = false)
	public static void FloatKi(KeyBinding kiFlight, KeyBinding keyBindJump, KeyBinding keyBindSneak) {
		EntityClientPlayerMP ecPlayerMP = DBCClient.mc.thePlayer;
		GameSettings mc = DBCClient.mc.gameSettings;

		int n = JRMCoreH.SklLvl(3, (byte) 1);
		int st = JRMCoreH.StusEfctsMe(13) ? (JRMCoreH.rc_sai(JRMCoreH.Race) ? JRMCoreH.mstc_sai(JRMCoreH.SklLvlX(1, JRMCoreH.PlyrSkillX) - 1)
				: (JRMCoreH.rc_arc(JRMCoreH.Race) ? JRMCoreH.mstc_arc() : (JRMCoreH.rc_humNam(JRMCoreH.Race) ? JRMCoreH.mstc_humnam() : 1))) : JRMCoreH.State;
		float inc = JRMCoreH.statInc(JRMCoreH.Pwrtyp, 11, 100, JRMCoreH.Race, JRMCoreH.Class, 0.0F) * 0.01F;
		float add = JRMCoreH.spdFrm(JRMCoreH.PlyrAttrbts(null)[4], n, JRMCoreH.curRelease, turbo, true, st, JRMCoreH.State2, inc);
		boolean pressingRightClick = isPressed(mc.keyBindUseItem);
		boolean pressingForward = isPressed(mc.keyBindForward);
		boolean pressingLeft = isPressed(mc.keyBindLeft);
		boolean pressingBack = isPressed(mc.keyBindBack);
		boolean pressingRight = isPressed(mc.keyBindRight);
		boolean isAnyDirectionKeyPressing = (!JRMCoreH.StusEfctsMe(4) && !pressingRightClick && (pressingForward || pressingLeft || pressingBack || pressingRight));
		boolean forw = (isAnyDirectionKeyPressing && isPressed(JRMCoreKeyHandler.Fn));
		if (dodge_forwSwTm == 0 && isPressed(JRMCoreKeyHandler.Fn) && !isPressed(mc.keyBindUseItem) && !isPressed(JRMCoreKeyHandler.KiCharge) && !isPressed(JRMCoreKeyHandler.KiAscend)) {
			dodge_forwSwTm = forw ? 1 : 0;
		}
		if (dodge_forwSwTm > 0 && isAnyDirectionKeyPressing && !ecPlayerMP.onGround) {
			dodge_forwHold = true;
			dodge_forwSwTm++;
		} else {
			dodge_forwHold = false;
			dodge_forwSwTm = 0;
		}
		int[] PlyrAttrbts = JRMCoreH.PlyrAttrbts();
		byte pwr = JRMCoreH.Pwrtyp;
		byte rce = JRMCoreH.Race;
		byte cls = JRMCoreH.Class;
		int maxEnergy = JRMCoreH.stat(ecPlayerMP, 5, pwr, 5, PlyrAttrbts[5], rce, cls, 0.0F);
		int ce2 = JRMCoreH.curEnergy;
		int cst2 = (int) (maxEnergy * 0.1F - n * 0.005F);
		int maxStam = JRMCoreH.stat(ecPlayerMP, 2, pwr, 3, PlyrAttrbts[2], rce, cls, 0.0F);
		int ce = JRMCoreH.curStamina;
		int cst = (int) (maxStam * (0.2F - n * 0.005F));
		boolean dodge = !JRMCoreH.PlyrSettingsB(2);
		if (ce > cst && n > 0 && dodge && ce2 > cst2 && !ecPlayerMP.isRiding()) {
			if (dodge_forwHold && !ecPlayerMP.onGround && dodge_forwHold) {
				if (!dodge_forwDash_STE)
					dodge_forwDash_STE = true;
				if (floating)
					floating = false;
				float s = add * 1.5F;
				float w = JRMCoreH.weightPerc(1);
				s *= w;
				double motionX = 0.0D;
				double motionY = 0.0D;
				double motionZ = 0.0D;
				float yaw = ecPlayerMP.rotationYaw;
				float pitch = ecPlayerMP.rotationPitch;
				if (pressingLeft) {
					yaw -= 90.0F / ((pressingForward || pressingBack) ? 2.0F : 1.0F) * (pressingBack ? -1.0F : 1.0F);
					pitch = 0.0F;
				} else if (pressingRight) {
					yaw += 90.0F / ((pressingForward || pressingBack) ? 2.0F : 1.0F) * (pressingBack ? -1.0F : 1.0F);
					pitch = 0.0F;
				}
				if (pressingForward) {
					pitch = ecPlayerMP.rotationPitch;
				} else if (pressingBack) {
					yaw -= 180.0F;
					pitch = ecPlayerMP.rotationPitch * -1.0F;
				}
				motionX = (-MathHelper.sin(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
				motionZ = (MathHelper.cos(yaw / 180.0F * 3.1415927F) * MathHelper.cos(pitch / 180.0F * 3.1415927F));
				motionY = -MathHelper.sin(pitch / 180.0F * 3.1415927F);
				// System.out.println("fn");
				// FN super speed thing, basically like dynamic flight though?
				setThrowableHeading(ecPlayerMP, motionX, motionY, motionZ, s * (float) JRMCoreConfig.Flngspd, 0.0F);
				if (sec <= 0.0F || dodge_forwSwTm == 2) {
					sec = 10.0F;
					triForce(4, 0, (dodge_forwSwTm == 2) ? 5 : 4);
					chargePart(false);
				}
				if (dodge_forwSwTm == 2) {
					KeyBinding.setKeyBindState(JRMCoreKeyHandler.Fn.getKeyCode(), false);
				}
			}
			if (dodge_forwDash_STE && !dodge_forwHold) {
				dodge_forwDash_STE = false;
				floating = true;
				dodge_forwSwTm = 0;
				KeyBinding.setKeyBindState(JRMCoreKeyHandler.Fn.getKeyCode(), false);
			}
		} else if (dodge_forwHold) {
			if (dodge_forwDash_STE) {
				dodge_forwDash_STE = false;
				floating = true;
				dodge_forwSwTm = 0;
				KeyBinding.setKeyBindState(JRMCoreKeyHandler.Fn.getKeyCode(), false);
			}
		}
		if (sec > 0.0F) {
			sec--;
		}
		boolean isSwooping = JRMCoreH.StusEfctsMe(7);

		if (isSwooping && !dodge_forwDash_STE && sent <= 0.0F) {
			sent = JRMCoreCliTicH.counterValue * 0.5F;
			JRMCoreH.Skll((byte) 5, (byte) 1, (byte) 7);
		} else if (!isSwooping && dodge_forwDash_STE && sent <= 0.0F) {
			sent = JRMCoreCliTicH.counterValue * 0.5F;
			JRMCoreH.Skll((byte) 5, (byte) 0, (byte) 7);
		} else if (sent > 0.0F && sent <= 1.0F && !isSwooping && !dodge_forwDash_STE) {
			sent = 0.0F;
		} else if (sent > 0.0F && sent <= 1.0F && isSwooping && dodge_forwDash_STE) {
			sent = 0.0F;
		}
		if (sent > 0.0F)
			sent--;
		int cost = (int) (1.0F + add);
		boolean able = true;
		boolean b = (n == 0);
		if (DBCConfig.flyAnyLvl)
			b = false;
		if (JRMCoreH.curEnergy < cost || b) {
			able = false;
		}
		if (DBCConfig.oldFly) {
			if (isPressed(kiFlight) || (isPressed(JRMCoreKeyHandler.Fn) && isPressed(kiFlight) && able)) {
				floatTime++;
				if (floatTime >= 20) {
					JRMCoreH.Cost(cost);
					floatTime = 0;
				}
				if (isPressed(JRMCoreKeyHandler.Fn)) {
					ecPlayerMP.motionY /= 15.15D;
				} else {
					ecPlayerMP.motionY = (0.6F * add * (float) JRMCoreConfig.Flngspd);
				}
				float par1 = ecPlayerMP.moveStrafing;
				float par2 = ecPlayerMP.moveForward;
				if (isPressed(DBCClient.mc.gameSettings.keyBindForward) || isPressed(DBCClient.mc.gameSettings.keyBindBack) || isPressed(DBCClient.mc.gameSettings.keyBindLeft)
						|| isPressed(DBCClient.mc.gameSettings.keyBindRight)) {
					mv(par1, par2, ecPlayerMP, add);
					floatMultAdded = true;
				} else {
					ecPlayerMP.motionX = 0.0D;
					ecPlayerMP.motionZ = 0.0D;
					floatMultAdded = false;

				}

			}

		} else {
			if (isPressed(kiFlight)) {
				KeyBinding.setKeyBindState(kiFlight.getKeyCode(), false);
				if (able) {
					if (!floating) {
						if (ecPlayerMP.onGround) { // set to flying
							ecPlayerMP.motionY = 0.5D;
							jumpToFly = true;
						}
					} else {
						floatMultAdded = false;
					}
					floating = !floating;
				}
			}

			if (floating && able) {
				if (!ecPlayerMP.onGround) {
					floatTime++;
					if (floatTime >= 20) {
						JRMCoreH.Cost(cost);
						floatTime = 0;
					}
					if (isPressed(keyBindJump)) { // go up
						// System.out.println("go up");
						jumpRel = true;
						ecPlayerMP.motionY = (0.25F * add * (float) JRMCoreConfig.Flngspd);
					} else if (isPressed(keyBindSneak)) {
						// System.out.println("go down");
						if (ecPlayerMP.motionY > -(0.25F * add)) {
							ecPlayerMP.motionY = (-(0.25F * add) * (float) JRMCoreConfig.Flngspd);
						}
					} else if (ecPlayerMP.motionY < 0.0D) {
						// System.out.println("gravity");
						if (ClientEvents.isFlightGravityDisabled) {
							ecPlayerMP.motionY /= 150.15D;
						} else {
							ecPlayerMP.motionY /= ((JRMCoreH.isShtng || !JRMCoreConfig.PlayerFlyingDragDownOn) ? 150.15D : 15.15D);
						}
					}
					float par1 = ecPlayerMP.moveStrafing;
					float par2 = ecPlayerMP.moveForward;
					if (isPressed(mc.keyBindForward) || isPressed(mc.keyBindBack) || isPressed(mc.keyBindLeft) || isPressed(mc.keyBindRight)) {
						if (JRMCoreH.StusEfctsMe(9) && !JRMCoreH.StusEfctsMe(4) && !isPressed(mc.keyBindBack) && !isPressed(mc.keyBindLeft) && !isPressed(DBCClient.mc.gameSettings.keyBindRight)) { // &&
							// if "D" and pressing only W, do dynamic flight
							System.out.println("yo");

							float s = add;
							float wei = JRMCoreH.weightPerc(1);
							s *= wei;
							int back = 0;
							int right = 0;
							int left = 0;
							double motionX = (-MathHelper.sin((ecPlayerMP.rotationYaw + back + left + right) / 180.0F * 3.1415927F) * MathHelper.cos(ecPlayerMP.rotationPitch / 180.0F * 3.1415927F));
							double motionZ = (MathHelper.cos((ecPlayerMP.rotationYaw + back + left + right) / 180.0F * 3.1415927F) * MathHelper.cos(ecPlayerMP.rotationPitch / 180.0F * 3.1415927F));
							double motionY = -MathHelper.sin((ecPlayerMP.rotationPitch + back) / 180.0F * 3.1415927F);
							setThrowableHeading(ecPlayerMP, motionX, motionY, motionZ, s * (float) JRMCoreConfig.Flngspd, 0.0F);
						} else {
							System.out.println("move");
							mv(par1, par2, ecPlayerMP, add * (float) JRMCoreConfig.Flngspd);
						}
						floatMultAdded = true;
					} else {
						// System.out.println("standing still");
						ecPlayerMP.motionX = 0.0D;
						ecPlayerMP.motionZ = 0.0D;
						floatMultAdded = false;
					}
				}
			}

			if (floating && ecPlayerMP.onGround) {
				if (jumpToFly) {
					jumpToFly = false;
				} else {
					floating = false;
					floatMultAdded = false;
				}
			}
		}
	}

	@Overwrite(remap = false)
	public static void mv(float strafe, float frward, EntityPlayer var4, float add) {
		System.out.println("mv");

		float f3 = strafe * strafe + frward * frward;
		if (f3 >= 1.0E-4F) {
			f3 = MathHelper.sqrt_float(f3);
			if (f3 < 1.0F) {
				f3 = 1.0F;
			}
			f3 = add / f3;
			float f4 = MathHelper.sin(var4.rotationYaw * 3.1415927F / 180.0F);
			float f5 = MathHelper.cos(var4.rotationYaw * 3.1415927F / 180.0F);
			strafe *= f3;
			frward *= f3;
			float speed = var4.onGround ? 0.25F : 0.25F;
			var4.motionX = ((strafe * f5 - frward * f4) * speed);
			var4.motionZ = ((frward * f5 + strafe * f4) * speed);
		}
	}

	@Overwrite(remap = false)
	public static void setThrowableHeading(Entity e, double par1, double par3, double par5, float par7, float par8) { // multiplies motionxyz by config flightspeed
		System.out.println("setting throwable heading");

		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
		float s = 1;
		e.motionX = par1 * s;
		e.motionY = par3 * s;
		e.motionZ = par5 * s;
	}

	@Shadow(remap = false)
	public static boolean isPressed(KeyBinding k) {
		return false;
	}

	@Shadow
	public static void triForce(int i, int j, int k) {
	}

	@Shadow
	public static void chargePart(boolean b) {
	}

}
