package youyihj.modularcontroller.util;

import hellfirepvp.modularmachinery.common.block.BlockController;
import hellfirepvp.modularmachinery.common.crafting.MachineRecipe;
import hellfirepvp.modularmachinery.common.crafting.helper.RecipeCraftingContext;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import hellfirepvp.modularmachinery.common.util.MiscUtils;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author youyihj
 */
public class RecipeProcessingContext {
    private final World world;
    private final BlockPos pos;
    private final MachineRecipe recipe;
    private final DynamicMachine machine;
    private final EnumFacing facing;

    public RecipeProcessingContext(World world, BlockPos pos, MachineRecipe recipe, DynamicMachine machine) {
        this.world = world;
        this.pos = pos;
        this.recipe = recipe;
        this.machine = machine;
        this.facing = world.getBlockState(pos).getValue(BlockController.FACING);
    }

    public RecipeProcessingContext(RecipeCraftingContext recipeCraftingContext) {
        this(
                recipeCraftingContext.getMachineController().getWorld(),
                recipeCraftingContext.getMachineController().getPos(),
                recipeCraftingContext.getParentRecipe(),
                recipeCraftingContext.getParentRecipe().getOwningMachine()
        );
    }

    public World getWorld() {
        return world;
    }

    public BlockPos getPos() {
        return pos;
    }

    public MachineRecipe getRecipe() {
        return recipe;
    }

    public DynamicMachine getMachine() {
        return machine;
    }

    public BlockPos getOffsetByFacing(int x, int y, int z) {
        BlockPos blockPos = new BlockPos(x, y, z);
        EnumFacing facing = EnumFacing.NORTH;
        EnumFacing controllerFacing = getFacing();
        while (facing != controllerFacing) {
            blockPos = MiscUtils.rotateYCCW(blockPos);
            facing = facing.rotateYCCW();
        }
        return getPos().add(blockPos);
    }

    public EnumFacing getFacing() {
        return facing;
    }
}
