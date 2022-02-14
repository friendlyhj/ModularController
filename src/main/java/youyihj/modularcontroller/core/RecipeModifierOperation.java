package youyihj.modularcontroller.core;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

import java.util.Objects;

/**
 * @author youyihj
 */
@ZenRegister
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

    @ZenMethod
    @ZenOperator(OperatorType.MUL)
    public WithAmount withAmount(float amount) {
        return new WithAmount(this, amount);
    }

    @ZenRegister
    @ZenClass("mods.modularcontroller.RecipeModifierOperationWithAmount")
    public static class WithAmount {
        private final RecipeModifierOperation operation;
        private final float modifier;

        public WithAmount(RecipeModifierOperation operation, float modifier) {
            this.operation = operation;
            this.modifier = modifier;
        }

        @ZenGetter("operation")
        public RecipeModifierOperation getOperation() {
            return operation;
        }

        @ZenGetter("modifier")
        public float getModifier() {
            return modifier;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WithAmount that = (WithAmount) o;
            return Float.compare(that.modifier, modifier) == 0 && operation == that.operation;
        }

        @Override
        public int hashCode() {
            return Objects.hash(operation, modifier);
        }
    }
}
