package youyihj.modularcontroller.core;

/**
 * @author youyihj
 */
public class Reference {
    public static final String MOD_ID = "modularcontroller";
    public static final String MOD_NAME = "Modular Controller";
    public static final String VERSION = "1.4.3";
    public static final String DEPENDENCIES = "required-after:modularmachinery@[1.11.0,1.11.1];required-after:crafttweaker@[4.1.20,);required-after:mixinbooter";

    public static final boolean IS_CE = isClassExists("github.kasuminova.mmce.common.concurrent.Action", Reference.class.getClassLoader());

    public static boolean isClassExists(String name, ClassLoader classLoader) {
        try {
            Class.forName(name, false, classLoader);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
