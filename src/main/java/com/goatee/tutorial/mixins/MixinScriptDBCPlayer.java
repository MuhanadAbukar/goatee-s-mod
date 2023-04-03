package com.goatee.tutorial.mixins;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.entity.ScriptDBCPlayer;
import noppes.npcs.scripted.entity.ScriptPlayer;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ScriptDBCPlayer.class)
public abstract class MixinScriptDBCPlayer {

	@Unique
	public void exampleMethod(String str){
		System.out.println(str);
	}
}
