package fr.leonarddoo.dba.loader;

import fr.leonarddoo.dba.annotation.Button;
import fr.leonarddoo.dba.exception.InvalidClassException;

import java.util.HashMap;
import java.util.Map;

public class ButtonLoader {

    /**
     * Map of all buttons with their class
     */
    protected static final Map<String, Class<?>> BUTTONS_MAP = new HashMap<>();

    /**
     * Load all buttons with annotation in classes
     * @param classes List of classes where buttons are
     */
    @SuppressWarnings("unused")
    public void loadButtons(Class<?>... classes) {
        for(Class<?> clazz : classes){
            if(clazz.isAnnotationPresent(Button.class)){
                String id = clazz.getAnnotation(Button.class).id();
                BUTTONS_MAP.put(id, clazz);
            }else{
                throw new InvalidClassException("The class " + clazz.getName() + " do not use @Button annotation.");
            }
        }
    }
}
