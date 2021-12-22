package youyihj.modularcontroller.event;

import hellfirepvp.modularmachinery.common.crafting.helper.RecipeCraftingContext;
import hellfirepvp.modularmachinery.common.modifier.ModifierReplacement;
import hellfirepvp.modularmachinery.common.modifier.RecipeModifier;

import java.util.Collections;

public class MachineRecipeCompleteEvent extends MachineRecipeBaseEvent {
    private final RecipeCraftingContext craftingContext;

    public MachineRecipeCompleteEvent(RecipeCraftingContext context) {
        super(context.getMachineController().getWorld(), context.getMachineController().getPos(), context.getParentRecipe(), context.getMachineController().getFoundMachine());
        this.craftingContext = context;
    }

    @Override
    public void addModifier(RecipeModifier modifier) {
        craftingContext.addModifier(new ModifierReplacement(null, Collections.singletonList(modifier), ""));
    }

}
