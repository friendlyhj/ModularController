package youyihj.modularcontroller.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventCancelable;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.modularcontroller.core.RecipeModifierOperation;
import youyihj.modularcontroller.event.MachineRecipeTickEvent;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("mods.modularcontroller.MachineRecipeTickEvent")
public class CrTMachineRecipeTickEvent extends CrTMachineRecipeBaseEvent implements IEventCancelable {
    private final MachineRecipeTickEvent event;

    public CrTMachineRecipeTickEvent(MachineRecipeTickEvent event) {
        super(event);
        this.event = event;
    }

    @ZenGetter("tick")
    public int getTick() {
        return event.getTick();
    }

    @Override
    public boolean isCanceled() {
        return event.isCanceled();
    }

    @Override
    public void setCanceled(boolean canceled) {
        event.setCanceled(canceled);
    }

    @ZenMethod
    public void setFailed(String message) {
        event.setFailureMessage(message);
    }

    @Override
    public void addModifier(String requirementType, RecipeModifierOperation.WithAmount modifier) {
        CraftTweakerAPI.logError("", new UnsupportedOperationException("Modifier addition is not supported in tick event."));
    }
}
