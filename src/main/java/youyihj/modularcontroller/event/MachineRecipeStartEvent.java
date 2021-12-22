package youyihj.modularcontroller.event;

import hellfirepvp.modularmachinery.common.crafting.helper.RecipeCraftingContext;
import hellfirepvp.modularmachinery.common.modifier.ModifierReplacement;
import hellfirepvp.modularmachinery.common.modifier.RecipeModifier;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

import java.util.Collections;

/**
 * @author youyihj
 */
@Cancelable
public class MachineRecipeStartEvent extends MachineRecipeBaseEvent {
    private String failureMessage;
    private final RecipeCraftingContext craftingContext;

    public MachineRecipeStartEvent(RecipeCraftingContext context) {
        super(context.getMachineController().getWorld(), context.getMachineController().getPos(), context.getParentRecipe(), context.getMachineController().getFoundMachine());
        this.craftingContext = context;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
        this.setCanceled(true);
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    @Override
    public void addModifier(RecipeModifier modifier) {
        craftingContext.addModifier(new ModifierReplacement(null, Collections.singletonList(modifier), ""));
    }
}
