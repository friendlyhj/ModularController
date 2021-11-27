package youyihj.modularcontroller.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import youyihj.modularcontroller.event.MachineRecipeCompleteEvent;

@ZenRegister
@ZenClass("mods.modularcontroller.MachineRecipeCompleteEvent")
public class CrTMachineRecipeCompleteEvent extends CrTMachineRecipeBaseEvent {
    public CrTMachineRecipeCompleteEvent(MachineRecipeCompleteEvent event) {
        super(event);
    }

}
