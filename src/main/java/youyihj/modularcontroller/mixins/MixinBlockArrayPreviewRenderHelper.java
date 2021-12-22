package youyihj.modularcontroller.mixins;

import hellfirepvp.modularmachinery.client.util.BlockArrayPreviewRenderHelper;
import hellfirepvp.modularmachinery.client.util.DynamicMachineRenderContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import youyihj.modularcontroller.ModularController;
import youyihj.modularcontroller.proxy.ClientProxy;

/**
 * @author youyihj
 */
@Mixin(value = BlockArrayPreviewRenderHelper.class)
public abstract class MixinBlockArrayPreviewRenderHelper {
    @Inject(method = "startPreview", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V"), cancellable = true)
    private void disableMessageCalledByMoC(DynamicMachineRenderContext currentContext, CallbackInfoReturnable<Boolean> cir) {
        if (new Exception().getStackTrace()[2].getClassName().equals(ClientProxy.class.getName())) {
            cir.cancel();
        }
    }

    @Inject(method = "clearSelection", at = @At("RETURN"), remap = false)
    private void resetExtraStuff(CallbackInfo ci) {
        ModularController.proxy.reset();
    }
}
