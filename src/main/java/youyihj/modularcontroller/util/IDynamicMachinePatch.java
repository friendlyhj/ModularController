package youyihj.modularcontroller.util;

import hellfirepvp.modularmachinery.common.block.BlockController;
import net.minecraft.util.SoundEvent;

import javax.annotation.Nullable;

/**
 * @author youyihj
 */
public interface IDynamicMachinePatch {
    void setController(BlockController controller);

    BlockController getController();

    void setActivatedSound(SoundEvent sound);

    @Nullable
    SoundEvent getActivatedSound();
}
