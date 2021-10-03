package youyihj.modularcontroller.util;

import hellfirepvp.modularmachinery.ModularMachinery;
import net.minecraft.util.text.translation.I18n;

import java.util.Objects;

/**
 * @author youyihj
 */
public class ControllerInformation {
    private final String machineName;
    private final String machineLocalizeName;
    private final int color;
    private String customName = "";
    private String localizedKey = "";
    private boolean isFullBlock = true;
    private int lightValue = 0;

    public ControllerInformation(String machineName, String machineLocalizeName, int color) {
        this.machineName = machineName;
        this.machineLocalizeName = machineLocalizeName;
        this.color = color;
    }

    public void setName(String customName) {
        this.customName = customName;
    }

    public String getName() {
        return customName.isEmpty() ? machineName + "_controller" : customName;
    }

    public boolean isFullBlock() {
        return isFullBlock;
    }

    public void setFullBlock(boolean fullBlock) {
        isFullBlock = fullBlock;
    }

    public int getLightValue() {
        return lightValue;
    }

    public void setLightValue(int lightValue) {
        this.lightValue = lightValue;
    }

    public int getColor() {
        return color;
    }

    public void setLocalizedKey(String localizedKey) {
        this.localizedKey = localizedKey;
    }

    public String getMachineName() {
        return machineName;
    }

    @SuppressWarnings("deprecation")
    public String getLocalizedName() {
        if (!localizedKey.isEmpty()) {
            return I18n.translateToLocal(localizedKey);
        }
        String localizationKey = ModularMachinery.MODID + "." + machineName;
        String localizedName = I18n.canTranslate(localizationKey) ? I18n.translateToLocal(localizationKey) :
                machineLocalizeName != null ? machineLocalizeName : localizationKey;
        return I18n.translateToLocalFormatted("modular.controller", localizedName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ControllerInformation that = (ControllerInformation) o;
        return color == that.color && isFullBlock == that.isFullBlock && lightValue == that.lightValue && Objects.equals(machineName, that.machineName) && Objects.equals(machineLocalizeName, that.machineLocalizeName) && Objects.equals(customName, that.customName) && Objects.equals(localizedKey, that.localizedKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(machineName, machineLocalizeName, color, customName, localizedKey, isFullBlock, lightValue);
    }
}
