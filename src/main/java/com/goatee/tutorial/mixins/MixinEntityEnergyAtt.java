//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.goatee.tutorial.mixins;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import JinRyuu.DragonBC.common.DBCClientTickHandler;
import JinRyuu.DragonBC.common.DBCConfig;
import JinRyuu.DragonBC.common.Npcs.EntityDBC;
import JinRyuu.JRMCore.Ds;
import JinRyuu.JRMCore.JRMCoreConfig;
import JinRyuu.JRMCore.JRMCoreH;
import JinRyuu.JRMCore.JRMCoreHDBC;
import JinRyuu.JRMCore.client.config.jrmc.JGConfigClientSettings;
import JinRyuu.JRMCore.entity.EntityCusPar;
import JinRyuu.JRMCore.entity.EntityEnAttacks;
import JinRyuu.JRMCore.entity.EntityEnergyAtt;
import JinRyuu.JRMCore.entity.EntityEnergyAttJ3;
import JinRyuu.JRMCore.i.ExtendedPlayer;
import JinRyuu.JRMCore.server.JGMathHelper;
import JinRyuu.JRMCore.server.JGPlayerClientServerHelper;
import JinRyuu.JRMCore.server.config.dbc.JGConfigDBCGoD;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.common.registry.IThrowableEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@Mixin(value = EntityEnergyAtt.class, remap = false)

public abstract class MixinEntityEnergyAtt extends Entity implements IThrowableEntity, IEntityAdditionalSpawnData, IEntitySelector, IProjectile {
	
	public MixinEntityEnergyAtt(World p_i1582_1_, String yo, String no) {
		super(p_i1582_1_);
	}

	public Entity shootingEntity;
	@Shadow
	private int xTile;
	@Shadow
	private int yTile;
	@Shadow
	private int zTile;
	@Shadow
	private Block inTile;
	@Shadow
	private int inData;
	@Shadow
	private boolean inGround;
	@Shadow
	private int ticksInGround;
	@Shadow
	private int ticksInAir;
	@Shadow
	private double damage;
	@Shadow
	private double damageOriginal;
	@Shadow
	private int damageTaken;
	@Shadow
	private int knockbackStrength;
	@Shadow
	private float explevel;
	@Shadow
	private String ExplSound;
	@Shadow
	private float strtX;
	@Shadow
	private float strtY;
	@Shadow
	private float strtZ;
	@Shadow
	private float trgtX;
	@Shadow
	private float trgtY;
	@Shadow
	private float trgtZ;
	@Shadow
	private byte type;
	@Shadow
	private byte speed;
	@Shadow
	private int dam;
	@Shadow
	private byte perc;
	@Shadow
	private short effect;
	@Shadow
	private int color;
	@Shadow
	private int color2;
	@Shadow
	private byte density;
	@Shadow
	private short sincantation;
	@Shadow
	private short sfire;
	@Shadow
	private short smove;
	@Shadow
	private byte[] sts;
	@Shadow
	private byte technum;
	@Shadow
	private byte align;
	@Shadow
	private float size;
	@Shadow
	private int waveCount;
	@Shadow
	private byte wave;
	@Shadow
	private Entity target;
	@Shadow
	private int cost;
	@Shadow
	private int originDmg;
	@Shadow
	private boolean shrink;
	@Shadow
	private byte relFired;
	@Shadow
	public boolean givenExp;
	@Shadow
	public double motionXStart;
	@Shadow
	public double motionYStart;
	@Shadow
	public double motionZStart;
	@Shadow
	public boolean givenTP;

	public boolean destroyer;

	public float DAMAGE_REDUCTION;
	@Shadow
	public boolean added;
	@Shadow
	public int animation_speed;
	@Shadow
	public long animation_start;
	@Shadow
	public int animation_id;
	@Shadow
	public int animation_id_Max;
	@Shadow
	public int animation_random_Max;
	@Shadow
	public ArrayList<Integer> animation_random;
	@Shadow
	public float render_scale;
	@Shadow
	public float render_scale_max;
	@Shadow
	public double dist;
	@Shadow
	public boolean shooterHolds;
	@Shadow
	public boolean hadTarget;
	@Shadow
	public boolean added2;
	@Shadow
	public long animation_start2;
	@Shadow
	public float waveScale;
	@Shadow
	public double finalDist;
	@Shadow
	public int lastSegments;
	@Shadow
	public float minScale;
	@Shadow
	public float maxScale;
	@Shadow
	public float maxDamage;
	@Shadow
	private boolean run;
	@Shadow
	private List<Entity> kiClashedList;
	@Shadow
	public float startRotationPitch;
	@Shadow
	public float startRotationYaw;

	@Shadow
	public float strtX() {
		return this.strtX;
	}

	@Shadow
	public float strtY() {
		return this.strtY;
	}

	@Shadow
	public float strtZ() {
		return this.strtZ;
	}

	@Shadow
	public float trgtX() {
		return this.trgtX;
	}

	@Shadow
	public float trgtY() {
		return this.trgtY;
	}

	@Shadow
	public float trgtZ() {
		return this.trgtZ;
	}

	@Shadow
	public int getShrink() {
		return this.getDataWatcher().getWatchableObjectInt(20);
	}

	@Shadow
	public byte getType() {
		return this.type;
	}

	@Shadow
	public int getCol() {
		return this.color;
	}

	@Shadow
	public int getCol2() {
		return this.color2;
	}

	@Shadow
	public byte getSpe() {
		return this.speed;
	}

	@Shadow
	public int getDam() {
		return this.dam;
	}

	@Shadow
	public byte getDen() {
		return this.density;
	}

	@Shadow
	public byte getPerc() {
		return this.perc;
	}

	@Shadow
	public int getAirTicks() {
		return this.ticksInAir;
	}

	@Shadow
	public byte[] getSts() {
		return this.sts;
	}

	@Shadow
	public void setAirTicks(int i) {
		this.ticksInAir = i;
	}

	@Shadow
	public short getEff() {
		return this.effect;
	}

	@Shadow
	public float getSize() {
		return this.size;
	}

	@Shadow
	public float getScale() {
		float damage = (float) this.originDmg;
		byte perc = this.getPerc();
		byte[] sts = this.getSts();
		byte den = this.getDen();
		float scale = JRMCoreH.calculateEnergyScale(damage, this.maxDamage, (float) perc, sts, den, this.minScale, this.maxScale);
		return scale;
	}

	@Shadow
	public void setScales() {
		this.minScale = (float) JRMCoreConfig.KiSizeMin[this.getType()];
		this.maxScale = (float) JRMCoreConfig.KiSizeMax[this.getType()];
		this.maxDamage = JRMCoreH.getMaxEnergyDamage();
	}

	@Shadow
	public float setScalesPost() {
		if (this.isWave()) {
			return 100.0F;
		} else if (this.isBlast()) {
			return 5.0F;
		} else if (this.isDisk()) {
			return 5.0F;
		} else if (this.isLaser()) {
			return 5.0F;
		} else if (this.isLargeBlast()) {
			return 10000.0F;
		} else if (this.isSpiral()) {
			return 5.0F;
		} else if (this.isBarrage()) {
			return 5.0F;
		} else if (this.isShield()) {
			return 5.0F;
		} else {
			return this.isExplosion() ? 20.0F : 1.0F;
		}
	}

	protected void entityInit() {
		this.dataWatcher.addObject(20, 0);
	}

