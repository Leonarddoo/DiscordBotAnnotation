package fr.leonarddoo.dba.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for Slash Command
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Command {

    /**
     * Name of the command
     * @return String
     */
    String name();

    /**
     * Description of the command
     * @return String
     */
    String description();

}
