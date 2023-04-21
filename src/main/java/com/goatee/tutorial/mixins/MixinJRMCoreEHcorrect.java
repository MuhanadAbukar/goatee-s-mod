package com.goatee.tutorial.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.goatee.tutorial.utils.UIDodge;

import JinRyuu.DragonBC.common.DBCConfig;
import JinRyuu.JRMCore.Ds;
import JinRyuu.JRMCore.JRMCoreConfig;
import JinRyuu.JRMCore.JRMCoreEH;
import JinRyuu.JRMCore.JRMCoreH;
import JinRyuu.JRMCore.JRMCoreHDBC;
import JinRyuu.JRMCore.JRMCoreHSAC;
import JinRyuu.JRMCore.mod_JRMCore;
import JinRyuu.JRMCore.entity.EntityEnergyAtt;
import JinRyuu.JRMCore.entity.EntityNPCshadow;
import JinRyuu.JRMCore.i.ExtendedPlayer;
import JinRyuu.JRMCore.server.config.dbc.JGConfigDBCFormMastery;
import JinRyuu.JRMCore.server.config.dbc.JGConfigUltraInstinct;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

@Mixin(value = JRMCoreEH.class, remap = false)
public abstract class MixinJRMCoreEHcorrect {
	
	boolean jfc = JRMCoreH.JFC();
	boolean dbc = JRMCoreH.DBC();
	boolean nc = JRMCoreH.NC();
	boolean saoc = JRMCoreH.SAOC();
	float red = 1.0F;
	float green = 1.0F;
	float blue = 1.0F;
	boolean setCol = false;
	float density = 1.0F;
	boolean setDen = false;

