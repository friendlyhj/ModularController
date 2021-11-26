package youyihj.modularcontroller.crafttweaker;

import hellfirepvp.modularmachinery.common.machine.IOType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.modularcontroller.IOType")
public enum CrTIOType {
    INPUT(IOType.INPUT),
    OUTPUT(IOType.OUTPUT);

    CrTIOType(IOType ioType) {
        this.ioType = ioType;
    }

    private final IOType ioType;

    @ZenMethod
    public static CrTIOType input() {
        return INPUT;
    }

    @ZenMethod
    public static CrTIOType output() {
        return OUTPUT;
    }

    public IOType getInternal() {
        return ioType;
    }
}
