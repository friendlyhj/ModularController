package youyihj.modularcontroller.event;

import hellfirepvp.modularmachinery.common.crafting.MachineRecipe;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineRecipeCompleteEvent extends BaseEvent {
    private final MachineRecipe recipe;
    private final DynamicMachine machine;

    public MachineRecipeCompleteEvent(MachineRecipe recipe, DynamicMachine machine, BlockPos pos, World world) {
        super(world, pos);
        this.recipe = recipe;
        this.machine = machine;
    }

    public MachineRecipe getRecipe() {
        return recipe;
    }

    public DynamicMachine getMachine() {
        return machine;
    }
}
