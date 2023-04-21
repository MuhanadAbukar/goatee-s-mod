package com.goatee.tutorial.utils;

import JinRyuu.JRMCore.JRMCoreConfig;
import JinRyuu.JRMCore.JRMCoreH;
import JinRyuu.JRMCore.i.ExtendedPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class DBCUtils {
	public static int getFullAttribute(EntityPlayer p, int attribute) { // gets current player attribute, with all transofrmations/effects calculated for

		int powerType = JRMCoreH.getByte(p, "jrmcPwrtyp");
		int race = JRMCoreH.getByte(p, "jrmcRace");
		int state = JRMCoreH.getByte(p, "jrmcState");
		int state2 = JRMCoreH.getByte(p, "jrmcState2");
		double release = JRMCoreH.getByte(p, "jrmcRelease");
		String sklx = JRMCoreH.getString(p, "jrmcSSltX");
		int resrv = JRMCoreH.getInt(p, "jrmcArcRsrv");
		String absorption = JRMCoreH.getString(p, "jrmcMajinAbsorptionData");
		int[] PlyrAttrbts = JRMCoreH.PlyrAttrbts(p);
		String[] PlyrSkills = JRMCoreH.getString(p, "jrmcSSlts").split(",");
		String statusEffects = JRMCoreH.getString(p, "jrmcStatusEff");
		boolean mj = JRMCoreH.StusEfcts(12, statusEffects);
		boolean c = (JRMCoreH.StusEfcts(10, statusEffects) || JRMCoreH.StusEfcts(11, statusEffects));
		boolean lg = JRMCoreH.StusEfcts(14, statusEffects);
		boolean kk = JRMCoreH.StusEfcts(5, statusEffects);
		boolean mc = JRMCoreH.StusEfcts(13, statusEffects);
		boolean mn = JRMCoreH.StusEfcts(19, statusEffects);
		boolean gd = JRMCoreH.StusEfcts(20, statusEffects);
		return JRMCoreH.getPlayerAttribute(p, PlyrAttrbts, attribute, state, state2, race, sklx, (int) release, resrv, lg, mj, kk, mc, mn, gd, powerType, PlyrSkills, c, absorption);
	}

	public static int getMaxStat(EntityPlayer p, int attribute) { // gets max player stat, 0 dmg 1 def only, rest are bugged
		int race = JRMCoreH.getByte(p, "jrmcRace");
		int powerType = JRMCoreH.getByte(p, "jrmcPwrtyp");
		byte classID = JRMCoreH.getByte(p, "jrmcClass");
		return JRMCoreH.stat(p, attribute, powerType, attribute, getFullAttribute(p, attribute), race, classID, 0.0F);
	}

	public static void setDmgRed(Entity entity, float dmgred) {
		if (entity != null) {
			NBTTagCompound nbt;
			if (entity instanceof EntityPlayer) {
				JRMCoreH.setFloat(100 - dmgred, (EntityPlayer) entity, "dmgred");
			} else {
				System.out.println(entity);
				nbt = JRMCoreH.nbt(entity);
				nbt.setFloat("dmgred", 100 - dmgred);
			}
		}
	}

	public static float getDmgRed(Entity entity) {
		if (entity != null) {
			NBTTagCompound nbt;
			if (entity instanceof EntityPlayer) {
				return JRMCoreH.getFloat((EntityPlayer) entity, "dmgred");
			} else {
				nbt = JRMCoreH.nbt(entity);
				return nbt.getFloat("dmgred");
			}

		}
		throw new NullPointerException();
	}

	public static double getCurrentFormMultiplier(EntityPlayer p) {
		double str = JRMCoreH.PlyrAttrbts(p)[0];
		double maxstr = getFullAttribute(p, 0);
		return ((maxstr > str) ? maxstr : str) / str * 1.0D;

	}

	public static float getPassiveDefense(EntityPlayer player) {
		if ((JRMCoreH.Pwrtyp != 3) && (JRMCoreH.Pwrtyp > 0)) {
			int DEX = JRMCoreH.TransPwrModAtr(JRMCoreH.PlyrAttrbts, 1, JRMCoreH.State, JRMCoreH.State2, JRMCoreH.Race, JRMCoreH.PlyrSkillX, JRMCoreH.curRelease, JRMCoreH.getArcRsrv(),
					JRMCoreH.StusEfctsMe(14), JRMCoreH.StusEfctsMe(12), JRMCoreH.StusEfctsMe(13), JRMCoreH.StusEfctsMe(19), JRMCoreH.Pwrtyp, JRMCoreH.PlyrSkills,
					(JRMCoreH.StusEfctsMe(10)) || (JRMCoreH.StusEfctsMe(11)));
			int SPI = JRMCoreH.PlyrAttrbts[5];
			int statSPI = JRMCoreH.stat(JRMCoreH.Pwrtyp, 5, SPI, JRMCoreH.Race, JRMCoreH.Class, JRMCoreH.SklLvl_KiBs(JRMCoreH.Pwrtyp));
			int def = JRMCoreH.stat(JRMCoreH.Pwrtyp, 1, DEX, JRMCoreH.Race, JRMCoreH.Class, 0.0F);
			double curAtr = (def * 0.01D * JRMCoreH.curRelease * JRMCoreH.weightPerc(1, player));

			if (!JRMCoreH.getBonusAttributes(1).equals("n")) {
				String attributeBonusStr = JRMCoreH.getBonusAttributes(1);
				for (String attributeStr : attributeBonusStr.split("\\|")) {
					if (attributeStr.split(";").length == 2) {
						curAtr *= Float.valueOf(attributeStr.split(";")[1].replaceAll("\\*", ""));
					}
				}
			}

			// Si le joueur bloque
			ExtendedPlayer props = ExtendedPlayer.get(player);
			if (props.getBlocking() == 1) {

				if (!JRMCoreH.PlyrSettingsB(10)) {
					return (float) (curAtr + ((JRMCoreH.SklLvl(11) * 0.005D * statSPI * JRMCoreH.curRelease * 0.01D)));
				}
				return (float) (curAtr);
			} else {
				if (!JRMCoreH.PlyrSettingsB(10)) {
					return (float) (curAtr * JRMCoreConfig.StatPasDef / 100 + ((JRMCoreH.SklLvl(11) * 0.005D * statSPI * JRMCoreH.curRelease * 0.01D)));
				}
				return (float) (curAtr) * JRMCoreConfig.StatPasDef / 100;
			}
		}
		return 0.0F;
	}
}
