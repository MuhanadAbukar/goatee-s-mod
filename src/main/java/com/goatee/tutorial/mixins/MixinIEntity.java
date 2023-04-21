package com.goatee.tutorial.mixins;

import org.spongepowered.asm.mixin.Unique;

public interface MixinIEntity {
	
	/* DBC doesn't have any damage reduction mechanisms,
	 * so I hijacked all of the LivingHurt and LivingAttack events, 
	 * rewrote them in a cleaner way (it was a disaster going through trying to understand)
	 * and added damage reduction functionality. 
	 * 100 DmgRed is 100% damage reduction, so entity will not take any damage,
	 * and 0 is entity taking full damage without reduction whatsoever.
	 * This works for ALL entities, even mobs, safezone masters, animals and all,
	 * just set the entity's "dmgred" nbt tag to desired value
	 * 
	 * 
	 */
	@Unique
	public float getDmgRed(); //gets entity damage reduction
	@Unique
	public void setDmgRed(int dmgred);

}
