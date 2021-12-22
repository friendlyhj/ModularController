package youyihj.modularcontroller.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IFacing;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.modularcontroller.event.MachineRecipeBaseEvent;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("mods.modularcontroller.MachineRecipeBaseEvent")
public abstract class CrTMachineRecipeBaseEvent {
    private final MachineRecipeBaseEvent event;

    public CrTMachineRecipeBaseEvent(MachineRecipeBaseEvent event) {
        this.event = event;
    }

    @ZenGetter("pos")
    public IBlockPos getPos() {
        return CraftTweakerMC.getIBlockPos(event.getPos());
    }

    @ZenGetter("world")
    public IWorld getWorld() {
        return CraftTweakerMC.getIWorld(event.getWorld());
    }

    @ZenGetter("recipeID")
    public String getRecipeID() {
        return event.getRecipe().getRegistryName().toString();
    }

    @ZenGetter("machineID")
    public String getMachineID() {
        return event.getMachine().getRegistryName().toString();
    }

    @ZenGetter("facing")
    public IFacing getFacing() {
        return CraftTweakerMC.getIFacing(event.getFacing());
    }

    @ZenMethod
    public IBlockPos getOffsetByFacing(int x, int y, int z) {
        return CraftTweakerMC.getIBlockPos(event.getOffsetByFacing(x, y, z));
    }

    @ZenMethod
    public abstract void addModifier(String requirementType, float amount, RecipeModifierOperation operation);
}
