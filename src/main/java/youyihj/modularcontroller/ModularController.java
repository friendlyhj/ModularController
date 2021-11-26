package youyihj.modularcontroller;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CTChatCommand;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import crafttweaker.mc1120.commands.SpecialMessagesChat;
import hellfirepvp.modularmachinery.common.lib.RegistriesMM;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;
import youyihj.modularcontroller.block.BlockMMController;
import youyihj.modularcontroller.core.Reference;
import youyihj.modularcontroller.crafttweaker.CraftTweakerExtension;
import youyihj.modularcontroller.util.ModularMachineryHacks;

import java.io.IOException;
import java.util.Objects;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.getClickableCommandText;
import static crafttweaker.mc1120.commands.SpecialMessagesChat.getNormalMessage;

@Mod(
        modid = Reference.MOD_ID,
        name = Reference.MOD_NAME,
        version = Reference.VERSION,
        dependencies = Reference.DEPENDENCIES
)
public class ModularController {

    public static Logger logger;

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(Reference.MOD_ID, path);
    }

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        CraftTweakerExtension.registerAllClasses();
        ModularMachineryHacks.loadAllCustomControllers();
        if (FMLCommonHandler.instance().getSide().isClient() && Loader.isModLoaded("resourceloader")) {
            try {
                ModularMachineryHacks.ClientStuff.writeAllCustomControllerModels();
            } catch (IOException e) {
                logger.error("failed to write controller models", e);
            }
        }
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        CTChatCommand.registerCommand(new CraftTweakerCommand("requirementTypes") {
            @Override
            protected void init() {
                setDescription(getClickableCommandText("\u00A72/ct requirementTypes", "/ct requirementTypes", true), getNormalMessage(" \u00A73Outputs a list of all requirement types in the game to the crafttweaker.log"));
            }

            @Override
            public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
                CraftTweakerAPI.logCommand("Requirement Types: ");
                RegistriesMM.REQUIREMENT_TYPE_REGISTRY.getKeys().stream()
                        .map(Objects::toString)
                        .forEach(CraftTweakerAPI::logCommand);

                sender.sendMessage(SpecialMessagesChat.getLinkToCraftTweakerLog("List of requirement types generated;", sender));
            }
        });
    }

    @Mod.EventBusSubscriber
    public static final class CommonRegistry {
        public static final SoundEvent MACHINE_ACTIVATED_INDUSTRY = new SoundEvent(rl("machine_activated_industry")).setRegistryName(rl("machine_activated_industry"));
        public static final SoundEvent MACHINE_ACTIVATED_SC_FICTION = new SoundEvent(rl("machine_activated_science_fiction")).setRegistryName(rl("machine_activated_science_fiction"));
        public static final SoundEvent MACHINE_ACTIVATED_STEAM = new SoundEvent(rl("machine_activated_steam")).setRegistryName(rl("machine_activated_steam"));

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            BlockMMController.CONTROLLERS.values().forEach(event.getRegistry()::register);
        }

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            BlockMMController.CONTROLLER_ITEMS.forEach(event.getRegistry()::register);
        }

        @SubscribeEvent
        public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
            event.getRegistry().registerAll(
                    MACHINE_ACTIVATED_INDUSTRY,
                    MACHINE_ACTIVATED_SC_FICTION,
                    MACHINE_ACTIVATED_STEAM
            );
        }
    }

    @Mod.EventBusSubscriber(Side.CLIENT)
    public static final class ModelRegistry {
        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            OBJLoader.INSTANCE.addDomain(Reference.MOD_ID);
            BlockMMController.CONTROLLER_ITEMS.forEach(ModelRegistry::registerItemModel);
        }

        @SubscribeEvent
        public static void blockColors(ColorHandlerEvent.Block event) {
            BlockMMController.CONTROLLERS.values().forEach(controller -> {
                BlockColors blockColors = event.getBlockColors();
                blockColors.registerBlockColorHandler(controller::getColorMultiplier, controller);
            });
        }

        @SubscribeEvent
        public static void itemColors(ColorHandlerEvent.Item event) {
            BlockMMController.CONTROLLERS.values().forEach(controller -> {
                ItemColors itemColors = event.getItemColors();
                itemColors.registerItemColorHandler(controller::getColorFromItemstack, controller);
            });
        }

        private static void registerItemModel(Item item) {
            ModelLoader.setCustomModelResourceLocation(item, 0,
                    new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
    }
}
