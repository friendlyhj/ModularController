package youyihj.modularcontroller.core;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ModDiscoverer;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author youyihj
 */
public class MixinPlugin implements IMixinConfigPlugin {
    public final Map<String, Boolean> ceDisabledClasses = new HashMap<>();

    @Override
    public void onLoad(String mixinPackage) {
        try {
            Field discoverField = Loader.class.getDeclaredField("discoverer");
            discoverField.setAccessible(true);
            ModDiscoverer discoverer = (ModDiscoverer) discoverField.get(Loader.instance());
            discoverer.getASMTable().getAll(CommunityEditionDisabled.class.getCanonicalName()).forEach(data -> {
                ceDisabledClasses.put(data.getClassName(), ((Boolean) data.getAnnotationInfo().getOrDefault("reverse", false)));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return !ceDisabledClasses.containsKey(mixinClassName) || (ceDisabledClasses.get(mixinClassName) ^ !Reference.IS_CE);
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
