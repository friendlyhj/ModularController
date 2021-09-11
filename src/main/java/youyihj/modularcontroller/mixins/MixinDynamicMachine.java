package youyihj.modularcontroller.mixins;

import hellfirepvp.modularmachinery.common.block.BlockController;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import net.minecraft.util.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import youyihj.modularcontroller.util.IDynamicMachinePatch;

import javax.annotation.Nullable;

@Mixin(value = DynamicMachine.class, remap = false)
public abstract class MixinDynamicMachine implements IDynamicMachinePatch {
    private BlockController controller;
    private SoundEvent activatedSound;

    @Override
    public void setController(BlockController controller) {
        this.controller = controller;
    }

    @Override
    public BlockController getController() {
        return controller;
    }

    @Override
    public void setActivatedSound(SoundEvent activatedSound) {
        this.activatedSound = activatedSound;
    }

    @Override
    @Nullable
    public SoundEvent getActivatedSound() {
        return activatedSound;
    }
}
