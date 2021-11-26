package youyihj.modularcontroller.crafttweaker;

import com.google.common.base.Functions;
import crafttweaker.api.event.IEventCancelable;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IFacing;
import crafttweaker.api.world.IWorld;
import hellfirepvp.modularmachinery.common.crafting.requirement.type.RequirementType;
import hellfirepvp.modularmachinery.common.lib.RegistriesMM;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import hellfirepvp.modularmachinery.common.modifier.RecipeModifier;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.modularcontroller.event.MachineRecipeStartEvent;

import java.util.Optional;

/**
 * @author youyihj
 */
@ZenClass("mods.modularcontroller.MachineRecipeStartEvent")
public class CrTMachineRecipeStartEvent implements IEventCancelable {
    private final MachineRecipeStartEvent event;

    public CrTMachineRecipeStartEvent(MachineRecipeStartEvent event) {
        this.event = event;
    }

    @ZenGetter("pos")
    public IBlockPos getPos() {
        return CraftTweakerMC.getIBlockPos(event.getPos());
    }

    @ZenGetter("world")
    public IWorld getWorld() {
        return CraftTweakerMC.getIWorld(event.getWorld());
    }

    @ZenGetter("recipeID")
    public String getRecipeID() {
        return event.getRecipe().getRegistryName().toString();
    }

    @ZenGetter("machineID")
    public String getMachineID() {
        return Optional.ofNullable(event.getMachine())
                .map(DynamicMachine::getRegistryName)
                .map(Functions.toStringFunction())
                .orElse("");
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

    @ZenGetter("facing")
    public IFacing getFacing() {
        return CraftTweakerMC.getIFacing(event.getFacing());
    }

    @ZenMethod
    public IBlockPos getOffsetByFacing(int x, int y, int z) {
        return CraftTweakerMC.getIBlockPos(event.getOffsetByFacing(x, y, z));
    }

    @ZenMethod
    public void addModifier(String requirementType, CrTIOType ioType, float amount, RecipeModifierOperation operation) {
        RequirementType<?, ?> type = RegistriesMM.REQUIREMENT_TYPE_REGISTRY.getValue(new ResourceLocation(requirementType));
        event.addModifier(new RecipeModifier(type, ioType.getInternal(), amount, operation.getInternal(), operation.affectChance()));
    }
}
