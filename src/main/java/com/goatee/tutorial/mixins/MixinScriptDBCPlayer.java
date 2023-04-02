package com.goatee.tutorial.mixins;

import org.spongepowered.asm.mixin.Unique;

import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.entity.ScriptPlayer;

public abstract class MixinScriptDBCPlayer<T extends EntityPlayerMP> extends ScriptPlayer<T> implements IDBCPlayer {

	public T player;
	
	public MixinScriptDBCPlayer(T player) {
		super(player);
		this.player = player;
		// TODO Auto-generated constructor stub
	}

	@Unique
    public void exampleMethod(){
		getRace();
    //Imp
    };

}
