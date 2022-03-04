package youyihj.modularcontroller.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.modularcontroller.core.RecipeModifierOperation;
import youyihj.modularcontroller.event.MachineRecipeBaseEvent;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("mods.modularcontroller.MachineRecipeBaseEvent")
public abstract class CrTMachineRecipeBaseEvent extends CrTMachineBaseEvent {
    private final MachineRecipeBaseEvent event;

    public CrTMachineRecipeBaseEvent(MachineRecipeBaseEvent event) {
        super(event);
        this.event = event;
    }

    @ZenGetter("recipeID")
    public String getRecipeID() {
        return event.getRecipe().getRegistryName().toString();
    }

    @ZenMethod
    public abstract void addModifier(String requirementType, RecipeModifierOperation.WithAmount modifier);
}
