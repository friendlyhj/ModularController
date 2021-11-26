package youyihj.modularcontroller.crafttweaker;

import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IFacing;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.modularcontroller.event.MachineRecipeCompleteEvent;

@ZenClass("mods.modularcontroller.MachineRecipeCompleteEvent")
public class CrTMachineRecipeCompleteEvent {
    public CrTMachineRecipeCompleteEvent(MachineRecipeCompleteEvent event) {
        this.event = event;
    }

    private final MachineRecipeCompleteEvent event;

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
}
