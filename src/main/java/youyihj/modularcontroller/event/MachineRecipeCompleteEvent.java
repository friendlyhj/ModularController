package youyihj.modularcontroller.event;

import hellfirepvp.modularmachinery.common.crafting.MachineRecipe;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineRecipeCompleteEvent extends BaseEvent {
    private final MachineRecipe recipe;
    private final DynamicMachine machine;
    private final BlockPos pos;
    private final World world;

    public MachineRecipeCompleteEvent(MachineRecipe recipe, DynamicMachine machine, BlockPos pos, World world) {
        this.recipe = recipe;
        this.machine = machine;
        this.pos = pos;
        this.world = world;
    }

    public MachineRecipe getRecipe() {
        return recipe;
    }

    public DynamicMachine getMachine() {
        return machine;
    }

    public BlockPos getPos() {
        return pos;
    }

    public World getWorld() {
        return world;
    }
}
