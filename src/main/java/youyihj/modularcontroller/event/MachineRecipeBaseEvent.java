package youyihj.modularcontroller.event;

import hellfirepvp.modularmachinery.common.block.BlockController;
import hellfirepvp.modularmachinery.common.crafting.MachineRecipe;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import hellfirepvp.modularmachinery.common.modifier.RecipeModifier;
import hellfirepvp.modularmachinery.common.util.MiscUtils;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public abstract class MachineRecipeBaseEvent extends Event {
    protected final World world;
    protected final BlockPos pos;
    protected final MachineRecipe recipe;
    protected final DynamicMachine machine;

    protected MachineRecipeBaseEvent(World world, BlockPos pos, MachineRecipe recipe, DynamicMachine machine) {
        this.world = world;
        this.pos = pos;
        this.recipe = recipe;
        this.machine = machine;
    }

    public boolean post() {
        return MinecraftForge.EVENT_BUS.post(this);
    }

    public World getWorld() {
        return world;
    }

    public BlockPos getPos() {
        return pos;
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
        return world.getBlockState(pos).getValue(BlockController.FACING);
    }

    public MachineRecipe getRecipe() {
        return recipe;
    }

    public DynamicMachine getMachine() {
        return machine;
    }

    public abstract void addModifier(RecipeModifier modifier);
}
