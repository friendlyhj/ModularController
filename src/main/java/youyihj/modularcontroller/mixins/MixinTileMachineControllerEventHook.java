package youyihj.modularcontroller.mixins;

import hellfirepvp.modularmachinery.common.crafting.helper.RecipeCraftingContext;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import hellfirepvp.modularmachinery.common.tiles.TileMachineController;
import hellfirepvp.modularmachinery.common.tiles.base.TileEntityRestrictedTick;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import youyihj.modularcontroller.core.CommunityEditionDisabled;
import youyihj.modularcontroller.event.MachineActivatedEvent;
import youyihj.modularcontroller.event.MachineRecipeEventFactory;

/**
 * @author youyihj
 */
@Mixin(value = TileMachineController.class, remap = false)
@CommunityEditionDisabled
public abstract class MixinTileMachineControllerEventHook extends TileEntityRestrictedTick {
    @Shadow
    private DynamicMachine foundMachine;

    private boolean firstCheck = true;

    @Redirect(method = "doRestrictedTick", at = @At(value = "INVOKE", target = "Lhellfirepvp/modularmachinery/common/crafting/helper/RecipeCraftingContext;canStartCrafting()Lhellfirepvp/modularmachinery/common/crafting/helper/RecipeCraftingContext$CraftingCheckResult;"), remap = false)
    private RecipeCraftingContext.CraftingCheckResult postStartEvent(RecipeCraftingContext context) {
        return MachineRecipeEventFactory.onStarted(context);
    }

    @Redirect(
            method = "matchesRotation",
            at = @At(
                    value = "FIELD",
                    target = "Lhellfirepvp/modularmachinery/common/tiles/TileMachineController;foundMachine:Lhellfirepvp/modularmachinery/common/machine/DynamicMachine;",
                    opcode = Opcodes.PUTFIELD
            )
    )
    private void redirectMatchesRotation(TileMachineController tileMachineController, DynamicMachine value) {
        if (value != null && this.foundMachine == null && !firstCheck) {
            new MachineActivatedEvent(world, pos, value).post();
        }
        firstCheck = false;
        foundMachine = value;
    }
}