	@SubscribeEvent
	public void Sd35MR(LivingHurtEvent event) {
		//System.out.println("shiiid i let bro relax 100 " + event.ammount);

		float amount = event.ammount;
		DamageSource source = event.source;
		EntityLivingBase targetEntity = event.entityLiving;
		if (targetEntity instanceof EntityPlayer && JRMCoreH.isInCreativeMode(targetEntity)) {
			event.ammount = 0.0F;
			return;

		}
		if (targetEntity instanceof EntityPlayer && source != DamageSource.outOfWorld) {// if attacking fusionspectator, cancel event

			String fusion = JRMCoreH.getString((EntityPlayer) targetEntity, "jrmcFuzion");
			if (fusion.contains(",")) {

				String[] fusionArray = fusion.split(",");
				if (targetEntity.getCommandSenderName().equalsIgnoreCase(fusionArray[1])) {

					event.ammount = 0.0F;
					event.setCanceled(true);
					return;
				}
			}
		}

		if (targetEntity instanceof EntityNPCshadow) {// if attacking a shadow dummy that's not your own, despawn dummy
			EntityNPCshadow e = (EntityNPCshadow) targetEntity;
			if (source.getEntity() instanceof EntityLivingBase && e.getSummoner() != source.getEntity()) {
				e.setDead();
			}
		}

		if (source.getDamageType().equals("EnergyAttack") && source.getSourceOfDamage() instanceof EntityEnergyAtt) { // if attacker is EnergyAtt, set ammount to 0
			event.ammount = 0.0F;
		}

		if (targetEntity instanceof EntityPlayer && source.getEntity() instanceof EntityPlayer) {// if both are players && idk something dim related, cancel attack

			String s = JRMCoreH.rwip(FMLCommonHandler.instance().getMinecraftServerInstance(), targetEntity.dimension + "");
			if (s.equalsIgnoreCase("false")) {

				event.ammount = 0.0F;

				return;
			}
		}

		if (!event.isCanceled() && (this.dbc || this.nc || this.saoc) && amount != 0.0F && source != Ds.OutOfBodyHealth && source != Ds.NotAlive && source != DamageSource.outOfWorld) {

			if (source.getEntity() != null && source.getEntity() instanceof EntityPlayer) {// if attacker is player

				EntityPlayer attacker = (EntityPlayer) source.getEntity();

				String fusion = JRMCoreH.getString(attacker, "jrmcFuzion");
				if (fusion.contains(",")) {// cancel event if fusion controller/spectator attack each other
					String[] fusionArray = fusion.split(",");
					if (attacker.getCommandSenderName().equalsIgnoreCase(fusionArray[0]) && targetEntity.getCommandSenderName().equalsIgnoreCase(fusionArray[1])) {

						event.setCanceled(true);
						return;
					}
					if (attacker.getCommandSenderName().equalsIgnoreCase(fusionArray[1]) && targetEntity.getCommandSenderName().equalsIgnoreCase(fusionArray[0])) {

						event.setCanceled(true);
						return;
					}
					if (targetEntity.getCommandSenderName().equalsIgnoreCase(fusionArray[1])) {

						event.setCanceled(true);

						return;
					}
				}
				boolean ultraInstinctCounter = source.getDamageType().equals("UICounter");
				boolean Melee = (ultraInstinctCounter || (source.getSourceOfDamage() == source.getEntity() && source.getDamageType().equals("player")));
				boolean energyAtt = (source.getDamageType().equals("EnergyAttack") && source.getSourceOfDamage() instanceof EntityEnergyAtt);
				boolean Projectile = (source.getSourceOfDamage() instanceof net.minecraft.entity.IProjectile && !energyAtt);

				int powerType = JRMCoreH.getByte(attacker, "jrmcPwrtyp");
				int race = JRMCoreH.getByte(attacker, "jrmcRace");
				int state = JRMCoreH.getByte(attacker, "jrmcState");
				int state2 = JRMCoreH.getByte(attacker, "jrmcState2");
				int classID = JRMCoreH.getByte(attacker, "jrmcClass");
				double release = JRMCoreH.getByte(attacker, "jrmcRelease");
				String sklx = JRMCoreH.getString(attacker, "jrmcSSltX");
				int resrv = JRMCoreH.getInt(attacker, "jrmcArcRsrv");
				String absorption = JRMCoreH.getString(attacker, "jrmcMajinAbsorptionData");
				int[] PlyrAttrbts = JRMCoreH.PlyrAttrbts(attacker);
				String[] PlyrSkills = JRMCoreH.getString(attacker, "jrmcSSlts").split(",");

				String statusEffects = JRMCoreH.getString(attacker, "jrmcStatusEff");
				boolean mj = JRMCoreH.StusEfcts(12, statusEffects);

				boolean lg = JRMCoreH.StusEfcts(14, statusEffects);
				boolean kk = JRMCoreH.StusEfcts(5, statusEffects);
				boolean mc = JRMCoreH.StusEfcts(13, statusEffects);
				boolean mn = JRMCoreH.StusEfcts(19, statusEffects);
				boolean gd = JRMCoreH.StusEfcts(20, statusEffects);
				int currentStamina = JRMCoreH.getInt(attacker, "jrmcStamina");
				int currentEnergy = JRMCoreH.getInt(attacker, "jrmcEnrgy");

				int STR = PlyrAttrbts[0];
				int DEX = PlyrAttrbts[1];

				float dam = amount;
				float den = 0.0F;
				int ml = JRMCoreH.stat(0, attacker, 0, STR, 0.0F);
				int cst = (int) (ml * 0.1F);
				int cstF = 0;
				int cstF2 = 0;
				int handEffectID = 0;

				if (JRMCoreH.isPowerTypeKi(powerType)) {// dbc

					if (JGConfigDBCFormMastery.FM_Enabled) {
						JRMCoreH.addToFormMasteriesValue(attacker, JGConfigDBCFormMastery.FM_GainDamageDealt, JGConfigDBCFormMastery.FM_GainDamageDealt, race, state, state2, kk, mc, mn, gd, 1);
					}

					boolean c = (JRMCoreH.StusEfcts(10, statusEffects) || JRMCoreH.StusEfcts(11, statusEffects));

					if (Melee) {

						int sklkf = JRMCoreH.SklLvl(12, PlyrSkills);
						boolean sklkfe = !JRMCoreH.PlyrSettingsB(attacker, 9);
						int sklks = 0;
						int sklks2 = 0;
						if (sklkf > 0 && sklkfe) {
							int SPI = PlyrAttrbts[5];
							int statSPI = JRMCoreH.stat(attacker, 5, powerType, 5, SPI, race, classID, JRMCoreH.SklLvl_KiBs(PlyrSkills, powerType));
							sklks = (int) (sklkf * 0.0025D * statSPI * release * 0.01D);
							if (sklks > 0) {
								cstF = (int) (sklks * DBCConfig.cnfKFc);
								if (currentEnergy <= cstF) {
									sklks = 0;
									cstF = 0;
								}
								sklks = (int) (sklks * DBCConfig.cnfKFd);
							}
						}

						STR = JRMCoreH.getPlayerAttribute(attacker, PlyrAttrbts, 0, state, state2, race, sklx, (int) release, resrv, lg, mj, kk, mc, mn, gd, powerType, PlyrSkills, c, absorption);
						int dmg = JRMCoreH.stat(attacker, 0, powerType, 0, STR, race, classID, 0.0F);
						// System.out.println("max atk stat" + dmg);

						double curAtr = dmg * release * 0.01D * JRMCoreH.weightPerc(0, attacker);

						boolean sklkfe2 = JRMCoreH.PlyrSettingsB(attacker, 13);
						boolean sklkfe3 = JRMCoreH.PlyrSettingsI(attacker, 13, 1);
						int skf = JRMCoreH.SklLvl(15, PlyrSkills);
						boolean hasKiWeaponEnabled = (sklkf > 0 && skf > 0 && sklkfe2);

						if (hasKiWeaponEnabled) {

							int WIL = JRMCoreH.getPlayerAttribute(attacker, PlyrAttrbts, 3, state, state2, race, sklx, (int) release, resrv, lg, mj, kk, mc, mn, gd, powerType, PlyrSkills, c,
									absorption);
							attacker.worldObj.playSoundAtEntity(attacker, "jinryuudragonbc:DBC4.kiblade2", 1.0F, 1.0F);
							int kiWeaponCost = 0;
							int kiWeaponDamage = 0;

							int dmg1 = (int) (JRMCoreH.stat(attacker, 3, powerType, 4, WIL, race, classID, 0.0F) * 0.01F);
							float data1 = (int) (0.005D * dmg1 * release * 0.01D * (sklkfe3 ? DBCConfig.cnfKCsd : DBCConfig.cnfKBld) * JRMCoreConfig.dat5699);
							float data2 = (int) (0.005D * dmg1 * release * 0.01D * (sklkfe3 ? DBCConfig.cnfKCsc : DBCConfig.cnfKBlc));
							kiWeaponCost = (int) (kiWeaponCost + data2 / ((sklkf > 1) ? (sklkf * 0.3F + 1.0F) : 1.0F));
							kiWeaponDamage = (int) (kiWeaponDamage + sklkf * data1);
							if (sklks2 > 0) {

								cstF2 = sklks2;
								if (currentEnergy <= cstF2) {

									sklks2 = 0;
									cstF2 = 0;
								}
								sklks2 = 0;
							}

							dmg1 = (int) (JRMCoreH.stat(attacker, 3, powerType, 4, WIL, race, classID, 0.0F) * 0.01F);
							data1 = (float) (dmg1 * release * 0.009999999776482582D * JRMCoreH.weightPerc(1, attacker) * (sklkfe3 ? DBCConfig.cnfKCsd : DBCConfig.cnfKBld) * JRMCoreConfig.dat5700);
							data2 = (float) (dmg1 * release * 0.009999999776482582D * JRMCoreH.weightPerc(1, attacker) * (sklkfe3 ? DBCConfig.cnfKCsc : DBCConfig.cnfKBlc));
							kiWeaponCost = (int) (kiWeaponCost + data2 / ((skf > 1) ? (skf * 0.3F + 1.0F) : 1.0F));
							kiWeaponDamage = (int) (kiWeaponDamage + skf * data1);
							if (sklks2 > 0) {

								cstF2 = sklks2;
								if (currentEnergy <= cstF2) {

									sklks2 = 0;
									cstF2 = 0;
								}
								sklks2 = 0;
							}

							if (kiWeaponCost > 0 && currentEnergy >= kiWeaponCost) {

								dam += kiWeaponDamage;
								currentEnergy -= kiWeaponCost;
								if (!JRMCoreH.isInCreativeMode(attacker))
									JRMCoreH.setInt(currentEnergy - kiWeaponCost, attacker, "jrmcEnrgy");

							}
						}

						if (JRMCoreConfig.DebugInfo) {
							mod_JRMCore.logger
									.info(attacker.getCommandSenderName() + " attacks " + targetEntity.getCommandSenderName() + " with melee " + curAtr + "+" + sklks + "=" + (curAtr + sklks));
						}

						dam = (float) (dam + curAtr + sklks);
					} else if (Projectile) {
						cst = 1;
						int WIL = JRMCoreH.getPlayerAttribute(attacker, PlyrAttrbts, 3, state, state2, race, sklx, (int) release, resrv, lg, mj, kk, mc, mn, gd, powerType, PlyrSkills, c, absorption);
						int dmg = (int) (JRMCoreH.stat(attacker, 3, powerType, 4, WIL, race, classID, 0.0F) * 0.01F);
						int skf = JRMCoreH.SklLvl(15, PlyrSkills);
						dam = (float) (dam + dmg * release * 0.004999999888241291D * skf * JRMCoreH.weightPerc(1, attacker));
					}

				} else if (powerType == 2 && Melee) {// some naruto shit

					int effectDamage = 0;

					if (JRMCoreH.NC() && attacker != null) {

						handEffectID = ExtendedPlayer.get(attacker).getHandEffect();
						effectDamage = ExtendedPlayer.get(attacker).getEffect_used2();
						if (handEffectID < 3 && handEffectID > 0 && attacker != null && attacker instanceof EntityPlayer) {

							if (handEffectID == 1 || handEffectID == 2) {

								JRMCoreH.newExpl(targetEntity.worldObj, attacker, targetEntity.posX, targetEntity.posY, targetEntity.posZ, 0.0F, false, 0.0D, attacker, (byte) (2 + handEffectID));

								ExtendedPlayer.get(attacker).setHandEffect(0);
								ExtendedPlayer.get(attacker).setEffect_used(0);
								ExtendedPlayer.get(attacker).setEffect_used2(0);
							}
						}
					}

					STR = JRMCoreH.getPlayerAttribute(attacker, PlyrAttrbts, 0, state, state2, race, sklx, (int) release, resrv, lg, mj, false, false, false, false, powerType, PlyrSkills, false, "0");
					int ta = JRMCoreH.SklLvl(0, 2, PlyrSkills);
					int cj = JRMCoreH.SklLvlY(2, JRMCoreH.getString(attacker, "jrmcSSltY"));
					den = (classID == 1) ? ((cj * JRMCoreConfig.hedp) * 0.01F) : 0.0F;
					int dmg = JRMCoreH.stat(attacker, 0, powerType, 0, STR, race, classID, ta * 0.04F + state * 0.25F);
					dam = (float) (dam + dmg * release * 0.009999999776482582D + effectDamage);
				} else if (powerType == 3 && Melee) {// some SAO shit

					int WeaponDamage = JRMCoreHSAC.getWeaponDamage(attacker.getCurrentEquippedItem(), STR);
					STR += JRMCoreHSAC.getWeaponBonus(attacker.getCurrentEquippedItem(), 0);
					DEX += JRMCoreHSAC.getWeaponBonus(attacker.getCurrentEquippedItem(), 1);
					int dmg = (int) JRMCoreHSAC.getDamage(WeaponDamage, STR, DEX);
					dam += dmg;
					cst = 0;
				}

				if (ultraInstinctCounter) {// if source is UI counter, multiply damage by UI attack percentage
					dam *= JGConfigUltraInstinct.CONFIG_UI_ATTACK_DAMAGE_PERCENTAGE[JRMCoreH.state2UltraInstinct(!mn, (byte) state2)] * 0.01F;
				}

				dam = (dam <= 0.0F) ? 1.0F : dam;

				int UI_cost = 0;

				if (Melee) { // calculate ui counter sta cost
					if (ultraInstinctCounter) {
						UI_cost = (int) UIDodge.getUltraInstinctCounterStaminaCost(attacker, (byte) JRMCoreH.state2UltraInstinct(!mn, (byte) state2));
					}
				}

				cst = (int) (cst * JRMCoreConfig.cnfPnchc + UI_cost); // calculates stamina cost for punch

				if (currentStamina > cst && dam > 0.0F) {// if attacking player has enough sta for sta cost

					event.ammount = 0.0F;
					boolean doAttack = true;

					if (this.dbc && JRMCoreConfig.sfzns) { // check if attack is in safezone
						if (JRMCoreHDBC.JRMCoreEHonLivingHurtSafeZone(targetEntity)) {
							String t = JRMCoreH.cly + StatCollector.translateToLocal("dbc.entitymasters.nofightinsafe");
							attacker.addChatMessage(new ChatComponentText(JRMCoreH.cly + t));
							event.ammount = 0.0F;
							return;
						}
					}
					// if not in safezone
					if (doAttack) {

						if (Melee) {
							boolean take_stamina = ultraInstinctCounter ? ((UI_cost > 0)) : true;
							if (take_stamina) {

								int new_stamina = currentStamina - cst;
								if (!JRMCoreH.isInCreativeMode(attacker))
									JRMCoreH.setInt(new_stamina, attacker, "jrmcStamina");

							}
							if (cstF > 0) {
								if (currentEnergy >= cstF) {
									if (!JRMCoreH.isInCreativeMode(attacker))
										JRMCoreH.setInt(currentEnergy - cstF, attacker, "jrmcEnrgy");

								}
							}
						}
						int dealt = (int) dam;
						int epoch = (int) (System.currentTimeMillis() / 1000L);

						if (targetEntity instanceof EntityPlayer) { // THIS BLOCK IS RESPONSIBLE FOR ALL PLAYER VS PLAYER DAMAGE
							// System.out.println("attacked player");

							EntityPlayer targetPlayer = (EntityPlayer) targetEntity;
							if (JRMCoreH.getByte(targetPlayer, "jrmcAccept") == 0 || JRMCoreH.getByte(targetPlayer, "jrmcPwrtyp") == 0) { // cancel attack if pwrtype or accpt 0
								event.setCanceled(false);
								return;
							}

							dam = damageEntity(targetPlayer, source, dam); // damage targetPlayer's armor

							JRMCoreH.a1t3(targetPlayer);
							JRMCoreH.setInt(epoch + 5, targetPlayer, "jrmcAttackLstPlyrTm");
							JRMCoreH.setString(attacker.getUniqueID().toString(), targetPlayer, "jrmcAttackLstPlyrNam");
							if (this.nc && JRMCoreH.clsTypOn(attacker) == 1 && JRMCoreH.getByte(attacker, "jrmcPwrtyp") == 2) { // some naruto shit

								JRMCoreH.jrmcEnergyDam(targetPlayer, (int) (dam * den), source);
							}
							if (energyAtt) { // if attacker does EnergyAttt
								//System.out.println("energyAtt");
								dealt = JRMCoreH.jrmcDam(targetPlayer, (int) dam, source, ((EntityEnergyAtt) source.getSourceOfDamage()).getType());
							} else if (powerType == 1 && Projectile) { // if attacker uses non projectile
								//System.out.println("projectile");
								int skf = JRMCoreH.SklLvl(15, PlyrSkills);
								int cost = (int) (dam * 0.005D * skf * DBCConfig.cnfKIc);
								if (currentEnergy >= cost) {
									if (!JRMCoreH.isInCreativeMode(attacker))
										JRMCoreH.setInt(currentEnergy - cost, attacker, "jrmcEnrgy");
									dealt = JRMCoreH.jrmcDam(targetPlayer, (int) (dam * DBCConfig.cnfKId), source);
									knockback(targetPlayer, attacker, 1);
								} else {
									event.setCanceled(false);
									return;
								}
							} else { // ALL MELEE, THIS IS WHAT I WANT
								// System.out.println("pvp melee")

								// System.out.println("raw damage " + dam);

								dealt = JRMCoreH.jrmcDam(targetPlayer, (int) dam, source);
								System.out.println("dam after all sort of calcs  " + dealt);
								knockback(targetPlayer, attacker, 1);
							}

						} else if (!this.dbc || JRMCoreH.DGE(targetEntity)) {// if attacked is not spacepod/kami/porunga/nimbus and not player, DAMAGE THE ENTITY
							//System.out.println("attacked nonplayer");
							if (powerType == 1 && Projectile) { // if dbc && projectile
								// System.out.println("idk nonplayer");
								int skf = JRMCoreH.SklLvl(15, PlyrSkills);
								int cost = (int) (dam * 0.005D * skf * DBCConfig.cnfKIc);
								if (currentEnergy >= cost) {
									if (!JRMCoreH.isInCreativeMode(attacker))
										JRMCoreH.setInt(currentEnergy - cost, attacker, "jrmcEnrgy");
									dealt = (int) dam;
									damageEntity(targetEntity, source, (int) (dam * DBCConfig.cnfKId));
									knockback(targetEntity, attacker, 1);
									knockback(targetEntity, attacker, 1);
								} else {
									event.setCanceled(false);
									return;
								}
							} else { // All player vs non player damage
								// System.out.println("idk2 nonplayer");
								dealt = (int) dam;
								damageEntity(targetEntity, source, dam); // this method is responsible for all non player damage dealing

								if (handEffectID == 1) { // some naruto shit
									damageEntity(targetEntity, source, dam);
									float push = 1.0F;
									targetEntity.motionX += ((attacker.posX > targetEntity.posX) ? -push : push);
									targetEntity.motionY += ((attacker.posY > targetEntity.posY) ? -push : push);
									targetEntity.motionZ += ((attacker.posZ > targetEntity.posZ) ? -push : push);
									targetEntity.velocityChanged = true;
								} else if (handEffectID == 0) {
									knockback(targetEntity, attacker, 1);
								}

							}
						}

						if (!attacker.worldObj.isRemote && (!this.dbc || JRMCoreH.DGE(targetEntity)) && attacker != null) { // TP handler
							boolean giveTP = true;
							if (source.getSourceOfDamage() != null && energyAtt) {
								EntityEnergyAtt kiAttack = (EntityEnergyAtt) source.getSourceOfDamage();
								if (kiAttack.givenTP) {

									giveTP = false;
								} else {
									kiAttack.givenTP = true;
								}
							}
							if (giveTP) {
								int tp = 1;
								if (targetEntity instanceof EntityPlayer) {

									int[] PlyrAttrbtsTar = JRMCoreH.PlyrAttrbts((EntityPlayer) targetEntity);
									int rltar = JRMCoreH.getByte(attacker, "jrmcRelease");
									float tartp = PlyrAttrbtsTar[4] / JRMCoreConfig.TpgnRt * JRMCoreConfig.TPGainRateRace[race] * rltar * 0.01F;
									float atttp = (float) ((PlyrAttrbts[4] / JRMCoreConfig.TpgnRt * JRMCoreConfig.TPGainRateRace[race]) * release);
									float mult = tartp / atttp;
									mult = (mult > 2.0F) ? 2.0F : mult;
									tp = (int) (tp + atttp * mult);
								} else if (targetEntity instanceof EntityNPCshadow) {

									float atttp = PlyrAttrbts[4] / JRMCoreConfig.TpgnRt * JRMCoreConfig.TPGainRateRace[race];
									float mult = (float) (atttp / atttp * release * 0.009999999776482582D);
									mult = (mult > 2.0F) ? 2.0F : mult;
									tp = (int) (tp + atttp * mult);
								} else {

									float atttp = (float) ((PlyrAttrbts[4] / JRMCoreConfig.TpgnRt * JRMCoreConfig.TPGainRateRace[race]) * release * 0.009999999776482582D);
									tp = (int) (tp + atttp);
								}
								JRMCoreH.jrmcExp(attacker, tp);
							}
						}
						if (source.damageType.equalsIgnoreCase("player")) { // plays punch sound
							int id = (int) (Math.random() * 3.0D) + 1;
							attacker.worldObj.playSoundAtEntity(attacker, "jinryuudragonbc:DBC4.punch" + id, 0.5F, 0.9F / (attacker.worldObj.rand.nextFloat() * 0.4F + 0.8F));
						}

						JRMCoreH.setInt(dealt, attacker, "jrmcLastDamageDealt");
						if (targetEntity != null && targetEntity instanceof EntityPlayer) {// if attacked is player, sets the jrmclastattacker && lastdamagereceieved for attacked
							JRMCoreH.setInt(dealt, (EntityPlayer) targetEntity, "jrmcLastDamageReceived");
							JRMCoreH.setString(attacker.getCommandSenderName() + ";" + epoch, (EntityPlayer) targetEntity, "jrmcLastAttacker");
						} else if (targetEntity != null) {// if not player, sets the jrmclastattacker and lastdamagereceieved for attacked entity
							JRMCoreH.nbt(targetEntity).setInteger("jrmcLastDamageReceived", dealt);
							JRMCoreH.nbt(targetEntity).setString("jrmcLastAttacker", attacker.getCommandSenderName() + ";" + epoch);

						}

						return;
					}

				}
			}
			// if attacker is not player && attacked is player
			if (!(source.getEntity() instanceof EntityPlayer) && source != DamageSource.outOfWorld && targetEntity instanceof EntityPlayer) {
				EntityPlayer targetPlayer = (EntityPlayer) targetEntity;
				Entity attacker = source.getEntity();

				if (targetEntity != null) {
					int acc = JRMCoreH.getByte(targetPlayer, "jrmcAccept");
					if (acc == 0 || JRMCoreH.getByte(targetPlayer, "jrmcPwrtyp") == 0) {

						event.setCanceled(false);

						return;
					}
					int currentBody = JRMCoreH.getInt(targetPlayer, "jrmcBdy");

					if ((this.dbc || this.nc || this.saoc)) {

						event.ammount = 0.0F;
						boolean doAttack = true;
						if (this.dbc && JRMCoreConfig.sfzns && JRMCoreHDBC.JRMCoreEHonLivingHurtSafeZone(targetEntity)) {
							event.ammount = 0.0F;
							event.setCanceled(true);
							return;
						}

						if (doAttack) {

							amount = damageEntity(targetPlayer, source, amount);
							int dealt = 0;
							JRMCoreH.a1t3(targetPlayer);
							if (attacker instanceof EntityNPCshadow) { // if source is shadow dummy, despawn dummy if attacked out of hp
								if (currentBody > amount) {
									dealt = JRMCoreH.jrmcDam(targetPlayer, (int) amount, source);
									// System.out.println("shadow dummy hit " + amount);
								} else {
									attacker.setDead();
									// System.out.println("despawn dummy");
								}
							} else if (source.getDamageType().equals("EnergyAttack") && source.getSourceOfDamage() instanceof EntityEnergyAtt) { // if source is EnergyAtt, finally calculate damage
								dealt = JRMCoreH.jrmcDam(targetPlayer, (int) amount, source, ((EntityEnergyAtt) source.getSourceOfDamage()).getType());
								// System.out.println("EnergyAtt hit " + amount);
							} else { // if source is none of the above, and not player
								dealt = JRMCoreH.jrmcDam(targetPlayer, (int) amount, source);
								// System.out.println("im him " + source.getDamageType());
							}
							System.out.println("dealt " + dealt);
							// System.out.println("amount after deal " + amount);
							int epoch = (int) (System.currentTimeMillis() / 1000L);
							if (attacker != null) {
								knockback(targetEntity, attacker, 1);
								JRMCoreH.setString(attacker.getCommandSenderName() + ";" + epoch, (EntityPlayer) targetEntity, "jrmcLastAttacker");
								JRMCoreH.setInt(dealt, (EntityPlayer) targetEntity, "jrmcLastDamageReceived");
								JRMCoreH.nbt(attacker).setInteger("jrmcLastDamageDealt", dealt);
							}

							return;
						}
					}

				}
			}
		}
	}

