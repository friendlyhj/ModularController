package youyihj.modularcontroller.util;

import com.google.gson.*;
import hellfirepvp.modularmachinery.common.data.Config;
import net.minecraft.util.JsonUtils;
import youyihj.modularcontroller.block.BlockMMController;

import java.lang.reflect.Type;

public enum MachineJsonPreReader implements JsonDeserializer<BlockMMController> {
    INSTANCE;

    @Override
    public BlockMMController deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int color = jsonObject.has("color") ? Integer.parseInt(jsonObject.get("color").getAsString(), 16) : Config.machineColor;
        return BlockMMController.create(
                JsonUtils.getString(jsonObject, "registryname"),
                JsonUtils.getString(jsonObject, "localizedname"),
                color
        );
    }
}
