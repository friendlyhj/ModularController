package youyihj.modularcontroller.util;

import hellfirepvp.modularmachinery.common.block.BlockController;

/**
 * @author youyihj
 */
public interface IDynamicMachinePatch {
    void setController(BlockController controller);

    BlockController getController();
}
