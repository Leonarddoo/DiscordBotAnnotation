package fr.leonarddoo.dba.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.dv8tion.jda.api.interactions.commands.OptionType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Repeatable(Options.class)
public @interface Option {
    /**
     * The type of the option
     * @return The type of the option
     */
    OptionType type();

    /**
     * The name of the option
     * @return The name of the option
     */
    String name();

    /**
     * The description of the option
     * @return The description of the option
     */
    String description();

    /**
     * If the option is required
     * @return If the option is required
     */
    boolean required() default false;

    /**
     * If the option is autocomplete
     * @return If the option is autocomplete
     */
    boolean autocomplete() default false;

    /**
     * The choices of the option
     * @return The choices of the option
     */
    Choice[] choices() default {};
}