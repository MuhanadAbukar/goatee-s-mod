package com.goatee.tutorial.mixin.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.util.ChatComponentText;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.api.entity.IPlayer;

@Mixin(IDBCPlayer.class)
public interface MixinIDBCPlayer extends IPlayer {
	//@Inject(at = @At(value = "HEAD"), method = "getRace", cancellable = true)
	//void exampleMethod(CallbackInfo ci);

}
