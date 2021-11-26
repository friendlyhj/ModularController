package youyihj.modularcontroller.event;

import hellfirepvp.modularmachinery.common.crafting.MachineRecipe;
import hellfirepvp.modularmachinery.common.crafting.helper.RecipeCraftingContext;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import hellfirepvp.modularmachinery.common.modifier.ModifierReplacement;
import hellfirepvp.modularmachinery.common.modifier.RecipeModifier;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

import java.util.Collections;

/**
 * @author youyihj
 */
@Cancelable
public class MachineRecipeStartEvent extends BaseEvent {
    private String failureMessage;
    private final RecipeCraftingContext craftingContext;

    public MachineRecipeStartEvent(RecipeCraftingContext context) {
        super(context.getMachineController().getWorld(), context.getMachineController().getPos());
        this.craftingContext = context;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
        this.setCanceled(true);
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public DynamicMachine getMachine() {
        return craftingContext.getMachineController().getFoundMachine();
    }

    public MachineRecipe getRecipe() {
        return craftingContext.getParentRecipe();
    }

    public void addModifier(RecipeModifier modifier) {
        craftingContext.addModifier(new ModifierReplacement(null, Collections.singletonList(modifier), ""));
    }
}
