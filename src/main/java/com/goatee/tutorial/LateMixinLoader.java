package com.goatee.tutorial;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.MixinEnvironment.Side;
import org.spongepowered.asm.service.MixinService;

import java.util.*;

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
     * @param loadedMods Set of loaded modids, for use in discrimination of what mixins load
     * @return mixin configurations to be queued and sent to Mixin library.
     */
    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        List<String> toLoad = new ArrayList<>();
        String[] CommonMixins = {"MixinIDBCPlayer", "MixinIPlayer", "MixinJRMCoreH", "MixinScriptDBCPlayer"};
        String[] ClientMixins = {"MixinScriptPlayer"};
        String[] ServerMixins = {};
        Collections.addAll(toLoad, CommonMixins);
        if(MixinService.getService().getSideName().equals(Side.CLIENT.name()))
            Collections.addAll(toLoad, ClientMixins);
        if(MixinService.getService().getSideName().equals(Side.SERVER.name()))
            Collections.addAll(toLoad, ServerMixins);
        return toLoad;
    }
}
