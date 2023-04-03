package com.goatee.tutorial.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.entity.ScriptDBCPlayer;
import noppes.npcs.scripted.entity.ScriptPlayer;

@Mixin(ScriptDBCPlayer.class)
public abstract class MixinScriptDBCPlayer<T extends EntityPlayerMP> extends ScriptPlayer<T> implements IDBCPlayer {

	public MixinScriptDBCPlayer(T player) {
		super(player);
		// TODO Auto-generated constructor stub
	}

	//@Inject(at = @At(value = "HEAD"), method = "getRace", cancellable = true)
	 // private void exampleMethod(CallbackInfo ci){
	//		player.addChatMessage(new ChatComponentText("Your race is " + getRace()));
	    //Imp
	  //  };
	

  

   // @Shadow public int getRace() {
    	 // throw new IllegalStateException("Mixin failed to shadow getRace()");

  //  };
    
    
}
