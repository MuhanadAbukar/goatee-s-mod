package com.goatee.tutorial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.spongepowered.asm.mixin.MixinEnvironment.Side;
import org.spongepowered.asm.service.MixinService;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

@LateMixin
public class LateMixinLoader implements ILateMixinLoader {
	/**
	 * @return the mixin config, generally `mixins.[modid].late.json`
	 */
	@Override
	public String getMixinConfig() {
		return "mixins.dbcnpcutils.late.json";
	}

	/**
	 * @param loadedMods Set of loaded modids, for use in discrimination of what
	 *                   mixins load
	 * @return mixin configurations to be queued and sent to Mixin library.
	 */
	@Override
	public List<String> getMixins(Set<String> loadedMods) {
		List<String> toLoad = new ArrayList<>();
		String[] CommonMixins = { "MixinIDBCPlayer", "MixinIPlayer", "MixinJRMCoreH", "MixinScriptDBCPlayer",
				"MixinScriptPlayer" };
		String[] ClientMixins = {};
		String[] ServerMixins = {};
		Collections.addAll(toLoad, CommonMixins);
		if (MixinService.getService().getSideName().equals(Side.CLIENT.name()))
			Collections.addAll(toLoad, ClientMixins);
		if (MixinService.getService().getSideName().equals(Side.SERVER.name()))
			Collections.addAll(toLoad, ServerMixins);
		return toLoad;
	}
}
