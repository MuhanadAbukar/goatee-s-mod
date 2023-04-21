package com.goatee.tutorial.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import com.goatee.tutorial.utils.DBCUtils;

import net.minecraft.entity.Entity;
import noppes.npcs.scripted.entity.ScriptEntity;

@Mixin(ScriptEntity.class)
public class MixinScriptEntity<T extends Entity> {
	@SuppressWarnings("unchecked")
	public ScriptEntity<T> e = ((ScriptEntity<T>) (Object) this); // works
	@Shadow(remap = false)
	protected T entity;

	@Unique
	public void setDmgRed(int dmgred) {
		DBCUtils.setDmgRed(entity, dmgred);
	}

	@Unique
	public float getDmgRed() {
		return 100 - DBCUtils.getDmgRed(entity);
	}

}
