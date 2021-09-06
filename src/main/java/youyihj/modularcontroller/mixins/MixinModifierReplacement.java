package youyihj.modularcontroller.mixins;

import hellfirepvp.modularmachinery.common.modifier.ModifierReplacement;
import net.minecraft.util.text.translation.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author youyihj
 */
@Mixin(value = ModifierReplacement.class, remap = false)
public abstract class MixinModifierReplacement {
    @SuppressWarnings("deprecation")
    @Inject(method = "getDescriptionLines", at = @At("RETURN"), cancellable = true)
    private void injectGetDescriptionLines(CallbackInfoReturnable<List<String>> cir) {
        List<String> lines = cir.getReturnValue();
        cir.setReturnValue(lines.stream().map(I18n::translateToLocal).collect(Collectors.toList()));
    }
}
