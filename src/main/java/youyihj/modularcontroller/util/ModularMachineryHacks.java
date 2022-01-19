package youyihj.modularcontroller.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import hellfirepvp.modularmachinery.ModularMachinery;
import hellfirepvp.modularmachinery.client.util.BlockArrayRenderHelper;
import hellfirepvp.modularmachinery.common.CommonProxy;
import hellfirepvp.modularmachinery.common.crafting.helper.RecipeCraftingContext;
import hellfirepvp.modularmachinery.common.machine.MachineLoader;
import hellfirepvp.modularmachinery.common.util.BlockArray;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import youyihj.modularcontroller.ModularController;
import youyihj.modularcontroller.block.BlockMMController;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public final class ModularMachineryHacks {
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(BlockMMController.class, MachineJsonPreReader.INSTANCE).create();
    private static Constructor<RecipeCraftingContext.CraftingCheckResult> craftingCheckResultConstructor;
    private static Method checkResultAddErrorMethod;
    private static Method checkResultSetValidityMethod;

    static {
        try {
            Class<RecipeCraftingContext.CraftingCheckResult> craftingCheckResultClass = RecipeCraftingContext.CraftingCheckResult.class;
            craftingCheckResultConstructor = craftingCheckResultClass.getDeclaredConstructor();
            craftingCheckResultConstructor.setAccessible(true);
            checkResultAddErrorMethod = craftingCheckResultClass.getDeclaredMethod("addError", String.class);
            checkResultAddErrorMethod.setAccessible(true);
            checkResultSetValidityMethod = craftingCheckResultClass.getDeclaredMethod("setValidity", float.class);
            checkResultSetValidityMethod.setAccessible(true);
        } catch (Exception e) {
            ModularController.logger.throwing(e);
        }
    }

    public static void loadAllCustomControllers() {
        for (File file : MachineLoader.discoverDirectory(CommonProxy.dataHolder.getMachineryDirectory()).get(MachineLoader.FileType.MACHINE)) {
            try (InputStreamReader isr = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
                GSON.fromJson(isr, BlockMMController.class);
            } catch (JsonParseException e) {
                ModularController.logger.error(file + " is not a valid machine json", e);
            } catch (IOException e) {
                ModularController.logger.error("failed to load custom controllers", e);
            }
        }
    }

    public static RecipeCraftingContext.CraftingCheckResult createErrorResult(String message, float validity) {
        try {
            RecipeCraftingContext.CraftingCheckResult result = craftingCheckResultConstructor.newInstance();
            checkResultAddErrorMethod.invoke(result, message);
            checkResultSetValidityMethod.invoke(result, validity);
            return result;
        } catch (Throwable e) {
            throw new RuntimeException("failed to create such a crafting check result", e);
        }
    }

    public static String getModularMachineryVersion() {
        return Loader.instance().getIndexedModList().get(ModularMachinery.MODID).getVersion();
    }

    @SideOnly(Side.CLIENT)
    public static class ClientStuff {
        private static Constructor<BlockArrayRenderHelper> renderHelperConstructor;

        static {
            try {
                renderHelperConstructor = BlockArrayRenderHelper.class.getDeclaredConstructor(BlockArray.class);
                renderHelperConstructor.setAccessible(true);
            } catch (Exception e) {
                ModularController.logger.throwing(e);
            }
        }

        public static BlockArrayRenderHelper createRenderHelper(BlockArray blockArray) {
            try {
                return renderHelperConstructor.newInstance(blockArray);
            } catch (Exception e) {
                throw new RuntimeException("failed to create such a render helper instance", e);
            }
        }

        public static void writeAllCustomControllerModels() throws IOException {
            IResourceManager resourceManager = Minecraft.getMinecraft().getResourceManager();
            for (BlockMMController controller : BlockMMController.CONTROLLERS.values()) {
                IResource blockStateResource = resourceManager.getResource(ModularController.rl("blockstates/mm_controller.json"));
                File blockStateFile = new File("resources/modularcontroller/blockstates/" + controller.getRegistryName().getResourcePath() + ".json");
                if (!blockStateFile.exists()) {
                    final InputStream inputStream = blockStateResource.getInputStream();
                    final FileOutputStream fileOutputStream = FileUtils.openOutputStream(blockStateFile);
                    IOUtils.copy(inputStream, fileOutputStream);
                    inputStream.close();
                    fileOutputStream.close();
                }
            }
        }
    }
}
