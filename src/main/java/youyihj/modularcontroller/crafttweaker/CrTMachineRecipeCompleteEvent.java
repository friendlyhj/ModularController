package youyihj.modularcontroller.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import hellfirepvp.modularmachinery.common.crafting.requirement.type.RequirementType;
import hellfirepvp.modularmachinery.common.lib.RegistriesMM;
import hellfirepvp.modularmachinery.common.machine.IOType;
import hellfirepvp.modularmachinery.common.modifier.RecipeModifier;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import youyihj.modularcontroller.core.RecipeModifierOperation;
import youyihj.modularcontroller.event.MachineRecipeCompleteEvent;

@ZenRegister
@ZenClass("mods.modularcontroller.MachineRecipeCompleteEvent")
public class CrTMachineRecipeCompleteEvent extends CrTMachineRecipeBaseEvent {
    private final MachineRecipeCompleteEvent event;

    public CrTMachineRecipeCompleteEvent(MachineRecipeCompleteEvent event) {
        super(event);
        this.event = event;
    }

    @Override
    public void addModifier(String requirementType, float amount, RecipeModifierOperation operation) {
        RequirementType<?, ?> type = RegistriesMM.REQUIREMENT_TYPE_REGISTRY.getValue(new ResourceLocation(requirementType));
        event.addModifier(new RecipeModifier(type, IOType.OUTPUT, amount, operation.getInternal(), operation.affectChance()));
    }

}
