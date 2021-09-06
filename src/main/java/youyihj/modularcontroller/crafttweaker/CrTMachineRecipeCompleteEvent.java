package youyihj.modularcontroller.crafttweaker;

import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
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
        return event.getRecipeID().toString();
    }

    @ZenGetter("machineID")
    public String getMachineID() {
        return event.getMachineID().toString();
    }
}
