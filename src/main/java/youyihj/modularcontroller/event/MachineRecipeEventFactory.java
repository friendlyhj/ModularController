package youyihj.modularcontroller.event;

import hellfirepvp.modularmachinery.common.crafting.MachineRecipe;
import hellfirepvp.modularmachinery.common.crafting.helper.RecipeCraftingContext;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import hellfirepvp.modularmachinery.common.tiles.TileMachineController;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.modularcontroller.util.ModularMachineryHacks;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public class MachineRecipeEventFactory {
    public static void onCompleted(MachineRecipe recipe, DynamicMachine machine, BlockPos pos, World world) {
        new MachineRecipeCompleteEvent(recipe, machine, pos, world).post();
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
