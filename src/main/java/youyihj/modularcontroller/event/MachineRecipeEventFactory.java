package youyihj.modularcontroller.event;

import hellfirepvp.modularmachinery.common.crafting.MachineRecipe;
import hellfirepvp.modularmachinery.common.crafting.helper.RecipeCraftingContext;
import hellfirepvp.modularmachinery.common.tiles.TileMachineController;
import youyihj.modularcontroller.core.CommunityEditionDisabled;
import youyihj.modularcontroller.util.ModularMachineryHacks;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
@CommunityEditionDisabled
public class MachineRecipeEventFactory {
    public static void onCompleted(RecipeCraftingContext context) {
        new MachineRecipeCompleteEvent(context).post();
    }

    public static RecipeCraftingContext.CraftingCheckResult onStarted(RecipeCraftingContext context) {
        RecipeCraftingContext.CraftingCheckResult originResult = context.canStartCrafting((req -> true));
        if (originResult.isFailure())
            return originResult;
        MachineRecipeStartEvent event = new MachineRecipeStartEvent(context);
        if (event.post()) {
            return ModularMachineryHacks.createErrorResult(event.getFailureMessage(), 0.98f);
        } else {
            return originResult;
        }
    }

    @Nullable
    public static String onTick(TileMachineController controller, MachineRecipe recipe, int tick) {
        MachineRecipeTickEvent event = new MachineRecipeTickEvent(controller.getWorld(), controller.getPos(), recipe, recipe.getOwningMachine(), tick);
        return event.post() ? event.getFailureMessage() : null;
    }
}
