package youyihj.modularcontroller.mixins;

import hellfirepvp.modularmachinery.common.crafting.helper.RecipeCraftingContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import youyihj.modularcontroller.event.MachineRecipeEventFactory;

/**
 * @author youyihj
 */
@Mixin(value = RecipeCraftingContext.class, remap = false)
public abstract class MixinRecipeCraftContext {
    /**
     * @author youyihj
     * @reason ?
     */
    @Overwrite
    public RecipeCraftingContext.CraftingCheckResult canStartCrafting() {
        return MachineRecipeEventFactory.onStarted((RecipeCraftingContext) (Object) this);
    }
}