	@Overwrite
	protected void knockback(EntityLivingBase targetEntity, Entity attacker, int knockbackStrength) {
		if (knockbackStrength > 0) {

			float var25 = MathHelper.sqrt_double(attacker.motionX * attacker.motionX + attacker.motionZ * attacker.motionZ);

			if (var25 > 0.0F) {
				targetEntity.addVelocity(attacker.motionX * knockbackStrength * 0.6000000238418579D / var25, 0.1D, attacker.motionZ * knockbackStrength * 0.6000000238418579D / var25);
			}
		}
	}

	@Overwrite
	protected float damageEntity(EntityPlayer targetPlayer, DamageSource source, float amount) {
		if (!targetPlayer.isEntityInvulnerable()) {

			if (amount <= 0.0F)
				return 0.0F;
			if (!source.isUnblockable() && targetPlayer.isBlocking() && amount > 0.0F) {
				amount = (1.0F + amount) * 0.5F;
			}

			amount = ApplyArmor(targetPlayer, targetPlayer.inventory.armorInventory, source, amount);
			if (amount <= 0.0F)
				return 0.0F;

			if (amount != 0.0F) {
				targetPlayer.addExhaustion(source.getHungerDamage());
			}
		}
		return amount;
	}

	@Overwrite
	private float ApplyArmor(EntityLivingBase entity, ItemStack[] inventory, DamageSource source, double damage) {
		int armorNum = 0;
		int armorVal = 0;
		for (ItemStack stack : inventory) {

			if (stack != null) {
				armorNum++;
			}
		}

		if (entity instanceof EntityPlayer) {
			for (int i = 0; i < 1; i++) {

				ItemStack ws = (ExtendedPlayer.get((EntityPlayer) entity)).inventory.getStackInSlot(0);
				if (ws != null) {

					if (ws != null) {

						armorNum++;

						float armorMax = 5.0F;
						int itemProtect = (int) (damage * (armorMax / (armorMax + 25.0F)));
						itemProtect = (int) ((damage < 30.0D) ? itemProtect : armorMax);
						int itemDamage = (damage > 10000.0D) ? 3 : ((damage > 5000.0D) ? 2 : 1);
						itemDamage = (itemDamage < 1) ? 1 : itemDamage;
						armorVal += itemProtect;
						ws.damageItem(itemDamage, entity);
					}
					if (ws.stackSize <= 0) {
						(ExtendedPlayer.get((EntityPlayer) entity)).inventory.setInventorySlotContents(0, null);
					}
				}
			}
		}
		if (armorNum == 0)
			return (float) damage;
		for (int i = 0; i < 4; i++) {

			ItemStack stack = inventory[i];
			if (stack != null) {

				if (stack != null) {
					if (stack.getItem() instanceof ItemArmor && !source.isUnblockable()) {

						ItemArmor armor = (ItemArmor) stack.getItem();
						float armorMax = armor.damageReduceAmount;

						int itemProtect = (int) (damage * (armorMax / (armorMax + 25.0F)));
						itemProtect = (int) ((damage < 30.0D) ? itemProtect : armorMax);
						int itemDamage = (damage > 10000.0D) ? 3 : ((damage > 5000.0D) ? 2 : 1);
						itemDamage = (itemDamage < 1) ? 1 : itemDamage;
						armorVal += itemProtect;
						stack.damageItem(itemDamage, entity);
					}
				}
				if (stack.stackSize <= 0) {
					inventory[i] = null;
				}
			}
		}
		return (float) (damage - armorVal);
	}

	@Overwrite
	protected void damageEntity(EntityLivingBase targetEntity, DamageSource source, float amount) { // responsible for all nonplayer entity damagae
		if (!targetEntity.isEntityInvulnerable()) {

			if (amount <= 0.0F)
				return;

			if (amount != 0.0F) {

				JRMCoreH.jrmctAll(4, targetEntity.getEntityId() + ";take;" + amount);
				//System.out.println("damgedEntity nonplayer " + targetEntity + " amount " + amount);

				float dmgred = JRMCoreH.nbt(targetEntity).getFloat("dmgred");
				amount = amount * dmgred / 100F;
				// System.out.println("new amount " + amount);
				float f2 = targetEntity.getHealth();
				targetEntity.setHealth(f2 - amount);
				targetEntity.func_110142_aN().func_94547_a(source, f2, amount);
				targetEntity.setAbsorptionAmount(targetEntity.getAbsorptionAmount() - amount);
			}
		}
	}

}
