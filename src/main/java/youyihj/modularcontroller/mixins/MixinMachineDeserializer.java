package youyihj.modularcontroller.mixins;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hellfirepvp.modularmachinery.common.block.BlockController;
import hellfirepvp.modularmachinery.common.lib.BlocksMM;
import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import net.minecraft.block.Block;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
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
        IDynamicMachinePatch dynamicMachine = ((IDynamicMachinePatch) cir.getReturnValue());
        if (block instanceof BlockController) {
            dynamicMachine.setController(((BlockController) block));
        } else {
            dynamicMachine.setController(BlocksMM.blockController);
        }
        String activatedSoundName = JsonUtils.getString(root, "activated_sound", "");
        if (!activatedSoundName.isEmpty()) {
            SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(activatedSoundName));
            if (sound != null) {
                dynamicMachine.setActivatedSound(sound);
            }
        }
    }
}
