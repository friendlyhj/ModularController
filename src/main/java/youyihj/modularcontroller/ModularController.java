package youyihj.modularcontroller;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;
import youyihj.modularcontroller.block.BlockMMController;
import youyihj.modularcontroller.core.Reference;
import youyihj.modularcontroller.crafttweaker.CraftTweakerExtension;
import youyihj.modularcontroller.util.ModularMachineryHacks;

import java.io.IOException;

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

    @Mod.EventBusSubscriber
    public static final class CommonRegistry {
        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            BlockMMController.CONTROLLERS.forEach(event.getRegistry()::register);
        }

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            BlockMMController.CONTROLLER_ITEMS.forEach(event.getRegistry()::register);
        }
    }

    @Mod.EventBusSubscriber(Side.CLIENT)
    public static final class ModelRegistry {
        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            BlockMMController.CONTROLLER_ITEMS.forEach(ModelRegistry::registerItemModel);
        }

        @SubscribeEvent
        public static void blockColors(ColorHandlerEvent.Block event) {
            BlockMMController.CONTROLLERS.forEach(controller -> {
                BlockColors blockColors = event.getBlockColors();
                blockColors.registerBlockColorHandler(controller::getColorMultiplier, controller);
            });
        }

        @SubscribeEvent
        public static void itemColors(ColorHandlerEvent.Item event) {
            BlockMMController.CONTROLLERS.forEach(controller -> {
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
