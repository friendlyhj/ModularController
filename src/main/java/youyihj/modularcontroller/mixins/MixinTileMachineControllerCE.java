package youyihj.modularcontroller.mixins;

import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import hellfirepvp.modularmachinery.common.tiles.TileMachineController;
import hellfirepvp.modularmachinery.common.tiles.base.TileEntityRestrictedTick;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import youyihj.modularcontroller.core.CommunityEditionDisabled;
import youyihj.modularcontroller.util.IDynamicMachinePatch;

/**
 * @author youyihj
 */
@Mixin(value = TileMachineController.class, remap = false)
@CommunityEditionDisabled(reverse = true)
public abstract class MixinTileMachineControllerCE extends TileEntityRestrictedTick {
    @Shadow
    private DynamicMachine foundMachine;

    private boolean firstCheck = true;

    @Redirect(
            method = "matchesRotation",
            at = @At(
                    value = "FIELD",
                    target = "Lhellfirepvp/modularmachinery/common/tiles/TileMachineController;foundMachine:Lhellfirepvp/modularmachinery/common/machine/DynamicMachine;",
                    opcode = Opcodes.PUTFIELD
            )
    )
    private void redirectMatchesRotation(TileMachineController tileMachineController, DynamicMachine value) {
        if (value != null && this.foundMachine == null && !firstCheck) {
            SoundEvent sound = ((IDynamicMachinePatch) value).getActivatedSound();
            if (sound != null) {
                world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
        }
        firstCheck = false;
        foundMachine = value;
    }
}
