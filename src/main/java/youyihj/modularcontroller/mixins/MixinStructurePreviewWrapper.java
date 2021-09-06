package youyihj.modularcontroller.mixins;

import com.google.common.collect.Iterables;
import hellfirepvp.modularmachinery.common.integration.preview.StructurePreviewWrapper;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import youyihj.modularcontroller.util.IDynamicMachinePatch;

import java.util.List;

@Mixin(value = StructurePreviewWrapper.class, remap = false)
public abstract class MixinStructurePreviewWrapper {
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

    @Inject(method = "getIngredients", at = @At(value = "RETURN"))
    private void injectGetIngredients(IIngredients ingredients, CallbackInfo ci) {
        ingredients.setInput(VanillaTypes.ITEM, new ItemStack(((IDynamicMachinePatch) machine).getController()));
    }
}
