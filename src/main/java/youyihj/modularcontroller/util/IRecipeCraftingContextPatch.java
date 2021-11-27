package youyihj.modularcontroller.util;

import hellfirepvp.modularmachinery.common.crafting.requirement.type.RequirementType;
import hellfirepvp.modularmachinery.common.modifier.RecipeModifier;

import java.util.List;
import java.util.Map;

/**
 * @author youyihj
 */
public interface IRecipeCraftingContextPatch {
    Map<RequirementType<?, ?>, List<RecipeModifier>> getAllModifiers();

    void setModifiers(Map<RequirementType<?, ?>, List<RecipeModifier>> modifiers);
}
