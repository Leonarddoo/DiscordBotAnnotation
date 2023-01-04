package fr.leonarddoo.dba.loader;

import fr.leonarddoo.dba.annotation.Modal;
import fr.leonarddoo.dba.exception.InvalidClassException;

import java.util.HashMap;
import java.util.Map;

public class ModalLoader {

    /**
     * Map of all modals with their class
     */
    protected static final Map<String, Class<?>> MODAL_MAP = new HashMap<>();

    /**
     * Load all modals with annotation in classes
     * @param classes List of classes where modals are
     */
    @SuppressWarnings("unused")
    public void loadModal(Class<?>... classes) {
        for(Class<?> clazz : classes){
            if(clazz.isAnnotationPresent(Modal.class)){
                String id = clazz.getAnnotation(Modal.class).id();
                MODAL_MAP.put(id, clazz);
            }else{
                throw new InvalidClassException("The class " + clazz.getName() + " do not use @Modal annotation.");
            }
        }
    }
}
