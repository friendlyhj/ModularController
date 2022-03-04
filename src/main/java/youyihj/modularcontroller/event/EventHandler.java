package youyihj.modularcontroller.event;

import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import youyihj.modularcontroller.util.IDynamicMachinePatch;

/**
 * @author youyihj
 */
@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void onMachineActivated(MachineActivatedEvent event) {
        IDynamicMachinePatch machine = (IDynamicMachinePatch) event.getMachine();
        SoundEvent sound = machine.getActivatedSound();
        if (sound != null) {
            BlockPos pos = event.getPos();
            event.world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
    }
}
