package youyihj.modularcontroller.crafttweaker;

import crafttweaker.CraftTweakerAPI;

public class CraftTweakerExtension {
    public static void registerAllClasses() {
        CraftTweakerAPI.registerClass(CrTMachineRecipeCompleteEvent.class);
        CraftTweakerAPI.registerClass(CrTMachineRecipeStartEvent.class);
    }
}
