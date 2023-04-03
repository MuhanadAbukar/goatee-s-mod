package com.goatee.tutorial.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.entity.ScriptDBCPlayer;
import noppes.npcs.scripted.entity.ScriptPlayer;

@Mixin(ScriptDBCPlayer.class)
public abstract class MixinScriptDBCPlayer<T extends EntityPlayerMP> extends ScriptPlayer<T> implements IDBCPlayer {

	
	

	public MixinScriptDBCPlayer(T player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	@Inject(at = @At(value = "HEAD"), method = "attackEntityFrom", cancellable = true)
	private void attackEntityFrom(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callback) {
	  callback.setReturnValue(false);
	}

	@Unique
    public void exampleMethod(){
		getRace();
    //Imp
    };

}
