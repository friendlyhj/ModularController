package youyihj.modularcontroller.mixins;

import hellfirepvp.modularmachinery.common.crafting.MachineRecipe;
import hellfirepvp.modularmachinery.common.crafting.helper.RecipeCraftingContext;
import hellfirepvp.modularmachinery.common.tiles.TileMachineController;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import youyihj.modularcontroller.event.MachineRecipeStartEvent;
import youyihj.modularcontroller.util.ModularMachineryHacks;

/**
 * @author youyihj
 */
@Mixin(value = RecipeCraftingContext.class, remap = false)
public abstract class MixinRecipeCraftingContext {
    @Shadow
    @Final
    private TileMachineController machineController;

    @Shadow
    public abstract MachineRecipe getParentRecipe();

    @Inject(method = "canStartCrafting(Ljava/util/function/Predicate;)Lhellfirepvp/modularmachinery/common/crafting/helper/RecipeCraftingContext$CraftingCheckResult;", at = @At("RETURN"), cancellable = true)
    private void injectStartCrafting(CallbackInfoReturnable<RecipeCraftingContext.CraftingCheckResult> cir) {
        handleInject(cir);
    }

//    @Inject(method = "canStartCrafting()Lhellfirepvp/modularmachinery/common/crafting/helper/RecipeCraftingContext$CraftingCheckResult;", at = @At("RETURN"), cancellable = true)
//    private void injectStartCrafting0(CallbackInfoReturnable<RecipeCraftingContext.CraftingCheckResult> cir) {
//        if (ModularMachineryHacks.getModularMachineryVersion().equals("1.10.0")) {
//            handleInject(cir);
//        }
//    }

    private void handleInject(CallbackInfoReturnable<RecipeCraftingContext.CraftingCheckResult> cir) {
        if (cir.getReturnValue().isFailure())
            return;
        MachineRecipeStartEvent event = new MachineRecipeStartEvent((RecipeCraftingContext) (Object) this);
        if (event.post()) {
            cir.setReturnValue(ModularMachineryHacks.createErrorResult(event.getFailureMessage(), 0.98f));
        }
    }
}
