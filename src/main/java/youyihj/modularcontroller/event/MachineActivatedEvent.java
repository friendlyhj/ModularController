package youyihj.modularcontroller.event;

import hellfirepvp.modularmachinery.common.machine.DynamicMachine;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author youyihj
 */
public class MachineActivatedEvent extends MachineBaseEvent {
    public MachineActivatedEvent(World world, BlockPos pos, DynamicMachine machine) {
        super(world, pos, machine);
    }
}
