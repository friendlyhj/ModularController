package youyihj.modularcontroller.mixins;

import hellfirepvp.modularmachinery.common.block.BlockController;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import hellfirepvp.modularmachinery.common.machine.TaggedPositionBlockArray;
import hellfirepvp.modularmachinery.common.tiles.TileMachineController;
import hellfirepvp.modularmachinery.common.tiles.base.TileEntityRestrictedTick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import youyihj.modularcontroller.util.IDynamicMachinePatch;

@Mixin(value = TileMachineController.class)
public abstract class MixinTileMachineController extends TileEntityRestrictedTick {

    @Shadow(remap = false)
    private DynamicMachine foundMachine;

    @Shadow(remap = false)
    private EnumFacing patternRotation;

    @Shadow(remap = false)
    private TaggedPositionBlockArray foundPattern;

    @Shadow(remap = false)
    private DynamicMachine.ModifierReplacementMap foundReplacements;

    private boolean receiveRedstone = false;

    @Inject(method = "doRestrictedTick", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private void injectRestrictedTick(CallbackInfo ci) {
        if (!world.isRemote) {
            if (world.getStrongPower(pos) > 0) {
                receiveRedstone = true;
                ci.cancel();
            } else {
                if (receiveRedstone && foundMachine != null) {
                    SoundEvent activatedSound = ((IDynamicMachinePatch) foundMachine).getActivatedSound();
                    if (activatedSound != null) {
                        world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, activatedSound, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    }
                }
                receiveRedstone = false;
            }
        }
    }

    @ModifyArg(method = "checkStructure", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z"))
    private IBlockState modifyStateToCheckStructure(IBlockState state) {
        return ((IDynamicMachinePatch) foundMachine).getController().getDefaultState().withProperty(BlockController.FACING, patternRotation);
    }

    @Inject(method = "matchesRotation", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private void injectMatchesRotation(TaggedPositionBlockArray pattern, DynamicMachine machine, CallbackInfoReturnable<Boolean> cir) {
        BlockController controller = ((IDynamicMachinePatch) machine).getController();
        if (controller != world.getBlockState(pos).getBlock()) {
            cir.setReturnValue(false);
            this.foundPattern = null;
            this.patternRotation = null;
            this.foundMachine = null;
            this.foundReplacements = null;
        }
    }

    @Redirect(
            method = "matchesRotation",
            at = @At(
                    value = "FIELD",
                    target = "Lhellfirepvp/modularmachinery/common/tiles/TileMachineController;foundMachine:Lhellfirepvp/modularmachinery/common/machine/DynamicMachine;",
                    opcode = Opcodes.PUTFIELD
            ),
            remap = false
    )
    private void redirectMatchesRotation(TileMachineController tileMachineController, DynamicMachine value) {
        if (value == null) {
            foundMachine = null;
            return;
        }
        SoundEvent activatedSound = ((IDynamicMachinePatch) value).getActivatedSound();
        if (this.foundMachine == null && activatedSound != null) {
            world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, activatedSound, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
        foundMachine = value;
    }
}
