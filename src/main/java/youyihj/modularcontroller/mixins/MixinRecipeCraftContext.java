package youyihj.modularcontroller.mixins;

import hellfirepvp.modularmachinery.common.crafting.helper.RecipeCraftingContext;
import hellfirepvp.modularmachinery.common.crafting.requirement.type.RequirementType;
import hellfirepvp.modularmachinery.common.modifier.RecipeModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import youyihj.modularcontroller.event.MachineRecipeEventFactory;
import youyihj.modularcontroller.util.IRecipeCraftingContextPatch;

import java.util.List;
import java.util.Map;

/**
 * @author youyihj
 */
@Mixin(value = RecipeCraftingContext.class, remap = false)
public abstract class MixinRecipeCraftContext implements IRecipeCraftingContextPatch {
    @Shadow
    private Map<RequirementType<?, ?>, List<RecipeModifier>> modifiers;

    /**
     * @author youyihj
     * @reason ?
     */
    @Overwrite
    public RecipeCraftingContext.CraftingCheckResult canStartCrafting() {
        return MachineRecipeEventFactory.onStarted((RecipeCraftingContext) (Object) this);
    }

    @Override
    public Map<RequirementType<?, ?>, List<RecipeModifier>> getAllModifiers() {
        return modifiers;
    }

    @Override
    public void setModifiers(Map<RequirementType<?, ?>, List<RecipeModifier>> modifiers) {
        this.modifiers = modifiers;
    }
}
