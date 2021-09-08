package youyihj.modularcontroller.mixins;

import hellfirepvp.modularmachinery.common.block.BlockController;
import hellfirepvp.modularmachinery.common.crafting.ActiveMachineRecipe;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import hellfirepvp.modularmachinery.common.machine.TaggedPositionBlockArray;
import hellfirepvp.modularmachinery.common.tiles.TileMachineController;
import hellfirepvp.modularmachinery.common.tiles.base.TileEntityRestrictedTick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import youyihj.modularcontroller.event.MachineRecipeCompleteEvent;
import youyihj.modularcontroller.util.IDynamicMachinePatch;

@Mixin(value = TileMachineController.class)
public abstract class MixinTileMachineController extends TileEntityRestrictedTick {

    @Shadow(remap = false)
    private DynamicMachine foundMachine;

    @Shadow(remap = false)
    private EnumFacing patternRotation;

    @Shadow(remap = false)
    private ActiveMachineRecipe activeRecipe;

    @ModifyArg(method = "checkStructure", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z"))
    private IBlockState modifyStateToCheckStructure(IBlockState state) {
        return ((IDynamicMachinePatch) foundMachine).getController().getDefaultState().withProperty(BlockController.FACING, patternRotation);
    }

    @Inject(method = "matchesRotation", at = @At(value = "RETURN"), cancellable = true, remap = false)
    private void injectMatchesRotation(TaggedPositionBlockArray pattern, DynamicMachine machine, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            BlockController controller = ((IDynamicMachinePatch) machine).getController();
            if (controller != world.getBlockState(pos).getBlock()) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "doRestrictedTick", at = @At(value = "INVOKE", target = "Lhellfirepvp/modularmachinery/common/crafting/ActiveMachineRecipe;reset()V"), remap = false)
    private void injectDoRestrictedTick(CallbackInfo ci) {
        new MachineRecipeCompleteEvent(activeRecipe.getRecipe(), foundMachine, pos, world).post();
    }
}
