package youyihj.modularcontroller.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import youyihj.modularcontroller.event.MachineActivatedEvent;

/**
 * @author youyihj
 */
@ZenRegister
@ZenClass("mods.modularcontroller.MachineActivatedEvent")
public class CrTMachineActivatedEvent extends CrTMachineBaseEvent {

    public CrTMachineActivatedEvent(MachineActivatedEvent event) {
        super(event);
    }
}
