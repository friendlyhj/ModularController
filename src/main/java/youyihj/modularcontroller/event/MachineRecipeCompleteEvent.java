package youyihj.modularcontroller.event;

import hellfirepvp.modularmachinery.common.crafting.MachineRecipe;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineRecipeCompleteEvent extends MachineRecipeBaseEvent {
    public MachineRecipeCompleteEvent(MachineRecipe recipe, DynamicMachine machine, BlockPos pos, World world) {
        super(world, pos, recipe, machine);
    }

}
