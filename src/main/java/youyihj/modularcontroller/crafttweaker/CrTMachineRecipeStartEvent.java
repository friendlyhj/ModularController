package youyihj.modularcontroller.crafttweaker;

import com.google.common.base.Functions;
import crafttweaker.api.event.IEventCancelable;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.modularcontroller.event.MachineRecipeStartEvent;

import java.util.Optional;

/**
 * @author youyihj
 */
@ZenClass("mods.modularcontroller.MachineRecipeStartEvent")
public class CrTMachineRecipeStartEvent implements IEventCancelable {
    private final MachineRecipeStartEvent event;

    public CrTMachineRecipeStartEvent(MachineRecipeStartEvent event) {
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
        return Optional.ofNullable(event.getMachine())
                .map(DynamicMachine::getRegistryName)
                .map(Functions.toStringFunction())
                .orElse("");
    }

    @ZenMethod
    public void setFailed(String message) {
        event.setFailureMessage(message);
    }

    @Override
    public boolean isCanceled() {
        return event.isCanceled();
    }

    @Override
    public void setCanceled(boolean canceled) {
        event.setCanceled(canceled);
    }
}
