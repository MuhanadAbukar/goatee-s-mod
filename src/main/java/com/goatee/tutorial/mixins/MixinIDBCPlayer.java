package com.goatee.tutorial.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.api.entity.IPlayer;

@Mixin(IDBCPlayer.class)
public interface MixinIDBCPlayer extends IPlayer {
	 @Unique
	    void exampleMethod();


}
