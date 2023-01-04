package fr.leonarddoo.dba.loader;

import fr.leonarddoo.dba.annotation.SelectMenu;
import fr.leonarddoo.dba.exception.InvalidClassException;

import java.util.HashMap;
import java.util.Map;

public class SelectMenuLoader {

    /**
     * Map of all select menus with their class
     */
    protected static final Map<String, Class<?>> SELECT_MENU_MAP = new HashMap<>();

    /**
     * Load all select menus with annotation in classes
     * @param classes List of classes where select menus are
     */
    @SuppressWarnings("unused")
    public void loadSelectMenu(Class<?>... classes) {
        for(Class<?> clazz : classes){
            if(clazz.isAnnotationPresent(SelectMenu.class)){
                String id = clazz.getAnnotation(SelectMenu.class).id();
                SELECT_MENU_MAP.put(id, clazz);
            }else{
                throw new InvalidClassException("The class " + clazz.getName() + " do not use @SelectMenu annotation.");
            }
        }
    }
}
