package fr.leonarddoo.dba.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Leonarddoo
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface StringSelectMenu {

    /**
     * The id of the select menu
     * @return The id of the select menu
     */
    String id();
}
