package youyihj.modularcontroller.event;

import hellfirepvp.modularmachinery.common.crafting.MachineRecipe;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

/**
 * @author youyihj
 */
@Cancelable
public class MachineRecipeStartEvent extends BaseEvent {
    private final DynamicMachine machine;
    private final MachineRecipe recipe;
    private String failureMessage;

    public MachineRecipeStartEvent(DynamicMachine machine, MachineRecipe recipe, World world, BlockPos pos) {
        super(world, pos);
        this.machine = machine;
        this.recipe = recipe;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
        this.setCanceled(true);
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public DynamicMachine getMachine() {
        return machine;
    }

    public MachineRecipe getRecipe() {
        return recipe;
    }
}
