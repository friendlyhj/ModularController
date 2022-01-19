package youyihj.modularcontroller.proxy;

import net.minecraft.util.math.BlockPos;
import youyihj.modularcontroller.block.BlockMMController;
import youyihj.modularcontroller.util.ModularMachineryHacks;

/**
 * @author youyihj
 */
public class CommonProxy {
    public void renderMachinePreview(BlockMMController controller, BlockPos pos) {
    }

    public void reset() {
    }

    public void preInit() {
        ModularMachineryHacks.loadAllCustomControllers();
    }
}
