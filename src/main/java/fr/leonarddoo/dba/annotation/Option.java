package fr.leonarddoo.dba.annotation;

import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.lang.annotation.*;

/**
 * Annotation for Slash Command Options
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(Options.class)
public @interface Option {

    /**
     * Type of the option
     * @return OptionType
     */
    OptionType type();

    /**
     * Name of the option
     * @return String
     */
    String name();

    /**
     * Description of the option
     * @return String
     */
    String description();

    /**
     * Whether the option is required or not (default not required)
     * @return boolean
     */
    boolean required() default false;
}
