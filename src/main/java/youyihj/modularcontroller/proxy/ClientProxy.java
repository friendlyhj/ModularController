package youyihj.modularcontroller.proxy;

import com.google.common.base.Objects;
import hellfirepvp.modularmachinery.client.util.BlockArrayPreviewRenderHelper;
import hellfirepvp.modularmachinery.client.util.DynamicMachineRenderContext;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import youyihj.modularcontroller.block.BlockMMController;

import java.util.List;

/**
 * @author youyihj
 */
public class ClientProxy extends CommonProxy {
    private BlockPos renderPos;
    private int machineIndex;

    @Override
    public void renderMachinePreview(BlockMMController controller, BlockPos pos) {
        BlockArrayPreviewRenderHelper renderHelper = getBlockArrayPreviewRenderHelper();
        if (!Objects.equal(renderPos, pos)) {
            renderHelper.unloadWorld();
        }
        renderPos = pos;
        List<DynamicMachine> machines = controller.getAssociatedMachines();
        if (machineIndex >= machines.size()) {
            renderHelper.unloadWorld();
        } else {
            DynamicMachine machine = machines.get(machineIndex++);
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            if (player != null) {
                player.sendMessage(
                        new TextComponentTranslation("message.modularcontroller.project", machine.getLocalizedName())
                );
                player.sendMessage(
                        new TextComponentTranslation("message.modularcontroller.next_right_click")
                );
            }
            DynamicMachineRenderContext context = DynamicMachineRenderContext.createContext(machine);
            context.snapSamples();
            renderHelper.startPreview(context);
            renderHelper.placePreview();
        }
    }

    @Override
    public void reset() {
        renderPos = null;
        machineIndex = 0;
    }

    private BlockArrayPreviewRenderHelper getBlockArrayPreviewRenderHelper() {
        return hellfirepvp.modularmachinery.client.ClientProxy.renderHelper;
    }
}
