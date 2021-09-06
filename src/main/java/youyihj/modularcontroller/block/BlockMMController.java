package youyihj.modularcontroller.block;

import hellfirepvp.modularmachinery.ModularMachinery;
import hellfirepvp.modularmachinery.common.block.BlockController;
import hellfirepvp.modularmachinery.common.item.ItemDynamicColor;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import hellfirepvp.modularmachinery.common.machine.MachineRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BlockMMController extends BlockController implements ItemDynamicColor {

    private final int color;
    private final String machineRegistryName;
    private final String machineLocalizedName;

    public static final List<BlockMMController> CONTROLLERS = new ArrayList<>();
    public static final List<Item> CONTROLLER_ITEMS = new ArrayList<>();

    private BlockMMController(String machineRegistryName, String machineLocalizedName, int color) {
        this.setRegistryName(machineRegistryName + "_controller");
        this.machineRegistryName = machineRegistryName;
        this.machineLocalizedName = machineLocalizedName;
        this.color = color;
    }

    public static BlockMMController create(String machineRegistryName, String machineLocalizedName, int color) {
        BlockMMController controller = new BlockMMController(machineRegistryName, machineLocalizedName, color);

        CONTROLLERS.add(controller);
        CONTROLLER_ITEMS.add(new ItemBlock(controller) {
            @Override
            public String getItemStackDisplayName(ItemStack stack) {
                return controller.getLocalizedName();
            }
        }.setRegistryName(machineRegistryName + "_controller"));

        return controller;
    }

    @Override
    public int getColorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
        return color;
    }

    @Override
    @SuppressWarnings("deprecation")
    public String getLocalizedName() {
        String localizationKey = ModularMachinery.MODID + "." + machineRegistryName;
        String localizedName = I18n.canTranslate(localizationKey) ? I18n.translateToLocal(localizationKey) :
                machineLocalizedName != null ? machineLocalizedName : localizationKey;
        return I18n.translateToLocalFormatted("modular.controller", localizedName);
    }

    @Override
    public int getColorFromItemstack(ItemStack stack, int tintIndex) {
        return color;
    }

    public DynamicMachine getAssociatedMachine() {
        return MachineRegistry.getRegistry().getMachine(new ResourceLocation(ModularMachinery.MODID, machineRegistryName));
    }
}
