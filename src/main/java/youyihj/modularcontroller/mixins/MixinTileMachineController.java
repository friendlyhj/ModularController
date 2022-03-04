package youyihj.modularcontroller.mixins;

import hellfirepvp.modularmachinery.common.block.BlockController;
import hellfirepvp.modularmachinery.common.crafting.helper.RecipeCraftingContext;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import hellfirepvp.modularmachinery.common.machine.MachineRegistry;
import hellfirepvp.modularmachinery.common.machine.TaggedPositionBlockArray;
import hellfirepvp.modularmachinery.common.tiles.TileMachineController;
import hellfirepvp.modularmachinery.common.tiles.base.TileEntityRestrictedTick;
import net.minecraft.block.Block;
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
import youyihj.modularcontroller.block.BlockMMController;
import youyihj.modularcontroller.event.MachineActivatedEvent;
import youyihj.modularcontroller.event.MachineRecipeEventFactory;
import youyihj.modularcontroller.util.IDynamicMachinePatch;

import java.util.Iterator;

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

    private boolean firstCheck = true;

    private Iterable<DynamicMachine> machines;

    @Override
    public void onLoad() {
        Block block = world.getBlockState(pos).getBlock();
        if (block instanceof BlockMMController) {
            machines = ((BlockMMController) block).getAssociatedMachines();
        } else {
            machines = MachineRegistry.getRegistry();
        }
    }

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

    @Redirect(method = "checkStructure", at = @At(value = "INVOKE", target = "Lhellfirepvp/modularmachinery/common/machine/MachineRegistry;iterator()Ljava/util/Iterator;"), remap = false)
    private Iterator<DynamicMachine> changeMachinesToCheck(MachineRegistry instance) {

        return machines.iterator();
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

    @Redirect(method = "doRestrictedTick", at = @At(value = "INVOKE", target = "Lhellfirepvp/modularmachinery/common/crafting/helper/RecipeCraftingContext;canStartCrafting()Lhellfirepvp/modularmachinery/common/crafting/helper/RecipeCraftingContext$CraftingCheckResult;"), remap = false)
    private RecipeCraftingContext.CraftingCheckResult postStartEvent(RecipeCraftingContext context) {
        return MachineRecipeEventFactory.onStarted(context);
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
        if (value != null && this.foundMachine == null && !firstCheck) {
            new MachineActivatedEvent(world, pos, value).post();
        }
        firstCheck = false;
        foundMachine = value;
    }
}
