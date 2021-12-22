package youyihj.modularcontroller.event;

import hellfirepvp.modularmachinery.common.crafting.MachineRecipe;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import hellfirepvp.modularmachinery.common.modifier.RecipeModifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

/**
 * @author youyihj
 */
@Cancelable
public class MachineRecipeTickEvent extends MachineRecipeBaseEvent {
    private String failureMessage = "";
    private final int tick;

    public MachineRecipeTickEvent(World world, BlockPos pos, MachineRecipe recipe, DynamicMachine machine, int tick) {
        super(world, pos, recipe, machine);
        this.tick = tick;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
        this.setCanceled(true);
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public int getTick() {
        return tick;
    }

    @Override
    public void addModifier(RecipeModifier modifier) {
        throw new UnsupportedOperationException("Modifier addition is not supported in tick event.");
    }
}
