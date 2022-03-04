package youyihj.modularcontroller.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.IEventHandle;
import crafttweaker.api.event.IEventManager;
import crafttweaker.util.EventList;
import crafttweaker.util.IEventHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.modularcontroller.event.MachineActivatedEvent;
import youyihj.modularcontroller.event.MachineRecipeCompleteEvent;
import youyihj.modularcontroller.event.MachineRecipeStartEvent;
import youyihj.modularcontroller.event.MachineRecipeTickEvent;

@ZenRegister
@ZenExpansion("crafttweaker.events.IEventManager")
public class ExpandEventManger {
    private static final EventList<CrTMachineRecipeCompleteEvent> elMachineRecipeComplete = new EventList<>();
    private static final EventList<CrTMachineRecipeStartEvent> elMachineRecipeStart = new EventList<>();
    private static final EventList<CrTMachineRecipeTickEvent> elMachineRecipeTick = new EventList<>();
    private static final EventList<CrTMachineActivatedEvent> elMachineActivated = new EventList<>();

    @ZenMethod
    public static IEventHandle onMachineRecipeComplete(IEventManager manager, IEventHandler<CrTMachineRecipeCompleteEvent> ev) {
        return elMachineRecipeComplete.add(ev);
    }

    @ZenMethod
    public static IEventHandle onMachineRecipeStart(IEventManager manager, IEventHandler<CrTMachineRecipeStartEvent> ev) {
        return elMachineRecipeStart.add(ev);
    }

    @ZenMethod
    public static IEventHandle onMachineRecipeTick(IEventManager manager, IEventHandler<CrTMachineRecipeTickEvent> ev) {
        return elMachineRecipeTick.add(ev);
    }

    @ZenMethod
    public static IEventHandle onMachineActivated(IEventManager manager, IEventHandler<CrTMachineActivatedEvent> ev) {
        return elMachineActivated.add(ev);
    }

    @Mod.EventBusSubscriber
    public static class Handler {
        @SubscribeEvent
        public static void onMachineRecipeCompleted(MachineRecipeCompleteEvent event) {
            if (elMachineRecipeComplete.hasHandlers()) {
                elMachineRecipeComplete.publish(new CrTMachineRecipeCompleteEvent(event));
            }
        }

        @SubscribeEvent
        public static void onMachineRecipeCrafting(MachineRecipeStartEvent event) {
            if (elMachineRecipeStart.hasHandlers()) {
                elMachineRecipeStart.publish(new CrTMachineRecipeStartEvent(event));
            }
        }

        @SubscribeEvent
        public static void onMachineRecipeTick(MachineRecipeTickEvent event) {
            if (elMachineRecipeTick.hasHandlers()) {
                elMachineRecipeTick.publish(new CrTMachineRecipeTickEvent(event));
            }
        }

        @SubscribeEvent
        public static void onMachineActivated(MachineActivatedEvent event) {
            if (elMachineActivated.hasHandlers()) {
                elMachineActivated.publish(new CrTMachineActivatedEvent(event));
            }
        }
    }
}
