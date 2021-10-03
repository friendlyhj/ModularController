package youyihj.modularcontroller.util;

import com.google.gson.*;
import hellfirepvp.modularmachinery.common.data.Config;
import net.minecraft.util.JsonUtils;
import youyihj.modularcontroller.block.BlockMMController;

import java.lang.reflect.Type;
import java.util.Optional;

public enum MachineJsonPreReader implements JsonDeserializer<BlockMMController> {
    INSTANCE;

    @Override
    public BlockMMController deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int color = jsonObject.has("color") ? Integer.parseInt(jsonObject.get("color").getAsString(), 16) : Config.machineColor;
        String registryName = JsonUtils.getString(jsonObject, "registryname");
        String localizedName = JsonUtils.getString(jsonObject, "localizedname");
        ControllerInformation information = new ControllerInformation(registryName, localizedName, color);
        Optional.ofNullable(jsonObject.get("controller")).map(JsonElement::getAsJsonObject).ifPresent(jsonInfo -> {
            information.setFullBlock(JsonUtils.getBoolean(jsonInfo, "fullBlock", true));
            information.setLocalizedKey(JsonUtils.getString(jsonInfo, "localizedKey", ""));
            information.setName(JsonUtils.getString(jsonInfo, "name", ""));
            information.setLightValue(JsonUtils.getInt(jsonInfo, "lightValue", 0));
            information.setEnableAlpha(JsonUtils.getBoolean(jsonInfo, "alphaEnabled", false));
        });
        return BlockMMController.create(information);
    }
}
