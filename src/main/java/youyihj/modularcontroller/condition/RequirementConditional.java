package youyihj.modularcontroller.condition;

import com.google.gson.JsonObject;
import hellfirepvp.modularmachinery.common.crafting.helper.*;
import hellfirepvp.modularmachinery.common.crafting.requirement.type.RequirementType;
import hellfirepvp.modularmachinery.common.integration.recipe.RecipeLayoutPart;
import hellfirepvp.modularmachinery.common.machine.IOType;
import hellfirepvp.modularmachinery.common.modifier.RecipeModifier;
import hellfirepvp.modularmachinery.common.util.ResultChance;
import net.minecraft.client.resources.I18n;
import youyihj.modularcontroller.ModularController;
import youyihj.modularcontroller.core.RecipeModifierOperation;
import youyihj.modularcontroller.util.RecipeProcessingContext;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.function.Function;

/**
 * @author youyihj
 */
public class RequirementConditional<T> extends ComponentRequirement<T, RequirementConditional.Type<T>> {

    private final Function<RecipeProcessingContext, RecipeModifierOperation.WithAmount> modifierFunction;
    private final ComponentRequirement<T, ?> base;
    private final String description;

    public RequirementConditional(ComponentRequirement<T, ?> base, Function<RecipeProcessingContext, RecipeModifierOperation.WithAmount> modifierFunction, String description) {
        super(Type.get(base.getRequirementType()), base.getActionType());
        this.modifierFunction = modifierFunction;
        this.base = base;
        this.description = description;
    }

    public static class Type<T> extends RequirementType<T, RequirementConditional<T>> {

        private static final Map<RequirementType<?, ?>, Type<?>> CONDITIONAL_TYPES = new HashMap<>();
        private final RequirementType<T, ?> baseType;

        public Type(RequirementType<T, ?> baseType) {
            this.baseType = baseType;
            CONDITIONAL_TYPES.put(baseType, this);
            setRegistryName(ModularController.rl("conditional_" + baseType.getRegistryName().toString().replace(':', '_')));
        }

        @SuppressWarnings("unchecked")
        public static <T> Type<T> get(RequirementType<T, ?> baseType) {
            return ((Type<T>) CONDITIONAL_TYPES.get(baseType));
        }

        public static Collection<Type<?>> getAllTypes() {
            return CONDITIONAL_TYPES.values();
        }

        public RequirementType<T, ?> getBaseType() {
            return baseType;
        }

        @Override
        public ComponentRequirement<T, ? extends RequirementType<T, RequirementConditional<T>>> createRequirement(IOType type, JsonObject jsonObject) {
            throw new UnsupportedOperationException();
        }
    }

    private ComponentRequirement<T, ?> getResultRequirement(RecipeCraftingContext context) {
        RecipeModifierOperation.WithAmount operation = modifierFunction.apply(new RecipeProcessingContext(context));
        if (operation == null) return base;
        RecipeModifier modifier = new RecipeModifier(getRequirementType().getBaseType(), getActionType(), operation.getModifier(), operation.getOperation().getInternal(), operation.getOperation().affectChance());
        return base.deepCopyModified(Collections.singletonList(modifier));
    }

    @Override
    public boolean isValidComponent(ProcessingComponent<?> component, RecipeCraftingContext ctx) {
        return getResultRequirement(ctx).isValidComponent(component, ctx);
    }

    @Override
    public boolean startCrafting(ProcessingComponent<?> component, RecipeCraftingContext context, ResultChance chance) {
        return getResultRequirement(context).startCrafting(component, context, chance);
    }

    @Nonnull
    @Override
    public CraftCheck finishCrafting(ProcessingComponent<?> component, RecipeCraftingContext context, ResultChance chance) {
        return getResultRequirement(context).finishCrafting(component, context, chance);
    }

    @Nonnull
    @Override
    public CraftCheck canStartCrafting(ProcessingComponent<?> component, RecipeCraftingContext context, List<ComponentOutputRestrictor> restrictions) {
        return getResultRequirement(context).canStartCrafting(component, context, restrictions);
    }

    @Override
    public ComponentRequirement<T, Type<T>> deepCopy() {
        return new RequirementConditional<>(base.deepCopy(), modifierFunction, description);
    }

    @Override
    public ComponentRequirement<T, Type<T>> deepCopyModified(List<RecipeModifier> modifiers) {
        return new RequirementConditional<>(base.deepCopyModified(modifiers), modifierFunction, description);
    }

    @Override
    public void startRequirementCheck(ResultChance contextChance, RecipeCraftingContext context) {
        getResultRequirement(context).startRequirementCheck(contextChance, context);
    }

    @Override
    public void endRequirementCheck() {
        base.endRequirementCheck();
    }

    @Nonnull
    @Override
    public String getMissingComponentErrorMessage(IOType ioType) {
        return base.getMissingComponentErrorMessage(ioType);
    }

    @Override
    public JEIComponent<T> provideJEIComponent() {
        return new JEIComponentWithDescription(base.provideJEIComponent());
    }

    private class JEIComponentWithDescription extends JEIComponent<T> {
        private final JEIComponent<T> base;

        public JEIComponentWithDescription(JEIComponent<T> base) {
            this.base = base;
        }

        @Override
        public Class<T> getJEIRequirementClass() {
            return base.getJEIRequirementClass();
        }

        @Override
        public List<T> getJEIIORequirements() {
            return base.getJEIIORequirements();
        }

        @Override
        public RecipeLayoutPart<T> getLayoutPart(Point offset) {
            return base.getLayoutPart(offset);
        }

        @Override
        public void onJEIHoverTooltip(int slotIndex, boolean input, T ingredient, List<String> tooltip) {
            base.onJEIHoverTooltip(slotIndex, input, ingredient, tooltip);
            tooltip.add(I18n.format(description));
        }
    }

}
