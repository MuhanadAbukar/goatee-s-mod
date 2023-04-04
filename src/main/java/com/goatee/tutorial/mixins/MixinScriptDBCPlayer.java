package com.goatee.tutorial.mixins;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.CustomNPCsException;
import noppes.npcs.scripted.entity.ScriptDBCPlayer;
import noppes.npcs.scripted.entity.ScriptPlayer;
import org.spongepowered.asm.mixin.Unique;

import JinRyuu.DragonBC.common.DBCKiTech;
import JinRyuu.JRMCore.JRMCoreClient;
import JinRyuu.JRMCore.JRMCoreH;
import JinRyuu.JRMCore.JRMCoreKeyHandler;
import JinRyuu.JRMCore.server.JGPlayerMP;

@Mixin(ScriptDBCPlayer.class)
public abstract class MixinScriptDBCPlayer<T extends EntityPlayerMP> {
	public T player;

	@Unique
	public void exampleMethod(String str){
		System.out.println(str);
		
	}
	@Unique
	public boolean isFlying(){
		return DBCKiTech.floating;
	}
	
	public void setFlight(boolean bo) {
		if(bo) {
			if(!DBCKiTech.floating) {
		KeyBinding k1 = JRMCoreKeyHandler.KiFlight;
		KeyBinding.setKeyBindState(k1.getKeyCode(), bo);
		//KeyBinding.unPressAllKeys();
			}
		}
		else {
			if(DBCKiTech.floating) {
				DBCKiTech.floating = false;
			}
		}	 
		
	}
	
	public boolean inAir() {
		return !player.onGround;
	}
	
	
	public int getMaxBody() {	
		int[] PlyrAttrbts = JRMCoreH.PlyrAttrbts(player);
    	byte pwr = JRMCoreH.getByte(player, "jrmcPwrtyp");
    	byte rce = JRMCoreH.getByte(player, "jrmcRace");
    	byte cls = JRMCoreH.getByte(player, "jrmcClass");
    	int maxBody = JRMCoreH.stat(pwr, 2, PlyrAttrbts[2], rce, cls, 0.0F);
    
    
    	return maxBody;
	}
	
	public int getMaxEnergy() {
		int[] PlyrAttrbts = JRMCoreH.PlyrAttrbts(player);

		byte pwr = JRMCoreH.getByte(player, "jrmcPwrtyp");
	    byte rce = JRMCoreH.getByte(player, "jrmcRace");
	    byte cls = JRMCoreH.getByte(player, "jrmcClass");
	    int maxEnergy = JRMCoreH.stat(pwr, 5, PlyrAttrbts[5], rce, cls, JRMCoreH.SklLvl_KiBs(player, pwr));
	   
	    return maxEnergy;
	}

	public int getMaxStamina() {
		int[] PlyrAttrbts = JRMCoreH.PlyrAttrbts(player);
		byte pwr = JRMCoreH.getByte(player, "jrmcPwrtyp");
	    byte rce = JRMCoreH.getByte(player, "jrmcRace");
	    byte cls = JRMCoreH.getByte(player, "jrmcClass");
	    int maxStam = JRMCoreH.stat(pwr, 3, PlyrAttrbts[2], rce, cls, 0.0F);
	    
	    return maxStam;
	}
	
	
	public String StusEffectsMe() {
		return JRMCoreH.StusEfctsMe();
	}
	
	public void changeDBCAnim(int i) {
		
		if(i == 1 || i == 2) {
		JRMCoreH.Anim(i);		
		}
		else {
            throw new CustomNPCsException("Invalid Animation ID : " + i + "\nValid IDs are: 1 for Flight \nand 2 for Standing", new Object[0]);

		}
	}
	
	public int[] getAllStats() {
		int[] stats = new int[6];
		NBTTagCompound nbt = player.getEntityData().getCompoundTag("PlayerPersisted");
		stats[0] = nbt.getInteger("jrmcStrI");
		stats[1] = nbt.getInteger("jrmcDexI");
		stats[2] = nbt.getInteger("jrmcWilI");
		stats[3] = nbt.getInteger("jrmcCnsI");
		stats[4] = nbt.getInteger("jrmcIntI");
		stats[5] = nbt.getInteger("jrmcCncI");
		
			return stats;
		}
	
	public int getFullStat(int statid) { //str 0, dex 1, con 2, wil 3, mnd 4, spi 5
		JGPlayerMP JG = new JGPlayerMP(player);
		NBTTagCompound nbt = player.getEntityData().getCompoundTag("PlayerPersisted");
	  	JG.setNBT(nbt);
		return JG.getAttribute(statid);
	}
	
	public int[] getAllFullStats() {
		int[] fullstats = new int[6];
		JGPlayerMP JG = new JGPlayerMP(player);
		NBTTagCompound nbt = player.getEntityData().getCompoundTag("PlayerPersisted");
	  	JG.setNBT(nbt);
		fullstats[0] = JG.getAttribute(0);
		fullstats[1] = JG.getAttribute(1);
		fullstats[2] = nbt.getInteger("jrmcCnsI");
		fullstats[3] = JG.getAttribute(3);
		fullstats[4] = nbt.getInteger("jrmcIntI");
		fullstats[5] = nbt.getInteger("jrmcCncI");
		
			return fullstats;
		}
	
	public JGPlayerMP JGPlayer() {
		JGPlayerMP JG = new JGPlayerMP(player);
		NBTTagCompound nbt = player.getEntityData().getCompoundTag("PlayerPersisted");
	  	JG.setNBT(nbt);
		return JG;
	}
	
	
	
	
	
		
	
	
}
