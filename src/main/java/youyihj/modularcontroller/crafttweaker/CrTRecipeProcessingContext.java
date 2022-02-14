package youyihj.modularcontroller.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IFacing;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.modularcontroller.util.RecipeProcessingContext;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("mods.modularcontroller.RecipeProcessingContext")
public class CrTRecipeProcessingContext {
    private final RecipeProcessingContext context;

    public CrTRecipeProcessingContext(RecipeProcessingContext context) {
        this.context = context;
    }

    @ZenGetter("pos")
    public IBlockPos getPos() {
        return CraftTweakerMC.getIBlockPos(context.getPos());
    }

    @ZenGetter("world")
    public IWorld getWorld() {
        return CraftTweakerMC.getIWorld(context.getWorld());
    }

    @ZenGetter("recipeID")
    public String getRecipeID() {
        return context.getRecipe().getRegistryName().toString();
    }

    @ZenGetter("machineID")
    public String getMachineID() {
        return context.getMachine().getRegistryName().toString();
    }

    @ZenGetter("facing")
    public IFacing getFacing() {
        return CraftTweakerMC.getIFacing(context.getFacing());
    }

    @ZenMethod
    public IBlockPos getOffsetByFacing(int x, int y, int z) {
        return CraftTweakerMC.getIBlockPos(context.getOffsetByFacing(x, y, z));
    }
}
