package youyihj.modularcontroller.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import hellfirepvp.modularmachinery.common.integration.crafttweaker.RecipePrimer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("mods.modularcontroller.IRequirementSupplier")
@FunctionalInterface
public interface IRequirementSupplier {
    @ZenMethod
    void supply(RecipePrimer primer);
}
