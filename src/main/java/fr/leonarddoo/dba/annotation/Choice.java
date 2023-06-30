package fr.leonarddoo.dba.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Repeatable(Choices.class)
public @interface Choice {
    /**
     * The name of the choice
     * @return The name of the choice
     */
    String name();

    /**
     * The value of the choice
     * @return The value of the choice
     */
    String value();
}
