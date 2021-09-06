package youyihj.modularcontroller.mixins;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hellfirepvp.modularmachinery.ModularMachinery;
import hellfirepvp.modularmachinery.common.block.BlockController;
import hellfirepvp.modularmachinery.common.lib.BlocksMM;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import youyihj.modularcontroller.ModularController;
import youyihj.modularcontroller.util.IDynamicMachinePatch;

import java.lang.reflect.Type;

@Mixin(value = DynamicMachine.MachineDeserializer.class, remap = false)
public abstract class MixinMachineDeserializer {

    @Inject(method = "deserialize", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void injectDeserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context, CallbackInfoReturnable<DynamicMachine> cir, JsonObject root, String registryName) {
        Block block = ForgeRegistries.BLOCKS.getValue(ModularController.rl(registryName + "_controller"));
        DynamicMachine dynamicMachine = cir.getReturnValue();
        if (block instanceof BlockController) {
            ((IDynamicMachinePatch) dynamicMachine).setController(((BlockController) block));
        } else {
            ((IDynamicMachinePatch) dynamicMachine).setController(BlocksMM.blockController);
        }
        ModularMachinery.log.info("Successfully loaded MM " + dynamicMachine.getRegistryName());
    }
}
