package youyihj.modularcontroller.core;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Set;

/**
 * @author youyihj
 */
public class MixinPlugin implements IMixinConfigPlugin {
    private Method communityEditionDisabledReverseMethod;

    @Override
    public void onLoad(String mixinPackage) {
        try {
            communityEditionDisabledReverseMethod = CommunityEditionDisabled.class.getMethod("reverse");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        try {
            Class<?> mixinClass = Class.forName(mixinClassName, false, Mixin.class.getClassLoader());
            Annotation[] annotations = mixinClass.getAnnotations();
            for (Annotation annotation : annotations) {
                try {
                    return ((Boolean) Proxy.getInvocationHandler(annotation).invoke(annotation, communityEditionDisabledReverseMethod, null)) ^ !Reference.IS_CE;
                } catch (Throwable ignored) {

                }
            }
            return true;
        } catch (Exception e) {
            return true;
        }
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