	@Shadow
	public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8) {
		float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
		par1 /= (double) var9;
		par3 /= (double) var9;
		par5 /= (double) var9;
		par1 += this.rand.nextGaussian() * 0.007499999832361937 * (double) par8;
		par3 += this.rand.nextGaussian() * 0.007499999832361937 * (double) par8;
		par5 += this.rand.nextGaussian() * 0.007499999832361937 * (double) par8;
		par1 *= (double) par7;
		par3 *= (double) par7;
		par5 *= (double) par7;
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;
		float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(par1, par5) * 180.0 / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(par3, (double) var10) * 180.0 / Math.PI);
		this.ticksInGround = 0;
	}

	@SideOnly(Side.CLIENT)
	@Shadow
	public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
		this.setPosition(par1, par3, par5);
		this.setRotation(par7, par8);
	}

	@SideOnly(Side.CLIENT)
	@Shadow
	public void setVelocity(double par1, double par3, double par5) {
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(par1, par5) * 180.0 / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(par3, (double) var7) * 180.0 / Math.PI);
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
			this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			this.ticksInGround = 0;
		}

	}

	@Shadow
	public boolean isContinuesWave() {
		return this.getType() >= JRMCoreConfig.ContinuesKiAttacks.length ? false : JRMCoreConfig.ContinuesKiAttacks[this.getType()];
	}

	@Unique
	public boolean isControllerOfShooter(Entity target, Entity shooter) {
		if (shooter instanceof EntityPlayer && target instanceof EntityPlayer) {
			NBTTagCompound nbt = shooter.getEntityData().getCompoundTag("PlayerPersisted");
			if (nbt.getString("ControllerName").equals(target.getCommandSenderName())) {

				return true;
			}
		}

		return false;

	}

	@Overwrite
	public void onUpdate() {
		if ((JGConfigClientSettings.configsChanged || this.run) && this.worldObj.isRemote && !this.isDead) {
			this.ignoreFrustumCheck = JGConfigClientSettings.renderEnergyOutsideView;
		}

		int diff;
		if (!this.isShield() && !this.isExplosion()) {
			int color = JRMCoreH.techCol[this.getCol()];
			diff = this.getCol2() == -1 ? JRMCoreH.techCol2[this.getCol()] : JRMCoreH.techCol3[this.getCol2()];
			if (this.isContinuesWave() && this.shooterHolds) {
				this.generateParticles((EntityEnergyAtt) (Object) this, this.shootingEntity, color, diff, true);
			}

			this.generateParticles((EntityEnergyAtt) (Object) this, this, color, diff, false);
		}

		if (!this.run) {
			if (this.rotationPitch != this.startRotationPitch) {
				this.rotationPitch = this.startRotationPitch;
			}

			if (this.rotationYaw != this.startRotationYaw) {
				this.rotationYaw = this.startRotationYaw;
			}
		}

		if (this.run) {
			this.startRotationPitch = this.rotationPitch;
			this.startRotationYaw = this.rotationYaw;
			this.shooterHolds = this.isContinuesWave();
			if (this.shootingEntity != null && this.shootingEntity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) this.shootingEntity;
				ExtendedPlayer.get(player).setAnimKiShootOn(0);
			}

			if (!this.worldObj.isRemote && !JRMCoreConfig.dat5695[this.type] && !this.isDead) {
				this.setDead();
			}

			if (!this.worldObj.isRemote && !JRMCoreConfig.dat5709[this.type] && this.hasEffect()) {
				this.effect = 0;
			}

			this.run = false;
		}

		if (!this.worldObj.isRemote && this.isContinuesWave() && !this.isShield() && !this.isExplosion() && this.target != null && !this.target.isDead && !this.shooterHolds
				&& JGMathHelper.isMotionSmallerThanN(this, 0.001)) {
			this.setDead();
		}

		double y;
		double kiX;
		double kiY;
		double motX;
		double kiZ;
		double motY;
		double kulx;
		double motZ;
		double kuly;
		double kulz;
		float spet;
		double distance;
		double x;
		double z;
		if (JRMCoreConfig.WavesShrinkOnceLetGo) {
			if (this.shootingEntity != null && this.shootingEntity instanceof EntityPlayer && this.isContinuesWave() && this.shooterHolds) {
				x = (double) (((EntityLivingBase) this.shootingEntity).width + 1.0F + 1.0F);
				y = (double) (((EntityLivingBase) this.shootingEntity).height + 0.2F);
				Vec3 vec3 = this.shootingEntity.getLookVec();
				x = this.shootingEntity.posX + vec3.xCoord * x;
				y = this.shootingEntity.posY + vec3.yCoord * x + (double) (this.shootingEntity.height * 0.7F)
						+ (double) (this.worldObj.isRemote ? JGPlayerClientServerHelper.clientPlayerPositioner(this.shootingEntity) : 0.0F);
				motX = this.shootingEntity.posZ + vec3.zCoord * x;
				if (x < 0.0) {
					x *= -1.0;
				}

				if (y < 0.0) {
					y *= -1.0;
				}

				if (motX < 0.0) {
					motX *= -1.0;
				}

				motY = (double) this.strtX;
				if (motY < 0.0) {
					motY *= -1.0;
				}

				motZ = (double) this.strtY;
				if (motZ < 0.0) {
					motZ *= -1.0;
				}

				motY = (double) this.strtZ;
				if (motY < 0.0) {
					motY *= -1.0;
				}

				motZ = x - motY;
				if (motZ < 0.0) {
					motZ *= -1.0;
				}

				kuly = y - motZ;
				if (kuly < 0.0) {
					kuly *= -1.0;
				}

				distance = motX - motY;
				if (distance < 0.0) {
					distance *= -1.0;
				}

				if (motZ > 0.20000000298023224 || kuly > 1.0 || distance > 0.20000000298023224) {
					this.shooterHolds = false;
					EntityPlayer player = (EntityPlayer) this.shootingEntity;
					ExtendedPlayer.get(player).setAnimKiShoot(0);
					if (!this.worldObj.isRemote) {
						JRMCoreH.setByte(0, (EntityPlayer) this.shootingEntity, "jrmcFrng");
						this.shrinkWave();
					} else {
						DBCClientTickHandler.nuller();
						JRMCoreH.isShtng = false;
					}
				}
			}
		} else if (this.shootingEntity != null && this.shootingEntity instanceof EntityPlayer && this.isContinuesWave() && this.shooterHolds) {
			x = (double) this.strtX;
			y = (double) this.strtY;
			z = (double) this.strtZ;
			if (x < 0.0) {
				x *= -1.0;
			}

			if (y < 0.0) {
				y *= -1.0;
			}

			if (z < 0.0) {
				z *= -1.0;
			}

			kiX = this.shootingEntity.posX;
			if (kiX < 0.0) {
				kiX *= -1.0;
			}

			kiY = this.shootingEntity.posY + (double) (this.worldObj.isRemote ? JGPlayerClientServerHelper.clientPlayerPositioner(this.shootingEntity) : 0.0F);
			if (kiY < 0.0) {
				kiY *= -1.0;
			}

			kiZ = this.shootingEntity.posZ;
			if (kiZ < 0.0) {
				kiZ *= -1.0;
			}

			kulx = x - kiX;
			if (kulx < 0.0) {
				kulx *= -1.0;
			}

			kuly = y - kiY;
			if (kuly < 0.0) {
				kuly *= -1.0;
			}

			kulz = z - kiZ;
			if (kulz < 0.0) {
				kulz *= -1.0;
			}

			spet = 3.0F;
			if (kulx > 3.0 || kuly > 3.0 || kulz > 3.0) {
				this.shooterHolds = false;
				EntityPlayer player = (EntityPlayer) this.shootingEntity;
				ExtendedPlayer.get(player).setAnimKiShoot(0);
				if (!this.worldObj.isRemote) {
					JRMCoreH.setByte(0, (EntityPlayer) this.shootingEntity, "jrmcFrng");
				} else {
					DBCClientTickHandler.nuller();
					JRMCoreH.isShtng = false;
				}
			}
		}

		if (!this.worldObj.isRemote && this.isContinuesWave() && JRMCoreConfig.ContinuesEnergyAttackEnemyLock && this.target != null && !this.target.isDead && this.shooterHolds) {
			x = this.posX;
			y = this.posY;
			z = this.posZ;
			if (x < 0.0) {
				x *= -1.0;
			}

			if (y < 0.0) {
				y *= -1.0;
			}

			if (z < 0.0) {
				z *= -1.0;
			}

			kiX = this.target.posX;
			if (kiX < 0.0) {
				kiX *= -1.0;
			}

			kiY = this.target.posY;
			if (kiY < 0.0) {
				kiY *= -1.0;
			}

			kiZ = this.target.posZ;
			if (kiZ < 0.0) {
				kiZ *= -1.0;
			}

			kulx = x - kiX;
			if (kulx < 0.0) {
				kulx *= -1.0;
			}

			kuly = y - kiY;
			if (kuly < 0.0) {
				kuly *= -1.0;
			}

			kulz = z - kiZ;
			if (kulz < 0.0) {
				kulz *= -1.0;
			}

			spet = 0.5F;
			if (kulx > 0.5 || kuly > 0.5 || kulz > 0.5) {
				this.target.posX = x;
				this.target.posY = y;
				this.target.posZ = z;
				this.target.posX = this.posX;
				this.target.posY = this.posY;
				this.target.posZ = this.posZ;
				this.target.motionX = this.motionX;
				this.target.motionY = this.motionY;
				this.target.motionZ = this.motionZ;
			}

			if (JRMCoreH.DBC() && this.target instanceof EntityDBC) {
				((EntityDBC) this.target).lockedBy = this;
			}
		}

		if (!this.worldObj.isRemote && this.isContinuesWave() && JRMCoreConfig.ContinuesEnergyAttackTargetSlowdown && JRMCoreConfig.ContinuesEnergyAttackMoveOnLostTarget && this.target != null
				&& !this.target.isDead && this.shooterHolds && JGMathHelper.isMotionSmallerThanN(this, 0.001)) {
			x = this.posX;
			y = this.posY;
			z = this.posZ;
			if (x < 0.0) {
				x *= -1.0;
			}

			if (y < 0.0) {
				y *= -1.0;
			}

			if (z < 0.0) {
				z *= -1.0;
			}

			kiX = this.target.posX;
			if (kiX < 0.0) {
				kiX *= -1.0;
			}

			kiY = this.target.posY;
			if (kiY < 0.0) {
				kiY *= -1.0;
			}

			kiZ = this.target.posZ;
			if (kiZ < 0.0) {
				kiZ *= -1.0;
			}

			kulx = x - kiX;
			if (kulx < 0.0) {
				kulx *= -1.0;
			}

			kuly = y - kiY;
			if (kuly < 0.0) {
				kuly *= -1.0;
			}

			kulz = z - kiZ;
			if (kulz < 0.0) {
				kulz *= -1.0;
			}

			spet = this.size + 1.0F;
			if (kulx > (double) spet || kuly > (double) spet || kulz > (double) spet) {
				this.target = null;
				this.hadTarget = false;
				this.motionX = this.motionXStart;
				this.motionY = this.motionYStart;
				this.motionZ = this.motionZStart;
			}
		}

		if (!this.worldObj.isRemote && JRMCoreConfig.WavesDieWhenTargetAway && this.shootingEntity != null && this.target != null && this.shootingEntity instanceof EntityPlayer
				&& this.isContinuesWave()) {
			x = this.posX;
			y = this.posY;
			z = this.posZ;
			if (x < 0.0) {
				x *= -1.0;
			}

			if (y < 0.0) {
				y *= -1.0;
			}

			if (z < 0.0) {
				z *= -1.0;
			}

			kiX = this.target.posX;
			if (kiX < 0.0) {
				kiX *= -1.0;
			}

			kiY = this.target.posY;
			if (kiY < 0.0) {
				kiY *= -1.0;
			}

			kiZ = this.target.posZ;
			if (kiZ < 0.0) {
				kiZ *= -1.0;
			}

			kulx = x - kiX;
			if (kulx < 0.0) {
				kulx *= -1.0;
			}

			kuly = y - kiY;
			if (kuly < 0.0) {
				kuly *= -1.0;
			}

			kulz = z - kiZ;
			if (kulz < 0.0) {
				kulz *= -1.0;
			}

			spet = this.size + 1.0F;
			if (kulx > (double) spet || kuly > (double) spet || kulz > (double) spet) {
				if (this.hasEffect() && !this.isShield() && !this.isExplosion()) {
					this.createExplosion(2);
				}

				this.setDead();
			}
		}

		if (this.shootingEntity != null && this.isExplosion() && this.hasEffect()) {
			x = this.shootingEntity.posX - this.posX;
			if (x < 0.0) {
				x *= -1.0;
			}

			y = this.shootingEntity.posY - this.posY;
			if (y < 0.0) {
				y *= -1.0;
			}

			z = this.shootingEntity.posZ - this.posZ;
			if (z < 0.0) {
				z *= -1.0;
			}

			if (x > 1.0 || y > 1.0 || z > 1.0) {
				this.shootingEntity.setPosition(this.posX, this.posY + 1.0, this.posZ);
			}
		}

		if (!this.worldObj.isRemote && this.shootingEntity == null) {
			this.setDead();
		}

		if (!this.worldObj.isRemote && this.shootingEntity != null && !this.isDead) {
			if (this.isShield()) {
				if (JRMCoreConfig.ShieldsMoveWithUser) {
					this.posX = this.shootingEntity.posX;
					this.posY = this.shootingEntity.posY + (double) (this.shootingEntity.height * 0.55F);
					this.posZ = this.shootingEntity.posZ;
				} else {
					diff = (int) (this.posX - this.shootingEntity.posX);
					diff *= diff > 0 ? 1 : -1;
					if (diff > 3) {
						this.setDead();
					}

					diff = (int) (this.posY - this.shootingEntity.posY);
					diff *= diff > 0 ? 1 : -1;
					if (diff > 3) {
						this.setDead();
					}

					diff = (int) (this.posZ - this.shootingEntity.posZ);
					diff *= diff > 0 ? 1 : -1;
					if (diff > 3) {
						this.setDead();
					}
				}
			} else if (this.isExplosion() && JRMCoreConfig.ExplosionsMoveWithUser) {
				this.posX = this.shootingEntity.posX;
				this.posY = this.shootingEntity.posY + (double) (this.shootingEntity.height * 0.55F);
				this.posZ = this.shootingEntity.posZ;
			}
		}

		if (!this.worldObj.isRemote && this.shootingEntity != null && JRMCoreConfig.dat5710 && (this.isShield() || this.isExplosion())) {
			diff = Integer.parseInt(JRMCoreH.data(this.shootingEntity.getCommandSenderName(), 8, "200"));
			if (diff == 0) {
				this.setDead();
			}
		}

		if (!this.worldObj.isRemote && this.isContinuesWave() && this.shootingEntity != null && this.shootingEntity instanceof EntityPlayer) {
			byte b = JRMCoreH.getByte((EntityPlayer) this.shootingEntity, "jrmcFrng");
			if (b == 0 && !this.shrink && JRMCoreConfig.WavesShrinkOnceLetGo) {
				this.shrink();
			}
		}

		if (!this.worldObj.isRemote && this.isContinuesWave() && this.hadTarget && (this.target == null || this.target.isDead)) {
			this.setDead();
		}

		if (!this.worldObj.isRemote && this.isExplosion() && this.ticksExisted >= JRMCoreConfig.dat5697) {
			this.setDead();
		}

		Entity var10000;
		if (this.isContinuesWave() && this.target != null) {
			if (JRMCoreConfig.ContinuesEnergyAttackTargetSlowdown) {
				this.motionX *= 0.05000000074505806;
				this.motionY *= 0.05000000074505806;
				this.motionZ *= 0.05000000074505806;
				var10000 = this.target;
				var10000.motionX *= 0.05000000074505806;
				var10000 = this.target;
				var10000.motionY *= 0.05000000074505806;
				var10000 = this.target;
				var10000.motionZ *= 0.05000000074505806;
			} else {
				this.target.motionX = this.motionX;
				this.target.motionY = this.motionY;
				this.target.motionZ = this.motionZ;
				this.target.posX = this.posX;
				this.target.posY = this.posY;
				this.target.posZ = this.posZ;
			}
		}

		if (this.ticksExisted == 1) {
			this.setSize(this.size, this.size);
			this.yOffset = this.size * 0.5F;
		}

		super.onUpdate();
		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
			float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, (double) var1) * 180.0 / Math.PI);
		}

		Block block = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
		if (block.getMaterial() != Material.air) {
			block.setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
			AxisAlignedBB axisalignedbb = block.getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);
			if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ))) {
				this.inGround = true;
			}
		}

		if (this.inGround && !this.isShield() && !this.isExplosion()) {
			int var19 = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
			if (!this.worldObj.isRemote) {
				if (block == this.inTile && var19 == this.inData) {
					++this.ticksInGround;
					if (this.ticksInGround == 1) {
						this.setDead();
						if (!this.worldObj.isRemote) {
							if (this.hasEffect()) {
								this.createExplosion(1);
							}

							this.playSoundAtEntity(this,
									JGConfigDBCGoD.CONFIG_GOD_ENABLED && JGConfigDBCGoD.CONFIG_GOD_ENERGY_ENABLED && this.destroyer ? "jinryuudragonbc:DBC5.hakai" : this.ExplSound, 1.0F, 1.0F);
						}
					}
				} else {
					this.inGround = false;
					this.motionX *= (double) (this.rand.nextFloat() * 0.2F);
					this.motionY *= (double) (this.rand.nextFloat() * 0.2F);
					this.motionZ *= (double) (this.rand.nextFloat() * 0.2F);
					this.ticksInGround = 0;
					this.ticksInAir = 0;
				}
			}
		} else {
			++this.ticksInAir;
			Vec3 var17 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			Vec3 var3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			MovingObjectPosition movingObject = this.worldObj.func_147447_a(var17, var3, false, true, false);
			var17 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
			var3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			if (!this.worldObj.isRemote
					&& ((float) this.ticksInAir >= (float) JRMCoreConfig.EnergyAttackMaxLifeTickPercMulti * (float) this.perc * 0.02F || this.ticksInAir >= JRMCoreConfig.EnergyAttackMaxLifeTick)) {
				this.setDead();
			}

			int t = this.ticksInAir / 10 * 10;
			if (this.ticksInAir == (t == 0 ? 10 : t)) {
				this.playSoundAtEntity(this, "jinryuudragonbc:" + JRMCoreH.techSnds(this.type, 2, this.smove), this.isBarrage() ? 0.5F : 1.0F, 1.0F);
			}

			if (movingObject != null) {
				var3 = Vec3.createVectorHelper(movingObject.hitVec.xCoord, movingObject.hitVec.yCoord, movingObject.hitVec.zCoord);
			}

			float var11;
			byte result;
			boolean doit;
			if (!this.worldObj.isRemote) {
				Entity lastEntity = null;
				doit = true;
				result = JRMCoreConfig.KiClosestEntityCheckSize;
				AxisAlignedBB aabb;
				List<?> entityList;
				if (result == 4) {
					aabb = this.boundingBox.copy();
					entityList = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, aabb);
				} else if (result == 3) {
					aabb = this.boundingBox.copy();
					entityList = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, aabb.addCoord(this.motionX, this.motionY, this.motionZ));
				} else if (result == 2) {
					aabb = this.boundingBox.copy();
					entityList = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, aabb.expand(0.5, 0.5, 0.5));
				} else if (result == 1) {
					entityList = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ));
				} else {
					entityList = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(0.5, 0.5, 0.5));
				}

				if (result != 5) {
					motY = 0.0;
					int n = 0;

					while (true) {
						if (n >= entityList.size()) {
							if (lastEntity != null) {
								movingObject = new MovingObjectPosition(lastEntity);
							}
							break;
						}

						Entity entity = (Entity) entityList.get(n);
						if (entity instanceof EntityLivingBase && entity.canBeCollidedWith() && (entity != this.shootingEntity || this.ticksInAir >= 5)) {
							var11 = 0.0F;
							AxisAlignedBB entityHitbox = entity.boundingBox.expand((double) var11, (double) var11, (double) var11);
							MovingObjectPosition movingObject2 = entityHitbox.calculateIntercept(var17, var3);
							if (movingObject2 != null) {
								distance = var17.distanceTo(movingObject2.hitVec);
								if (distance < motY || motY == 0.0) {
									lastEntity = entity;
									motY = distance;
								}
							}
						}

						++n;
					}
				}
			}

			if (this.motionX <= 0.01 && this.motionY <= 0.01 && this.motionZ <= 0.01 && this.motionX >= -0.01 && this.motionY >= -0.01 && this.motionZ >= -0.01 && !this.shrink
					&& !this.isContinuesWave()) {
				this.shrink();
			}

			int var23;
			int curEn;
			float spe;
			if (!this.worldObj.isRemote) {
				List<?> entityList = this.checkForEntitiesInside();
				var23 = 0;

				label1188: while (true) {
					Entity entity;
					EntityEnergyAtt energyEntity;
					if (var23 >= entityList.size()) {
						var23 = 0;

						while (true) {
							if (var23 >= entityList.size()) {
								break label1188;
							}

							label1432: {
								entity = (Entity) entityList.get(var23);
								float dent;
								if (entity != this.shootingEntity) {
									curEn = this.checkReaction(entity, true);
									EntityEnergyAtt entityKi = null;
									if (entity != null) {
										if (entity instanceof EntityEnergyAtt) {
											entityKi = (EntityEnergyAtt) entity;
										}

										if (entity instanceof EntityLivingBase) {
											if (movingObject == null) {
												movingObject = new MovingObjectPosition(entity);
											}

											if (this.target == null && !JRMCoreH.isFusionSpectator(entity) && !isControllerOfShooter(entity, shootingEntity)) {
												this.setTarget(entity);
											}
										} else if (this.isContinuesWave() && this.wave > 0 && this.shootingEntity instanceof EntityPlayer) {
											this.shrinkWave();
										} else if (!(entity instanceof EntityEnAttacks) && entity != this.shootingEntity && !this.isShield() && !this.isExplosion()) {
											if (this.hasEffect() && (curEn <= 0 || curEn >= 6) && entity instanceof EntityLivingBase) {
												this.createExplosion(2);
											}
										} else if (entityKi != null && !(entityKi.shootingEntity instanceof EntityDBC) && !this.kiClashedList.contains(entity)) {
											if (!this.isShield() && !this.isExplosion()) {
												doit = true;
												result = 0;
												if (JGConfigDBCGoD.CONFIG_GOD_ENABLED && JGConfigDBCGoD.CONFIG_GOD_ENERGY_ENABLED && this.destroyer
														&& this.damage * (double) this.DAMAGE_REDUCTION / 2.0 > entityKi.getDamage() / 2.0) {
													entityKi.setDead();
													break label1432;
												}

												if (JRMCoreConfig.dat5705 != 1.0 && !entityKi.isShield() && !entityKi.isExplosion() && !this.isDead && !entityKi.isDead) {
													result = this.killWeakerAttack((EntityEnergyAtt) (Object) this, entityKi);
												}

												if (result == 0) {
													if (entityKi != null && !entityKi.isDead && (entityKi.isShield() || entityKi.isExplosion())) {
														doit = false;
													}

													if (entityKi != null && !entityKi.isDead && !this.isDead) {
														this.kiClashedList.add(entity);
														if (doit && entityKi.shootingEntity != this.shootingEntity) {
															float dam = (float) (((EntityEnergyAtt) entity).getDamage() / 2.0);
															spe = (float) ((EntityEnergyAtt) entity).getSpe() * 2.0F;
															float den = (float) ((EntityEnergyAtt) entity).getDen() * 10.0F;
															float damt = (float) (this.damage / 2.0);
															spet = (float) this.speed * 2.0F;
															dent = (float) this.density * 10.0F;
															long power = this.getPower(entityKi);
															if (power >= 0L) {
																float res = ((damt - dam) / damt + (spe - spet) / spe + (dent - den) / dent) / 3.0F;
																this.motionX *= (double) res;
																this.motionY *= (double) res;
																this.motionZ *= (double) res;
																if (JGConfigDBCGoD.CONFIG_GOD_ENABLED && JGConfigDBCGoD.CONFIG_GOD_ENERGY_ENABLED && ((EntityEnergyAtt) entity).destroyer
																		&& !(((EntityEnergyAtt) entity).getDamage() * (double) this.DAMAGE_REDUCTION / 2.0 > this.damage / 2.0)) {
																	((EntityEnergyAtt) entity).motionX = this.motionX;
																	((EntityEnergyAtt) entity).motionY = this.motionY;
																	((EntityEnergyAtt) entity).motionZ = this.motionZ;
																}

																if (((EntityEnergyAtt) entity).getAirTicks() < this.ticksInAir) {
																	this.ticksInAir = ((EntityEnergyAtt) entity).getAirTicks();
																}
															}
														}
													}
												}
											} else if (this.isShield() && !this.worldObj.isRemote) {
												doit = true;
												if ((int) (Math.random() * 3.0) == 0) {
													doit = false;
												}

												if (doit && this.shootingEntity instanceof EntityPlayer) {
													boolean qckmth = !entityKi.isShield() && !entityKi.isExplosion();
													if (qckmth) {
														String s2 = JRMCoreH.getString((EntityPlayer) this.shootingEntity, JRMCoreH.techNbt[this.technum]);
														JRMCoreH.setString(JRMCoreH.tech_expgiv(s2, JRMCoreH.DBC() ? JRMCoreHDBC.DBCgetConfigTechExpRate() : 1), (EntityPlayer) this.shootingEntity,
																JRMCoreH.techNbt[this.technum]);
													}
												}
											}
										}
									}
								}

								if (this.isExplosion() && (this.hasEffect() || !entity.equals(this.shootingEntity))) {
									energyEntity = null;
									if (entity instanceof EntityEnergyAtt) {
										energyEntity = (EntityEnergyAtt) entity;
									}

									if (entity instanceof EntityLivingBase) {
										if (movingObject == null) {
											movingObject = new MovingObjectPosition(entity);
										}

										if (this.target == null && JRMCoreH.isFusionSpectator(movingObject == null ? null : movingObject.entityHit)
												&& !isControllerOfShooter(movingObject.entityHit, shootingEntity)) {
											System.out.println("asda");
											this.target = entity;
										}
									}

									if ((energyEntity == null || energyEntity.shootingEntity instanceof EntityDBC || this.kiClashedList.contains(entity)) && entity != null) {
										MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
										var23 = (int) (this.damage * (double) (this.hasEffect() ? 3.0F : 1.0F) * (entity.equals(this.shootingEntity) ? JRMCoreConfig.dat5701 / 100.0 : 1.0));
										this.giveExperience(entity, 1);
										DamageSource damagesource = null;
										if (this.shootingEntity == null || this.shootingEntity instanceof EntityDBC && entity instanceof EntityDBC) {
											this.setDead();
											return;
										}

										damagesource = Ds.causeEntityEnergyAttDamage(this, this.shootingEntity);
										if (this.isBurning()) {
											entity.setFire(5);
										}

										motZ = entity.motionX;
										motY = entity.motionY;
										motZ = entity.motionZ;
										if (!JRMCoreH.isFusionSpectator(movingObject == null ? null : movingObject.entityHit) && entity.attackEntityFrom(damagesource, (float) var23)) {
											if (this.type < 7 && JRMCoreConfig.dat5706[this.type]) {
												entity.motionX = motZ;
												entity.motionY = motY;
												entity.motionZ = motZ;
											}

											if (entity instanceof EntityLivingBase) {
												if (!this.worldObj.isRemote && this.shootingEntity instanceof EntityPlayer) {
													String s2 = JRMCoreH.getString((EntityPlayer) this.shootingEntity, JRMCoreH.techNbt[this.technum]);
													JRMCoreH.setString(JRMCoreH.tech_expgiv(s2, JRMCoreH.DBC() ? JRMCoreHDBC.DBCgetConfigTechExpRate() : 1), (EntityPlayer) this.shootingEntity,
															JRMCoreH.techNbt[this.technum]);
												}

												if (this.knockbackStrength > 0 && !entity.equals(this.shootingEntity) && (this.type >= 7 || JRMCoreConfig.dat5706[this.type])) {
													dent = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
													if (dent > 0.0F) {
														entity.addVelocity(this.motionX * (double) this.knockbackStrength * 0.6000000238418579 / (double) dent, 0.1,
																this.motionZ * (double) this.knockbackStrength * 0.6000000238418579 / (double) dent);
													}
												}
											}
										}
									}
								}
							}

							++var23;
						}
					}

					entity = (Entity) entityList.get(var23);
					if (entity != null && entity != this.shootingEntity && !entity.isDead) {
						if (entity instanceof EntityEnergyAtt) {
							energyEntity = (EntityEnergyAtt) entity;
							if (energyEntity.isShield() || energyEntity.isExplosion()) {
								this.handleKiaiClash(energyEntity);
							}
						} else if (JRMCoreH.NC() && entity instanceof EntityEnergyAttJ3) {
							EntityEnergyAttJ3 entityShield = (EntityEnergyAttJ3) entity;
							this.handleJutsuWallClash(entityShield);
						}
					}

					++var23;
				}
			}

			if (!this.isShield()) {
				if (movingObject != null && movingObject.entityHit != this.shootingEntity) {
					if (!this.worldObj.isRemote && !this.isExplosion() && !JRMCoreH.isFusionSpectator(movingObject == null ? null : movingObject.entityHit)
							&& !isControllerOfShooter(movingObject.entityHit, shootingEntity) && this.canSpiralNotGoThrough()) {
						this.playSoundAtEntity(this, JGConfigDBCGoD.CONFIG_GOD_ENABLED && JGConfigDBCGoD.CONFIG_GOD_ENERGY_ENABLED && this.destroyer ? "jinryuudragonbc:DBC5.hakai" : this.ExplSound,
								1.0F, 1.0F);
					}

					if (movingObject.entityHit != null && (this.shootingEntity instanceof EntityPlayer || movingObject.entityHit instanceof EntityPlayer) && this.isContinuesWave()
							&& this.shooterHolds) {
						if (this.shootingEntity instanceof EntityPlayer) {
							this.trgtX = (float) this.posX;
							this.trgtY = (float) this.posY;
							this.trgtZ = (float) this.posZ;
							byte b = JRMCoreH.getByte((EntityPlayer) this.shootingEntity, "jrmcFrng");
							if (b == 1) {
								if (this.target != null) {
									if (this.waveCount == 20) {
										++this.wave;
										if (JRMCoreConfig.ContinuesEnergyAttackTimer == 0 && this.wave > 2) {
											this.wave = 2;
										}

										if (!this.worldObj.isRemote) {
											EntityPlayer Player = (EntityPlayer) this.shootingEntity;
											byte curRel = JRMCoreH.getByte(Player, "jrmcRelease");
											curEn = JRMCoreH.getInt(Player, "jrmcEnrgy");
											float cost2 = (float) ((double) this.cost * (double) curRel * 0.009999999776482582 * (double) ((float) this.perc * 0.02F)
													* JRMCoreConfig.dat5696[this.type][2]);
											if (!((float) curEn - cost2 > 0.0F)) {
												this.setDead();
											}

											if (cost2 < (float) curEn) {
												if (!JRMCoreH.isInCreativeMode(this.shootingEntity)) {
													JRMCoreH.setInt((float) curEn - cost2, Player, "jrmcEnrgy");
												}

												this.damage = (double) this.originDmg * (double) curRel * 0.009999999776482582 * (double) this.perc * 0.019999999552965164
														* JRMCoreConfig.dat5696[this.type][1];
											} else {
												this.setDead();
											}
										}

										if (this.wave == 1 && movingObject.entityHit instanceof EntityLivingBase && !this.worldObj.isRemote) {
											if (this.shootingEntity instanceof EntityPlayer) {
												String s2 = JRMCoreH.getString((EntityPlayer) this.shootingEntity, JRMCoreH.techNbt[this.technum]);
												JRMCoreH.setString(JRMCoreH.tech_expgiv(s2, JRMCoreH.DBC() ? JRMCoreHDBC.DBCgetConfigTechExpRate() : 1), (EntityPlayer) this.shootingEntity,
														JRMCoreH.techNbt[this.technum]);
											}
										}

										var23 = (int) this.damage;
										DamageSource damagesource = Ds.causeEntityEnergyAttDamage(this, this.shootingEntity);
										if (!JRMCoreH.isFusionSpectator(movingObject == null ? null : movingObject.entityHit) && !isControllerOfShooter(movingObject.entityHit, shootingEntity)
												&& this.target.attackEntityFrom(damagesource, (float) var23)) {
											this.weakenSpiral();
										}

										if (JRMCoreConfig.ContinuesEnergyAttackTargetSlowdown) {
											this.motionX *= 0.05000000074505806;
											this.motionY *= 0.05000000074505806;
											this.motionZ *= 0.05000000074505806;
											var10000 = this.target;
											var10000.motionX *= 0.05000000074505806;
											var10000 = this.target;
											var10000.motionY *= 0.05000000074505806;
											var10000 = this.target;
											var10000.motionZ *= 0.05000000074505806;
										} else {
											this.target.motionX = this.motionX;
											this.target.motionY = this.motionY;
											this.target.motionZ = this.motionZ;
											this.target.posX = this.posX;
											this.target.posY = this.posY;
											this.target.posZ = this.posZ;
										}
									}

									this.target.motionX = this.motionX;
									this.target.motionY = this.motionY;
									this.target.motionZ = this.motionZ;
								}

								--this.waveCount;
								if (this.waveCount <= 0) {
									this.waveCount = 20;
								}

								if (JRMCoreConfig.ContinuesEnergyAttackTimer > 0 && this.wave >= JRMCoreConfig.ContinuesEnergyAttackTimer) {
									if (this.hasEffect() && !this.isShield() && !this.isExplosion()) {
										this.createExplosion(2);
									}

									this.setDead();
								}
							} else {
								this.shrinkWave();
							}
						}

						if (movingObject.entityHit instanceof EntityLivingBase) {
							if (!JRMCoreH.isFusionSpectator(movingObject == null ? null : movingObject.entityHit) && !isControllerOfShooter(movingObject.entityHit, shootingEntity)) {
								this.setTarget(movingObject.entityHit);
							}
						} else {
							this.shrinkWave();
						}
					} else {
						var23 = 0;
						DamageSource damagesource;
						if (movingObject.entityHit != null && (this.shootingEntity instanceof EntityPlayer || movingObject.entityHit instanceof EntityPlayer)) {
							MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
							var23 = (int) this.damage;
							this.giveExperience(movingObject.entityHit, 1);
							damagesource = null;
							if (this.shootingEntity == null || this.shootingEntity instanceof EntityDBC && movingObject.entityHit instanceof EntityDBC) {
								this.setDead();
								return;
							}

							damagesource = Ds.causeEntityEnergyAttDamage(this, this.shootingEntity);
							if (this.isBurning()) {
								movingObject.entityHit.setFire(5);
							}

							motX = movingObject.entityHit.motionX;
							motY = movingObject.entityHit.motionY;
							motZ = movingObject.entityHit.motionZ;
							if (!JRMCoreH.isFusionSpectator(movingObject == null ? null : movingObject.entityHit) && !isControllerOfShooter(movingObject.entityHit, shootingEntity)) {
								if (movingObject.entityHit.attackEntityFrom(damagesource, (float) var23)) {
									this.weakenSpiral();
									if (this.type < 7 && !JRMCoreConfig.dat5706[this.type]) {
										movingObject.entityHit.motionX = motX;
										movingObject.entityHit.motionY = motY;
										movingObject.entityHit.motionZ = motZ;
									}

									if (movingObject.entityHit instanceof EntityLivingBase) {
										if (!this.worldObj.isRemote && this.shootingEntity instanceof EntityPlayer) {
											doit = true;
											if ((this.isBarrage() || this.isExplosion()) && (int) (Math.random() * 6.0) == 0) {
												doit = false;
											}

											if (doit) {
												String s2 = JRMCoreH.getString((EntityPlayer) this.shootingEntity, JRMCoreH.techNbt[this.technum]);
												JRMCoreH.setString(JRMCoreH.tech_expgiv(s2, JRMCoreH.DBC() ? JRMCoreHDBC.DBCgetConfigTechExpRate() : 1), (EntityPlayer) this.shootingEntity,
														JRMCoreH.techNbt[this.technum]);
											}
										}

										if (this.knockbackStrength > 0 && (this.type >= 7 || JRMCoreConfig.dat5706[this.type])) {
											spe = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
											if (spe > 0.0F) {
												movingObject.entityHit.addVelocity(this.motionX * (double) this.knockbackStrength * 0.6000000238418579 / (double) spe, 0.1,
														this.motionZ * (double) this.knockbackStrength * 0.6000000238418579 / (double) spe);
											}
										}
									}

									if (this.canSpiralNotGoThrough() && !this.isShield() && !this.isExplosion()) {
										if (this.hasEffect()) {
											this.createExplosion(2);
										}

										this.setDead();
									}
								} else if (movingObject.entityHit.isEntityAlive() && !DBCConfig.KiAttackGoThroughInvulnerableEnemies && !this.isShield() && !this.isExplosion()
										&& this.canSpiralNotGoThrough()) {
									this.motionX *= -0.10000000149011612;
									this.motionY *= -0.10000000149011612;
									this.motionZ *= -0.10000000149011612;
									this.rotationYaw += 180.0F;
									this.prevRotationYaw += 180.0F;
									this.setDead();
									this.ticksInAir = 0;
								}
							}
						} else {
							this.xTile = movingObject.blockX;
							this.yTile = movingObject.blockY;
							this.zTile = movingObject.blockZ;
							this.inTile = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
							this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
							this.inGround = true;
							if (this.inTile.getMaterial() != Material.air) {
								this.inTile.onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
							}

							if (movingObject.entityHit != null && this.shootingEntity != null) {
								MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
								var23 = (int) this.damage;
								damagesource = null;
								if (this.shootingEntity == null || this.shootingEntity instanceof EntityDBC && movingObject.entityHit instanceof EntityDBC) {
									this.setDead();
									return;
								}

								damagesource = Ds.causeEntityEnergyAttDamage(this, this.shootingEntity);
								if (this.isBurning()) {
									movingObject.entityHit.setFire(5);
								}

								motX = movingObject.entityHit.motionX;
								motY = movingObject.entityHit.motionY;
								motZ = movingObject.entityHit.motionZ;
								if (!JRMCoreH.isFusionSpectator(movingObject == null ? null : movingObject.entityHit) && !isControllerOfShooter(movingObject.entityHit, shootingEntity)) {
									if (movingObject.entityHit.attackEntityFrom(damagesource, (float) var23)) {
										this.weakenSpiral();
										if (this.type < 7 && !JRMCoreConfig.dat5706[this.type]) {
											movingObject.entityHit.motionX = motX;
											movingObject.entityHit.motionY = motY;
											movingObject.entityHit.motionZ = motZ;
										}

										if (movingObject.entityHit instanceof EntityLivingBase && this.knockbackStrength > 0 && (this.type >= 7 || JRMCoreConfig.dat5706[this.type])) {
											spe = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
											if (spe > 0.0F) {
												movingObject.entityHit.addVelocity(this.motionX * (double) this.knockbackStrength * 0.6000000238418579 / (double) spe, 0.1,
														this.motionZ * (double) this.knockbackStrength * 0.6000000238418579 / (double) spe);
											}
										}

										if (this.hasEffect() && !this.isShield() && !this.isExplosion()) {
											this.createExplosion(2);
										}

										this.setDead();
									} else if (!this.isShield() && !this.isExplosion()) {
										this.motionX *= -0.10000000149011612;
										this.motionY *= -0.10000000149011612;
										this.motionZ *= -0.10000000149011612;
										this.rotationYaw += 180.0F;
										this.prevRotationYaw += 180.0F;
										this.setDead();
										this.ticksInAir = 0;
									}
								}
							}
						}
					}
				} else if ((this.wave > 0 || this.waveCount < 20) && this.target != null && this.target.isDead) {
					this.shrinkWave();
				}
			}

			if (!this.isShield() && !this.isExplosion()) {
				this.posX += this.motionX;
				this.posY += this.motionY;
				this.posZ += this.motionZ;
			}

			this.ShieldPushAwayEntities();
			if ((double) (this.rotationPitch - this.prevRotationPitch) >= 180.0) {
				this.prevRotationPitch += 360.0F;
			}

			if (this.rotationYaw - this.prevRotationYaw < -180.0F) {
				this.prevRotationYaw -= 360.0F;
			}

			if (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
				this.prevRotationYaw += 360.0F;
			}

			float var22 = 1.0F;
			var11 = 0.0F;
			if (this.isInWater()) {
				for (var23 = 0; var23 < 4; ++var23) {
					float var27 = 0.25F;
					this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double) var27, this.posY - this.motionY * (double) var27, this.posZ - this.motionZ * (double) var27, this.motionX,
							this.motionY, this.motionZ);
				}

				var22 = 1.0F;
			}

			if (!this.isShield() && !this.isExplosion()) {
				this.motionX *= (double) var22;
				this.motionY *= (double) var22;
				this.motionZ *= (double) var22;
				this.motionY -= (double) var11;
			}

			this.setPosition(this.posX, this.posY, this.posZ);
		}

		if (this.worldObj.isRemote && this.isDead && this.shootingEntity != null && this.shootingEntity instanceof EntityPlayer && this.shooterHolds) {
			EntityPlayer player = (EntityPlayer) this.shootingEntity;
			ExtendedPlayer.get(player).setAnimKiShoot(0);
			this.shrinkWave();
		}

	}

	@Shadow
	public void setDead() {
		super.setDead();
		if (this.worldObj.isRemote && this.worldObj.isRemote && this.isDead && this.shootingEntity != null && this.shootingEntity instanceof EntityPlayer && this.shooterHolds) {
			EntityPlayer player = (EntityPlayer) this.shootingEntity;
			ExtendedPlayer.get(player).setAnimKiShoot(0);
			this.shrinkWave();
			DBCClientTickHandler.nuller();
			JRMCoreH.isShtng = false;
		}

	}

	@Shadow
	private byte checkReaction(Entity entity, boolean react) {
		String nev = EntityList.getEntityString(entity);
		if (JRMCoreConfig.dat5704) {
			JRMCoreH.log("[JRMC CONSOLE] Name of Entity that was hit by a Ki Blast: " + nev + " | (Can be used for Reaction Config!)");
		}

		byte data = 0;
		if (entity instanceof EntityEnergyAtt) {
			nev = nev + "!" + ((EntityEnergyAtt) entity).getType();
		}

		if (JRMCoreConfig.dat5702.get(nev) != null) {
			data = (Byte) JRMCoreConfig.dat5702.get(nev);
			if (react) {
				this.checkReacts(entity, data);
			}
		}

		if (JRMCoreConfig.dat5703.get(this.getType() + "." + nev) != null) {
			data = (Byte) JRMCoreConfig.dat5703.get(this.getType() + "." + nev);
			if (react) {
				this.checkReacts(entity, data);
			}
		}

		return data;
	}

	@Shadow
	private void checkReacts(Entity entity, byte data) {
		int var23;
		DamageSource damagesource;
		double motX;
		double motY;
		double motZ;
		switch (data) {
		case 1:
			this.setDead();
			break;
		case 2:
			if (!entity.isDead) {
				entity.setDead();
			}
			break;
		case 3:
			if (this.effect == 1 && !entity.isDead) {
				entity.setDead();
			}
			break;
		case 4:
			var23 = (int) this.damage;
			damagesource = Ds.causeEntityEnergyAttDamage(this, this.shootingEntity);
			motX = entity.motionX;
			motY = entity.motionY;
			motZ = entity.motionZ;
			if (!JRMCoreH.isFusionSpectator(entity) && !isControllerOfShooter(entity, shootingEntity) && entity.attackEntityFrom(damagesource, (float) var23)) {
				this.weakenSpiral();
				if (this.type < 7 && !JRMCoreConfig.dat5706[this.type]) {
					entity.motionX = motX;
					entity.motionY = motY;
					entity.motionZ = motZ;
				}
			}
			break;
		case 5:
			if (this.effect == 1) {
				var23 = (int) this.damage;
				damagesource = Ds.causeEntityEnergyAttDamage(this, this.shootingEntity);
				motX = entity.motionX;
				motY = entity.motionY;
				motZ = entity.motionZ;
				if (!JRMCoreH.isFusionSpectator(entity) && !isControllerOfShooter(entity, shootingEntity) && entity.attackEntityFrom(damagesource, (float) var23)) {
					this.weakenSpiral();
					if (this.type < 7 && !JRMCoreConfig.dat5706[this.type]) {
						entity.motionX = motX;
						entity.motionY = motY;
						entity.motionZ = motZ;
					}
				}
			}
		}

	}

	@Shadow
	public long getPower(Entity entity) {
		String powerFormula = JRMCoreConfig.KiAttackPowerFormula;
		long Power = 0L;
		long damage = (long) ((EntityEnergyAtt) entity).getDamage();
		long speed = (long) ((EntityEnergyAtt) entity).getSpe();
		long density = (long) ((EntityEnergyAtt) entity).getDen();
		String[] Formula = powerFormula.toLowerCase().replace(" ", "").replace("(", "").replace("damage", damage + "").replace("speed", speed + "").replace("density", density + "").split("\\)");

		for (int i = 0; i < Formula.length; ++i) {
			String formulaSegment = i == 0 ? Formula[i] : Formula[i].substring(1);
			String method = formulaSegment.contains("+") ? "+"
					: (formulaSegment.contains("-") ? "-" : (formulaSegment.contains("*") ? "*" : (formulaSegment.contains("/") ? "/" : (formulaSegment.contains("%") ? "%" : "null"))));
			long value1 = Long.parseLong(formulaSegment.split("\\" + method)[0]);
			long value2 = 1L;
			long result = value1;
			if (!method.equals("null")) {
				value2 = Long.parseLong(formulaSegment.split("\\" + method)[1]);
				result = JGMathHelper.StringMethod(method, value1, value2);
			}

			if (i > 0) {
				String method2 = Formula[i].substring(0, 1);
				Power = JGMathHelper.StringMethod(method2, Power, result);
			} else {
				Power = result;
			}
		}

		return Power;
	}

	@Shadow
	private boolean canSpiralNotGoThrough() {
		return this.isSpiral() ? !JRMCoreConfig.dat5708[this.effect] : true;
	}

	@Shadow
	private void createExplosion(int type) {
		if (JGConfigDBCGoD.CONFIG_GOD_ENABLED && JGConfigDBCGoD.CONFIG_GOD_ENERGY_ENABLED && this.destroyer) {
			type = 10;
		}

		JRMCoreH.newExpl(this.worldObj, this, this.posX, this.posY, this.posZ, this.explevel, false, this.damage, this.shootingEntity, (byte) type);
	}

	@Shadow
	private List<?> checkForEntitiesInside() {
		AxisAlignedBB aabb = this.boundingBox.copy();
		List<?> entityList = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, aabb);
		return entityList;
	}

	@Shadow
	private void giveExperience(Entity entity, int amount) {
		if (JRMCoreH.DGE(entity) && !this.givenExp) {
			JRMCoreH.jrmcExp(this.shootingEntity, amount, this.getType());
			this.givenExp = true;
		}

	}

	@Shadow
	private void playSoundAtEntity(Entity entity, String s, float f, float f1) {
		this.worldObj.playSoundAtEntity(entity, s, f, f1);
		if (this.isWave() && this.shooterHolds) {
			this.worldObj.playSoundEffect((double) this.strtX(), (double) this.strtY(), (double) this.strtZ(), s, f, f1);
		}

	}

	@Shadow
	private void shrinkWave() {
		JRMCoreH.setByte(0, (EntityPlayer) this.shootingEntity, "jrmcFrng");
		if (!this.shrink) {
			this.shrink();
		}

	}

	@Shadow
	private void shrink() {
		this.shrink = true;
		this.getDataWatcher().updateObject(20, 1);
	}

	@Shadow
	private void setTarget(Entity entity) {
		this.target = entity;
		this.hadTarget = true;
	}

	@Shadow
	private byte killWeakerAttack(EntityEnergyAtt entityEnergyAtt, EntityEnergyAtt entityKi) {
		long power1 = this.getPower(entityEnergyAtt);
		long power2 = this.getPower(entityKi);
		boolean amIStronger = power1 > power2;
		if (power1 != power2) {
			if (amIStronger) {
				if ((double) power1 / JRMCoreConfig.dat5705 >= (double) power2) {
					return 2;
				}
			} else if ((double) power2 / JRMCoreConfig.dat5705 >= (double) power1) {
				this.setDead();
				return 1;
			}
		}

		return 0;
	}

	@Shadow
	private void handleJutsuWallClash(EntityEnergyAttJ3 shield) {
		if (JRMCoreH.NC() && shield != this.shootingEntity && shield instanceof EntityEnAttacks) {
			long shieldPower = shield.getPower(shield);
			long kiPower = this.getPower(this);
			double kiDamage = this.getDamage();
			if (JGConfigDBCGoD.CONFIG_GOD_ENABLED && JGConfigDBCGoD.CONFIG_GOD_ENERGY_ENABLED && this.destroyer && kiPower > shieldPower) {
				shield.setDead();
				return;
			}

			if (kiPower > shieldPower) {
				this.setDamage((double) ((float) this.getDamage() - (float) shield.getDamage()));
				shield.setDead();
			} else if (kiPower < shieldPower) {
				shield.setDamage((double) ((float) shield.getDamage() - (float) kiDamage));
				this.setDead();
			} else {
				shield.setDead();
				this.setDead();
			}

			shield.motionX = 0.0;
			shield.motionY = 0.0;
			shield.motionZ = 0.0;
		}

	}

	@Shadow
	private void handleKiaiClash(EntityEnergyAtt attack) {
		float dam = (float) attack.getDamage();
		if (JGConfigDBCGoD.CONFIG_GOD_ENABLED && JGConfigDBCGoD.CONFIG_GOD_ENERGY_ENABLED && this.destroyer && this.damage * (double) this.DAMAGE_REDUCTION / 2.0 > (double) dam) {
			attack.setDead();
			String s2 = JRMCoreH.getString((EntityPlayer) this.shootingEntity, JRMCoreH.techNbt[this.technum]);
			JRMCoreH.setString(JRMCoreH.tech_expgiv(s2, JRMCoreH.DBC() ? JRMCoreHDBC.DBCgetConfigTechExpRate() : 1), (EntityPlayer) this.shootingEntity, JRMCoreH.techNbt[this.technum]);
		} else {
			if (this.damage / 2.0 > (double) dam) {
				this.setDamage(this.getDamage() - (double) dam);
				attack.setDead();
			} else if (this.damage / 2.0 < (double) dam) {
				attack.setDamage((double) dam - this.damage / 2.0);
				if (this.shootingEntity instanceof EntityPlayer) {
					boolean doit2 = false;
					if (JRMCoreConfig.dat5707 != 0 && (int) (Math.random() * 100.0) < JRMCoreConfig.dat5707) {
						doit2 = true;
					}

					if (doit2) {
						String s2 = JRMCoreH.getString((EntityPlayer) this.shootingEntity, JRMCoreH.techNbt[this.technum]);
						JRMCoreH.setString(JRMCoreH.tech_expgiv(s2, JRMCoreH.DBC() ? JRMCoreHDBC.DBCgetConfigTechExpRate() : 1), (EntityPlayer) this.shootingEntity, JRMCoreH.techNbt[this.technum]);
					}
				}

				this.setDead();
			} else {
				this.setDead();
				attack.setDead();
			}

		}
	}

	@Shadow
	public boolean isWave() {
		return this.getType() == 0;
	}

	@Shadow
	public boolean isBlast() {
		return this.getType() == 1;
	}

	@Shadow
	public boolean isDisk() {
		return this.getType() == 2;
	}

	@Shadow
	public boolean isLaser() {
		return this.getType() == 3;
	}

	@Shadow
	public boolean isLargeBlast() {
		return this.getType() == 5;
	}

	@Shadow
	public boolean isSpiral() {
		return this.getType() == 4;
	}

	@Shadow
	public boolean isBarrage() {
		return this.getType() == 6;
	}

	@Shadow
	public boolean isShield() {
		return this.getType() == 7;
	}

	@Shadow
	public boolean isExplosion() {
		return this.getType() == 8;
	}

	@Shadow
	private boolean hasEffect() {
		return this.effect == 1;
	}

	@Shadow
	private void ShieldPushAwayEntities() {
		if (!this.worldObj.isRemote && this.isShield() && this.hasEffect()) {
			Entity var5 = null;
			List<?> var6 = this.checkForEntitiesInside();

			for (int var9 = 0; var9 < var6.size(); ++var9) {
				var5 = (Entity) var6.get(var9);
				if (!var5.equals(this.shootingEntity) && var5 instanceof EntityLivingBase) {
					float res = 0.5F;
					var5.motionX = ((double) res - (var5.posX - this.posX)) * -1.0;
					var5.motionY = ((double) res - (var5.posY - this.posY)) * -1.0;
					var5.motionZ = ((double) res - (var5.posZ - this.posZ)) * -1.0;
					var5.velocityChanged = true;
				}
			}
		}

	}

	@Shadow
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setShort("xTile", (short) this.xTile);
		nbt.setShort("yTile", (short) this.yTile);
		nbt.setShort("zTile", (short) this.zTile);
		nbt.setByte("inTile", (byte) Block.getIdFromBlock(this.inTile));
		nbt.setByte("inData", (byte) this.inData);
		nbt.setByte("inGround", (byte) (this.inGround ? 1 : 0));
		nbt.setDouble("damage", this.damage);
		nbt.setFloat("size", this.size);
		nbt.setBoolean("destroyer", this.destroyer);
	}

	@Shadow
	public void readEntityFromNBT(NBTTagCompound nbt) {
		this.xTile = nbt.getShort("xTile");
		this.yTile = nbt.getShort("yTile");
		this.zTile = nbt.getShort("zTile");
		this.inTile = Block.getBlockById(nbt.getByte("inTile") & 255);
		this.inData = nbt.getByte("inData") & 255;
		this.inGround = nbt.getByte("inGround") == 1;
		if (nbt.hasKey("damage")) {
			this.damage = nbt.getDouble("damage");
		}

		if (nbt.hasKey("size")) {
			this.size = nbt.getFloat("size");
		}

		if (nbt.hasKey("destroyer")) {
			this.destroyer = nbt.getBoolean("destroyer");
		}

	}

	@Shadow
	public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Shadow
	public float getShadowSize() {
		return 0.0F;
	}

	@Shadow
	public void setDamage(double par1) {
		this.damage = par1;
	}

	@Shadow
	public double getDamage() {
		return this.damage * (double) (JGConfigDBCGoD.CONFIG_GOD_ENABLED && JGConfigDBCGoD.CONFIG_GOD_ENERGY_ENABLED && this.destroyer ? this.DAMAGE_REDUCTION : 1.0F);
	}

	@Shadow
	public void setKnockbackStrength(int par1) {
		this.knockbackStrength = par1;
	}

	@Shadow
	public boolean canAttackWithItem() {
		return false;
	}

	@Shadow
	public boolean isEntityApplicable(Entity var1) {
		return false;
	}

	@Shadow
	public void writeSpawnData(ByteBuf data) {
		data.writeInt(this.shootingEntity == null ? 0 : this.shootingEntity.getEntityId());
		data.writeInt(this.target == null ? 0 : this.target.getEntityId());
		data.writeByte(this.perc);
		data.writeByte(this.type);
		data.writeByte(this.speed);
		data.writeByte(this.perc);
		data.writeByte(this.effect);
		data.writeInt(this.color);
		data.writeInt(this.dam);
		data.writeByte(this.density);
		data.writeShort(this.sincantation);
		String se = "";
		if (this.sts != null) {
			for (int i = 0; i < this.sts.length; ++i) {
				se = se + "," + this.sts[i];
			}

			se = se.substring(1);
		}

		ByteBufUtils.writeUTF8String(data, se);
		data.writeByte(this.technum);
		data.writeShort(this.sfire);
		data.writeShort(this.smove);
		data.writeFloat(this.strtX);
		data.writeFloat(this.strtY);
		data.writeFloat(this.strtZ);
		data.writeFloat(this.size);
		data.writeFloat(this.trgtX);
		data.writeFloat(this.trgtY);
		data.writeFloat(this.trgtZ);
		data.writeByte(this.shrink ? 1 : 0);
		data.writeFloat(this.explevel);
		data.writeInt(this.color2);
		data.writeDouble(this.damage);
		data.writeDouble(this.damageOriginal);
		data.writeInt(this.damageTaken);
		data.writeBoolean(this.destroyer);
	}

	@Shadow
	public void readSpawnData(ByteBuf data) {
		int first = data.readInt();
		this.shootingEntity = first == 0 ? this.shootingEntity : this.worldObj.getEntityByID(first);
		int second = data.readInt();
		this.target = first == 0 ? this.target : this.worldObj.getEntityByID(second);
		this.perc = data.readByte();
		this.type = data.readByte();
		this.speed = data.readByte();
		this.perc = data.readByte();
		this.effect = (short) data.readByte();
		this.color = data.readInt();
		this.dam = data.readInt();
		this.density = data.readByte();
		this.sincantation = data.readShort();
		String[] se = ByteBufUtils.readUTF8String(data).split(",");
		byte[] sts2;
		if (se.length < 3) {
			sts2 = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			this.sts = sts2;
		} else {
			sts2 = new byte[se.length];

			for (int i = 0; i < se.length; ++i) {
				sts2[i] = Byte.parseByte(se[i]);
			}

			this.sts = sts2;
		}

		this.technum = data.readByte();
		this.sfire = data.readShort();
		this.smove = data.readShort();
		this.strtX = data.readFloat();
		this.strtY = data.readFloat();
		this.strtZ = data.readFloat();
		this.size = data.readFloat();
		this.trgtX = data.readFloat();
		this.trgtY = data.readFloat();
		this.trgtZ = data.readFloat();
		this.shrink = data.readByte() == 1;
		this.explevel = data.readFloat();
		this.color2 = data.readInt();
		this.damage = data.readDouble();
		this.damageOriginal = data.readDouble();
		this.damageTaken = data.readInt();
		this.destroyer = data.readBoolean();
	}

	@Shadow
	public Entity getThrower() {
		return null;
	}

	@Shadow
	public void setThrower(Entity entity) {
	}

	@SideOnly(Side.CLIENT)
	@Shadow
	public boolean isInRangeToRenderVec3D(Vec3 par1Vec3) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Shadow
	public double getMaxRenderDistanceSquared() {
		return 65536.0;
	}

	@Shadow
	public boolean isInRangeToRenderDist(double par1) {
		return true;
	}

	@Shadow
	public void generateParticles(EntityEnergyAtt EntityEnergyAtt, Entity entity, int color, int color2, boolean startSpawn) {
		if (EntityEnergyAtt != null && entity != null && EntityEnergyAtt.worldObj.isRemote) {
			for (int i = 0; i < 3; ++i) {
				for (int k = 0; k < JGConfigClientSettings.get_da1(); ++k) {
					float red = (float) (color >> 16 & 255) / 255.0F;
					float green = (float) (color >> 8 & 255) / 255.0F;

					float blue = (float) (color & 255) / 255.0F;
					red *= 0.7F;
					green *= 0.7F;
					blue *= 0.7F;
					float red2 = (float) (color2 >> 16 & 255) / 255.0F;
					float green2 = (float) (color2 >> 8 & 255) / 255.0F;
					float blue2 = (float) (color2 & 255) / 255.0F;
					float life = 0.4F * entity.height;
					double x = (Math.random() * (double) (entity.height * 2.0F) - (double) entity.height) * 0.800000011920929;
					double y = (Math.random() * (double) (entity.height * 2.0F) - (double) entity.height) * 0.800000011920929;
					double z = (Math.random() * (double) (entity.height * 2.0F) - (double) entity.height) * 0.800000011920929;
					Vec3 vec3 = entity.getLookVec();
					double d8 = (double) (entity.width + (startSpawn ? 0.0F : 1.5F));
					double d9 = (double) entity.height;
					double x2;
					double y2;
					double z2;
					if (startSpawn) {
						x2 = (double) EntityEnergyAtt.strtX();
						y2 = (double) EntityEnergyAtt.strtY();
						z2 = (double) EntityEnergyAtt.strtZ();
						x2 += vec3.xCoord * d8;
						y2 += vec3.yCoord * d9 + (double) (entity.height * 0.4F);
						z2 += vec3.zCoord * d8;
					} else {
						x2 = EntityEnergyAtt.posX;
						y2 = EntityEnergyAtt.posY;
						z2 = EntityEnergyAtt.posZ;
					}

					x2 += x;
					y2 += y;
					z2 += z;
					float rotationYaw = -EntityEnergyAtt.rotationYaw;
					float rotationPitch = -EntityEnergyAtt.rotationPitch;
					double motionX = (double) (-MathHelper.sin(rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(rotationPitch / 180.0F * 3.1415927F));
					double motionZ = (double) (MathHelper.cos(rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(rotationPitch / 180.0F * 3.1415927F));
					double motionY = (double) (-MathHelper.sin(rotationPitch / 180.0F * 3.1415927F));
					if (startSpawn) {
						x2 += -motionX * 3.0;
						y2 += -motionY * 3.0;
						y2 -= (double) EntityEnergyAtt.height * 0.25;
						z2 += -motionZ * 3.0;
					}

					motionX *= 0.5;
					motionY *= 0.5;
					motionY += (double) ((float) (Math.random() * 0.10000000149011612) - 0.05F);
					motionZ *= 0.5;
					if (startSpawn) {
						motionX *= -1.0;
						motionY *= -1.0;
						motionZ *= -1.0;
					}

					float scaleStart = ((float) (Math.random() * 0.019999999552965164) + 0.02F) * life * 0.3F;
					float scaleEnd = ((float) (Math.random() * 0.009999999776482582) + 0.02F) * life * 0.3F;
					int textureID = (int) (Math.random() * 3.0) + 8;
					Entity particle = new EntityCusPar("jinryuumodscore:bens_particles.png", entity.worldObj, 0.2F, 0.2F, x2, y2, z2, 0.0, 0.0, 0.0, -motionX, -motionY, -motionZ, 0.0F, textureID, 8,
							3, 32, false, 0.0F, false, 0.0F, 1, "", 30, 2, scaleStart, scaleEnd, scaleStart, 0, red, green, blue, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 2, 0.6F, 0.0F, 0.9F, 0.95F, 0.06F,
							false, -1, true, (Entity) null);
					entity.worldObj.spawnEntityInWorld(particle);
					Entity particle2 = new EntityCusPar("jinryuumodscore:bens_particles.png", entity.worldObj, 0.2F, 0.2F, x2, y2, z2, 0.0, 0.0, 0.0, -motionX, -motionY, -motionZ, 0.0F, textureID, 8,
							3, 32, false, 0.0F, false, 0.0F, 1, "", 30, 2, scaleStart * 0.8F, scaleEnd * 0.8F, scaleStart * 0.8F, 0, red2, green2, blue2, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 2, 0.6F,
							0.0F, 0.9F, 0.95F, 0.06F, false, -1, true, (Entity) null);
					entity.worldObj.spawnEntityInWorld(particle2);
				}
			}
		}

	}

	@Shadow
	public void weakenSpiral() {
		if (this.isSpiral() && !this.canSpiralNotGoThrough() && JRMCoreConfig.SpiralWeakensAfterHit > 0) {
			double minusDamage = (double) JRMCoreConfig.SpiralWeakensAfterHit / 100.0;
			if (!JRMCoreConfig.SpiralWeakensBasedOnStartDamage) {
				if (this.damage <= 0.0) {
					this.setDead();
				} else {
					this.damage *= minusDamage;
				}
			} else {
				if (!((1.0 - minusDamage) * (double) this.damageTaken > this.damage) && !(this.damage <= 0.0)) {
					this.damage = this.damageOriginal * (1.0 - minusDamage * (double) this.damageTaken);
				} else {
					this.setDead();
				}

				++this.damageTaken;
			}

			if (this.damage < 0.0) {
				this.damage = 0.0;
				this.setDead();
			}
		}

	}

	@Shadow
	public float rad(float angle) {
		return angle * 3.1415927F / 180.0F;
	}
}
