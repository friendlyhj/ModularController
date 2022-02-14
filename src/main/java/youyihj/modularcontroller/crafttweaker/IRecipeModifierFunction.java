package youyihj.modularcontroller.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.modularcontroller.core.RecipeModifierOperation;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("mods.modularcontroller.IRecipeModifierFunction")
@FunctionalInterface
public interface IRecipeModifierFunction {
    @ZenMethod
    RecipeModifierOperation.WithAmount execute(CrTRecipeProcessingContext context);
}
