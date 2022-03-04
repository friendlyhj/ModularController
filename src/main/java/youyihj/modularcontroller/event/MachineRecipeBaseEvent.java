package youyihj.modularcontroller.event;

import hellfirepvp.modularmachinery.common.crafting.MachineRecipe;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import hellfirepvp.modularmachinery.common.modifier.RecipeModifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class MachineRecipeBaseEvent extends MachineBaseEvent {
    protected final MachineRecipe recipe;

    protected MachineRecipeBaseEvent(World world, BlockPos pos, MachineRecipe recipe, DynamicMachine machine) {
        super(world, pos, machine);
        this.recipe = recipe;
    }

    public MachineRecipe getRecipe() {
        return recipe;
    }

    public abstract void addModifier(RecipeModifier modifier);
}
