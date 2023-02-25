package youyihj.modularcontroller.core;

import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;

/**
 * @author youyihj
 */
public class MixinInit implements ILateMixinLoader {

    @Override
    public List<String> getMixinConfigs() {
        return Collections.singletonList("mixins.modularcontroller.json");
    }
}
