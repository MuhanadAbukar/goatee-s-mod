package com.goatee.tutorial.mixins;

import org.spongepowered.asm.mixin.Mixin;

import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.api.entity.IPlayer;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IDBCPlayer.class)
public interface MixinIDBCPlayer extends IPlayer {
	@Unique
	public void exampleMethod(String str);

}
