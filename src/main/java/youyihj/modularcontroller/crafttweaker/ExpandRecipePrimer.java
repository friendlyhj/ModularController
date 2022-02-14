package youyihj.modularcontroller.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import hellfirepvp.modularmachinery.common.crafting.helper.ComponentRequirement;
import hellfirepvp.modularmachinery.common.integration.crafttweaker.RecipePrimer;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.modularcontroller.condition.RequirementConditional;

/**
 * @author youyihj
 */
@ZenRegister
@ZenExpansion("mods.modularmachinery.RecipePrimer")
public class ExpandRecipePrimer {
    @ZenMethod
    public static RecipePrimer addConditionalRequirement(RecipePrimer recipePrimer, String description, IRequirementSupplier requirementSupplier, IRecipeModifierFunction modifierFunction) {
        RecipePrimer temp = new RecipePrimer(null, null, 0, 0, false);
        requirementSupplier.supply(temp);
        ComponentRequirement<?, ?> requirement = temp.getComponents().get(0);
        recipePrimer.appendComponent(new RequirementConditional<>(
                requirement,
                (context) -> modifierFunction.execute(new CrTRecipeProcessingContext(context)),
                description
        ));
        return recipePrimer;
    }
}
