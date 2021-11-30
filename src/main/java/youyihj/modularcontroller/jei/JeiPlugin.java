package youyihj.modularcontroller.jei;

import hellfirepvp.modularmachinery.common.integration.ModIntegrationJEI;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.ItemStack;
import youyihj.modularcontroller.block.BlockMMController;

/**
 * @author youyihj
 */
@JEIPlugin
public class JeiPlugin implements IModPlugin {
    @Override
    public void register(IModRegistry registry) {
        BlockMMController.CONTROLLERS.values().forEach(controller ->
                controller.getAssociatedMachines().forEach(machine ->
                        registry.addRecipeCatalyst(new ItemStack(controller), ModIntegrationJEI.getCategoryStringFor(machine))
                )
        );
    }
}
