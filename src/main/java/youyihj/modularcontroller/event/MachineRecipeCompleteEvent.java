package youyihj.modularcontroller.event;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineRecipeCompleteEvent extends BaseEvent {
    private final ResourceLocation recipeID;
    private final ResourceLocation machineID;
    private final BlockPos pos;
    private final World world;

    public MachineRecipeCompleteEvent(ResourceLocation recipeID, ResourceLocation machineID, BlockPos pos, World world) {
        this.recipeID = recipeID;
        this.machineID = machineID;
        this.pos = pos;
        this.world = world;
    }

    public ResourceLocation getRecipeID() {
        return recipeID;
    }

    public ResourceLocation getMachineID() {
        return machineID;
    }

    public BlockPos getPos() {
        return pos;
    }

    public World getWorld() {
        return world;
    }
}
