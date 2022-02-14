package youyihj.modularcontroller.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventCancelable;
import hellfirepvp.modularmachinery.common.crafting.requirement.type.RequirementType;
import hellfirepvp.modularmachinery.common.lib.RegistriesMM;
import hellfirepvp.modularmachinery.common.machine.IOType;
import hellfirepvp.modularmachinery.common.modifier.RecipeModifier;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.modularcontroller.core.RecipeModifierOperation;
import youyihj.modularcontroller.event.MachineRecipeStartEvent;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("mods.modularcontroller.MachineRecipeStartEvent")
public class CrTMachineRecipeStartEvent extends CrTMachineRecipeBaseEvent implements IEventCancelable {
    private final MachineRecipeStartEvent event;

    public CrTMachineRecipeStartEvent(MachineRecipeStartEvent event) {
        super(event);
        this.event = event;
    }

    @ZenMethod
    public void setFailed(String message) {
        event.setFailureMessage(message);
    }

    @Override
    public boolean isCanceled() {
        return event.isCanceled();
    }

    @Override
    public void setCanceled(boolean canceled) {
        event.setCanceled(canceled);
    }

    @Override
    public void addModifier(String requirementType, float amount, RecipeModifierOperation operation) {
        RequirementType<?, ?> type = RegistriesMM.REQUIREMENT_TYPE_REGISTRY.getValue(new ResourceLocation(requirementType));
        event.addModifier(new RecipeModifier(type, IOType.INPUT, amount, operation.getInternal(), operation.affectChance()));
    }
}
