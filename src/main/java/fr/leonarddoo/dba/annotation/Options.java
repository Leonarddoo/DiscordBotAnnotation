package fr.leonarddoo.dba.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Multiple Options Annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Options {

    /**
     * Array of Options
     * @return Option[]
     */
    @SuppressWarnings("unused")
    Option[] value();
}
