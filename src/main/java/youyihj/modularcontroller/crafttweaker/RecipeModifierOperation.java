package youyihj.modularcontroller.crafttweaker;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * @author youyihj
 */
@ZenClass("mods.modularcontroller.RecipeModifierOperation")
public enum RecipeModifierOperation {
    ADDITION(0, false),
    MULTIPLIER(1, false),
    ADDITION_CHANCE(0, true),
    MULTIPLIER_CHANCE(1, true);

    private final int internalCode;
    private final boolean affectChance;

    RecipeModifierOperation(int internalCode, boolean affectChance) {
        this.internalCode = internalCode;
        this.affectChance = affectChance;
    }

    @ZenMethod
    public static RecipeModifierOperation addition() {
        return ADDITION;
    }

    @ZenMethod
    public static RecipeModifierOperation multiplier() {
        return MULTIPLIER;
    }

    @ZenMethod
    public static RecipeModifierOperation additionChance() {
        return ADDITION_CHANCE;
    }

    @ZenMethod
    public static RecipeModifierOperation multiplierChance() {
        return MULTIPLIER_CHANCE;
    }

    public int getInternal() {
        return internalCode;
    }

    public boolean affectChance() {
        return affectChance;
    }
}
