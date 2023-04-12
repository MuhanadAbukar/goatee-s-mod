package com.goatee.tutorial;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

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
        List<String> toLoad = new ArrayList<String>();
        String[] mixins = {"MixinIDBCPlayer", "MixinIPlayer", "MixinJRMCoreH", "MixinScriptDBCPlayer", "MixinScriptPlayer"};
        return Arrays.asList(mixins);
    }
}
