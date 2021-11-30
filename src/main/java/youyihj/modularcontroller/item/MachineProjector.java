package youyihj.modularcontroller.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import youyihj.modularcontroller.ModularController;
import youyihj.modularcontroller.block.BlockMMController;
import youyihj.modularcontroller.core.Reference;

/**
 * @author youyihj
 */
public class MachineProjector extends Item {
    public static final MachineProjector INSTANCE = new MachineProjector();

    private MachineProjector() {
        setMaxStackSize(1);
        setRegistryName(ModularController.rl("machine_projector"));
        setCreativeTab(CreativeTabs.TOOLS);
        setUnlocalizedName(Reference.MOD_ID + ".machine_projector");
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        IBlockState blockState = worldIn.getBlockState(pos);
        Block block = blockState.getBlock();
        if (block instanceof BlockMMController && worldIn.isRemote) {
            BlockMMController controller = (BlockMMController) block;
            ModularController.proxy.renderMachinePreview(controller, pos);
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }
}
