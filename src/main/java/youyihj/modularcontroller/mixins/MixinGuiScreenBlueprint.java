package youyihj.modularcontroller.mixins;

import com.google.common.collect.Iterables;
import hellfirepvp.modularmachinery.client.gui.GuiScreenBlueprint;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import youyihj.modularcontroller.util.IDynamicMachinePatch;

import java.util.List;

@Mixin(value = GuiScreenBlueprint.class, remap = false)
public abstract class MixinGuiScreenBlueprint {
    @Shadow
    @Final
    private DynamicMachine machine;

    @ModifyArg(method = "drawButtons", at = @At(value = "INVOKE", target = "Lhellfirepvp/modularmachinery/client/util/RenderingUtils;renderBlueStackTooltip(IILjava/util/List;Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/RenderItem;)V"))
    private List<Tuple<ItemStack, String>> modifyArgRenderBlueStackTooltip(List<Tuple<ItemStack, String>> list) {
        ItemStack ctrl = new ItemStack(((IDynamicMachinePatch) machine).getController());
        list.set(0, new Tuple<>(ctrl, "1x " + Iterables.getFirst(ctrl.getTooltip(Minecraft.getMinecraft().player,
                Minecraft.getMinecraft().gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL), "")));
        return list;
    }

    @ModifyArg(method = "render2DHover", at = @At(value = "INVOKE", target = "Lhellfirepvp/modularmachinery/common/util/BlockArray$IBlockStateDescriptor;<init>(Lnet/minecraft/block/state/IBlockState;)V"))
    private IBlockState changeController(IBlockState state) {
        return ((IDynamicMachinePatch) machine).getController().getDefaultState();
    }
}
