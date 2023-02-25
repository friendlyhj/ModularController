package youyihj.modularcontroller.core;

import java.lang.annotation.*;

/**
 * @author youyihj
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface CommunityEditionDisabled {
    boolean reverse() default false;
}
