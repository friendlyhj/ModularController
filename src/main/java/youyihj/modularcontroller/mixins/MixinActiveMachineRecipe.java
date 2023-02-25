package youyihj.modularcontroller.mixins;

import hellfirepvp.modularmachinery.common.crafting.ActiveMachineRecipe;
import hellfirepvp.modularmachinery.common.crafting.MachineRecipe;
import hellfirepvp.modularmachinery.common.crafting.helper.RecipeCraftingContext;
import hellfirepvp.modularmachinery.common.tiles.TileMachineController;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import youyihj.modularcontroller.core.CommunityEditionDisabled;
import youyihj.modularcontroller.event.MachineRecipeEventFactory;

/**
 * @author youyihj
 */
@Mixin(value = ActiveMachineRecipe.class, remap = false)
@CommunityEditionDisabled
public abstract class MixinActiveMachineRecipe {
    @Shadow
    @Final
    private MachineRecipe recipe;

    @Shadow
    private int tick;

    @Inject(method = "complete", at = @At("HEAD"))
    private void postCompleteEvent(RecipeCraftingContext completionContext, CallbackInfo ci) {
        MachineRecipeEventFactory.onCompleted(completionContext);
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "FIELD",
                    target = "Lhellfirepvp/modularmachinery/common/crafting/ActiveMachineRecipe;tick:I",
                    opcode = Opcodes.GETFIELD,
                    ordinal = 1,
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    private void postTickEvent(TileMachineController ctrl, RecipeCraftingContext context, CallbackInfoReturnable<TileMachineController.CraftingStatus> cir) {
        String s = MachineRecipeEventFactory.onTick(ctrl, recipe, tick);
        if (s != null) {
            tick = 0;
            cir.setReturnValue(TileMachineController.CraftingStatus.failure(s));
        }
    }
}
