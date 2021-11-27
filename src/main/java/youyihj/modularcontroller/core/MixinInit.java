package youyihj.modularcontroller.core;

import org.spongepowered.asm.mixin.Mixins;
import zone.rong.mixinbooter.MixinLoader;

/**
 * @author youyihj
 */
@MixinLoader
public class MixinInit {
    public MixinInit() {
        Mixins.addConfiguration("mixins.modularcontroller.json");
    }
}
