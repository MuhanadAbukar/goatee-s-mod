package com.goatee.tutorial.mixins;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.goatee.tutorial.utils.DBCUtils;

import JinRyuu.DragonBC.common.DBCConfig;
import JinRyuu.JRMCore.JRMCoreConfig;
import JinRyuu.JRMCore.JRMCoreH;
import JinRyuu.JRMCore.mod_JRMCore;
import JinRyuu.JRMCore.i.ExtendedPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

@Mixin(JRMCoreH.class)
public abstract class MixinJRMCoreH {

	@Overwrite(remap = false)
	public static boolean isFusionSpectator(Entity Player) {
		if (Player != null && Player instanceof EntityPlayer) {
			NBTTagCompound nbt = Player.getEntityData().getCompoundTag("PlayerPersisted");
			byte powerType = nbt.getByte("jrmcPwrtyp");
			boolean isSpectator = nbt.getBoolean("kiBlastsPassThrough");
			// boolean hasSpectator = nbt.hasKey("SpectatorName");
			// if(hasController) {

			// }
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

	@Overwrite(remap = false)
	public static int jrmcDam(Entity Player, int dbcA, DamageSource s, byte t) { // checks if EnergyAtt is disc, if so cuts saiyan tail and sets form to 0 if ssj4/ape
		if (!Player.worldObj.isRemote && Player instanceof EntityPlayer && t == 2) {
			Random ran = new Random();
			int r = ran.nextInt(4);
			if (r == 0) {
				Player.worldObj.playSoundAtEntity(Player, "jinryuudragonbc:DBC4.disckill", 1.0F, 1.0F);
				JRMCoreH.setByte((byte) 4, (EntityPlayer) Player, "jrmcTlmd");
				int state = JRMCoreH.getByte((EntityPlayer) Player, "jrmcState");
				if (state == 7 || state == 8 || state == 14) {
					JRMCoreH.setByte(0, (EntityPlayer) Player, "jrmcState");
				}
			}
		}

		return jrmcDam(Player, dbcA, s);
	}

	@Overwrite(remap = false)
	public static int jrmcDam(Entity Player, int dbcA, DamageSource s) { // Param1 entity to damage, par2 raw damage(before calcs), par3 source
		if (!Player.worldObj.isRemote && Player instanceof EntityPlayer) { // this methods is only for damaging players, use damageEntity(EntityLivingBase targetEntity, DamageSource source, float
																			// amount) for other entities
			EntityPlayer player = (EntityPlayer) Player;
			boolean dse = (s != null && s.getEntity() != null && s.getEntity() instanceof EntityPlayer); // if attacker is also player
			if (!player.capabilities.isCreativeMode & player != null && s != null) { // if player not gmc
				ExtendedPlayer props = ExtendedPlayer.get(player);
				boolean block = (props.getBlocking() == 1); // is player blocking?
				int[] PlyrAttrbts = JRMCoreH.PlyrAttrbts(player);
				NBTTagCompound nbt = JRMCoreH.nbt(player, "pres");
				byte state = nbt.getByte("jrmcState");
				byte state2 = nbt.getByte("jrmcState2");
				String sklx = JRMCoreH.getString(player, "jrmcSSltX");
				int t = JRMCoreH.SklLvl(4, player); // gets endurance skill level
				byte race = nbt.getByte("jrmcRace");
				byte powerType = nbt.getByte("jrmcPwrtyp");
				byte classID = nbt.getByte("jrmcClass");
				byte release = JRMCoreH.getByte(player, "jrmcRelease");
				int resrv = JRMCoreH.getInt(player, "jrmcArcRsrv");
				String absorption = JRMCoreH.getString(player, "jrmcMajinAbsorptionData");
				int currStamina = JRMCoreH.getInt(player, "jrmcStamina");
				int currEnergy = JRMCoreH.getInt(player, "jrmcEnrgy");
				String ste = JRMCoreH.getString(player, "jrmcStatusEff");
				boolean mj = JRMCoreH.StusEfcts(12, ste);
				boolean lg = JRMCoreH.StusEfcts(14, ste);
				boolean mc = JRMCoreH.StusEfcts(13, ste);
				boolean kk = JRMCoreH.StusEfcts(5, ste);
				boolean mn = JRMCoreH.StusEfcts(19, ste);
				boolean gd = JRMCoreH.StusEfcts(20, ste);

				boolean lf = (s != null && s == DamageSource.fall);

				int DEX = PlyrAttrbts[1];
				int CON = PlyrAttrbts[2];
				String[] ps = JRMCoreH.PlyrSkills(player); // all player skills
				double per = 1.0D;
				int def = 0;
				String x = JRMCoreH.getString(player, "jrmcStatusEff");
				boolean c = (JRMCoreH.StusEfcts(10, x) || JRMCoreH.StusEfcts(11, x));
				if (powerType != 3 && powerType > 0) {
					DEX = JRMCoreH.getPlayerAttribute(player, PlyrAttrbts, 1, state, state2, race, sklx, release, resrv, lg, mj, kk, mc, mn, gd, powerType, ps, c, absorption);
				}

				int kiProtection = 0;
				int kiProtectionCost = 0;
				boolean kiProtectOn = false;

				if (JRMCoreH.pwr_ki(powerType)) { // if dbc

					int maxCON = JRMCoreH.getPlayerAttribute(player, PlyrAttrbts, 2, state, state2, race, sklx, release, resrv, lg, mj, kk, mc, mn, gd, powerType, ps, c, absorption);

					per = ((maxCON > CON) ? maxCON : CON) / CON * 1.0D;
					def = JRMCoreH.stat(player, 1, powerType, 1, DEX, race, classID, 0.0F);
					int SPI = PlyrAttrbts[5];
					int energyPool = JRMCoreH.stat(player, 5, powerType, 5, SPI, race, classID, JRMCoreH.SklLvl_KiBs(ps, powerType));
					def = (int) (def * release * 0.01D * JRMCoreH.weightPerc(1, player));

					kiProtectOn = !JRMCoreH.PlyrSettingsB(player, 10);
					int kiProtectLevel = JRMCoreH.SklLvl(11, ps);
					if (kiProtectOn) {
						kiProtection = (int) (kiProtectLevel * 0.005D * energyPool * release * 0.01D);
						if (kiProtection < 1)
							kiProtection = 1;
						kiProtection = (int) (kiProtection * DBCConfig.cnfKDd);
						float damage = dbcA / 3.0F / (dbcA + "").length();
						if (damage < 1.0F)
							damage = 1.0F;
						kiProtectionCost = (int) (kiProtectLevel * release * 0.01D * damage);
						if (kiProtectionCost < 1)
							kiProtectionCost = 1;
						kiProtectionCost = (int) (kiProtectionCost * DBCConfig.cnfKDc);
					}
					def += kiProtection;
				} else if (JRMCoreH.pwr_cha(powerType)) { // some naruto shit
					int ta = JRMCoreH.SklLvl(0, 2, ps);
					def = JRMCoreH.stat(player, 1, powerType, 1, DEX, race, classID, ta * 0.04F + state * 0.25F);
					def = (int) ((def * release) * 0.01D);
					if (classID == 2) {
						String StE = nbt.getString("jrmcStatusEff");
						if (JRMCoreH.StusEfcts(16, StE)) {
							int WIL = PlyrAttrbts[3];
							int statWIL = JRMCoreH.stat(player, 3, powerType, 5, WIL, race, classID, 0.0F);
							def += (int) (statWIL * 0.25D * release * 0.01D);
						}
					}
				} else if (JRMCoreH.pwr_sa(powerType)) { // some SAO shit
					def = 0;
				} else { // if pwrtyp is 0?
					def = JRMCoreH.stat(player, 1, powerType, 1, DEX, race, classID, 0.0F);
				}
				int staminaCost = (int) ((def - kiProtection) * 0.05F);
				if (block && currStamina >= staminaCost) { // if has enough stamina and blocking
					int id = (int) (Math.random() * 2.0D) + 1;
					player.worldObj.playSoundAtEntity(player, "jinryuudragonbc:DBC4.block" + id, 0.5F, 0.9F / (player.worldObj.rand.nextFloat() * 0.6F + 0.9F));
					JRMCoreH.setInt((currStamina - staminaCost < 0) ? 0 : (currStamina - staminaCost), player, "jrmcStamina");
				} else {
					def = (int) (((def - kiProtection) * JRMCoreConfig.StatPasDef) * 0.01F) + kiProtection;
				}
				if (currEnergy >= kiProtectionCost) { // if has enough ki for kiprot cost
					JRMCoreH.setInt((currEnergy - kiProtectionCost < 0) ? 0 : (currEnergy - kiProtectionCost), player, "jrmcEnrgy");
				} else {
					def -= kiProtection;
				}
				if (JRMCoreConfig.DebugInfo || (JRMCoreH.difp.length() > 0 && player.getCommandSenderName().equalsIgnoreCase(JRMCoreH.difp))) {
					mod_JRMCore.logger.info(player.getCommandSenderName() + " receives Damage: Original=" + dbcA);
				}
				int defensePenetration = 0;
				if (dse) { // get attacker def pen
					String[] ops = JRMCoreH.PlyrSkills((EntityPlayer) s.getEntity()); // gets attacker skills
					defensePenetration = JRMCoreH.SklLvl(14, 1, ops); // get df level
				} else if (s.getEntity() instanceof net.minecraft.entity.EntityLivingBase) {// set non player attacker df
					defensePenetration = 10;
				}

				int dbcAO = dbcA;
				int defense = lf ? 0 : def;
				int defensePen2 = (int) ((defense * defensePenetration) * 0.01F);
				double e = (1.0F - 0.03F * t);
				String ss = "A=" + defense + ((defensePen2 > 0) ? ("-" + defensePenetration + "%") : "") + ", SEM=" + (1.0F - 0.03F * t);
				dbcA = (int) ((dbcA - defense - defensePen2) * e);

				dbcA = (dbcA < 1) ? 1 : dbcA;
				if (((dbcAO * defensePenetration) * 0.01F) * e > dbcA)
					dbcA = (int) (((dbcAO * defensePenetration) * 0.01F) * e);
				//System.out.println("per is " + per); //current form & se multiplier
				int maxdef = DBCUtils.getMaxStat(player, 1);
				float dmgred = JRMCoreH.getFloat(player, "dmgred");

				System.out.println("dba bef is" + dbcA);
				dbcA = (int) (dbcA - (maxdef * release * 0.01F)); // per is attacked player's form multiplier
				dbcA = (int) (dbcA * dmgred / 100F);
				//System.out.println("dba after is" + dbcA);

				if (JRMCoreConfig.DebugInfo || (JRMCoreH.difp.length() > 0 && player.getCommandSenderName().equalsIgnoreCase(JRMCoreH.difp))) {
					mod_JRMCore.logger.info(player.getCommandSenderName() + " DM: A=" + dbcA + ", DF Div:" + per + ", " + ss);
				}
				//System.out.println(player.getCommandSenderName() + " DM: A=" + dbcA + ", DF Div:" + per + ", " + ss);
				if (JRMCoreH.DBC()) { // damage weights
					ItemStack stackbody = (ExtendedPlayer.get(player)).inventory.getStackInSlot(1);
					ItemStack stackhead = (ExtendedPlayer.get(player)).inventory.getStackInSlot(2);
					if (stackbody != null)
						stackbody.damageItem(1, player);
					if (stackhead != null)
						stackhead.damageItem(1, player);

				}
				dbcA = dbcA <= 0 ? 1 : dbcA;
				int curBody = JRMCoreH.getInt(player, "jrmcBdy");
				int all = curBody - dbcA;
				//System.out.println("all is" + all);
				//System.out.println("dba is" + dbcA);

				int set = (all < 0) ? 0 : all; // if curbody after dam < 0, set it to 0, else do nothing
				if (dse) { // friendly fist handler
					boolean friendlyFist = JRMCoreH.PlyrSettingsB((EntityPlayer) s.getEntity(), 12);
					if (friendlyFist && !s.getDamageType().equals("MajinAbsorption")) {

						if (!s.getEntity().equals(Player)) { // KO handler
							int ko = JRMCoreH.getInt(player, "jrmcHar4va");
							set = (all < 20) ? 20 : all;
							if (ko <= 0 && set == 20) {
								JRMCoreH.setInt(6, player, "jrmcHar4va");
								JRMCoreH.setByte((race == 4) ? ((state < 4) ? state : 4) : 0, player, "jrmcState");
								JRMCoreH.setByte(0, player, "jrmcState2");
								JRMCoreH.setByte(0, player, "jrmcRelease");
								JRMCoreH.setInt(0, player, "jrmcStamina");
								JRMCoreH.StusEfcts(19, ste, player, false);

							}
						}
					}
				}
				JRMCoreH.setInt(set, player, "jrmcBdy");

			}
		}
		return dbcA;
	}

}
