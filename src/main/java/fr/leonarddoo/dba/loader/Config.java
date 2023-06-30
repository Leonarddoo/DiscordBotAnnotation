package fr.leonarddoo.dba.loader;

import com.google.gson.*;
import fr.leonarddoo.dba.exception.ConfigException;

import java.io.*;

@SuppressWarnings("unused")
public class Config {

    /**
     * Config object
     */
    private static JsonObject config;
    private static File file;
    private static Gson gson;

    /**
     * Load the config file
     * @param configFile Config file
     * @throws FileNotFoundException If the file is not found
     */
    @SuppressWarnings("unused")
    public static void load(File configFile) throws FileNotFoundException {
        config = (JsonObject) JsonParser.parseReader(new FileReader(configFile));
        file = configFile;
        gson = new Gson();
    }

    /**
     * Get a string from the config
     * @param path Path of the string
     * @return String
     */
    @SuppressWarnings("unused")
    public static String getString(String path) {
        if(config == null) throw new ConfigException("Config not loaded");
        try {
            return config.get(path).getAsString();
        }catch (NullPointerException e){
            throw new ConfigException("The path " + path + " is not found in the config file.");
        }
    }

    /**
     * Get an int from the config
     * @param path Path of the int
     * @return Int
     */
    @SuppressWarnings("unused")
    public static int getInt(String path){
        if(config == null) throw new ConfigException("Config not loaded");
        try{
            return config.get(path).getAsInt();
        }catch (NullPointerException e){
            throw new ConfigException("The path " + path + " is not found in the config file.");
        }
    }

    /**
     * Get a boolean from the config
     * @param path Path of the boolean
     * @return Boolean
     */
    @SuppressWarnings("unused")
    public static boolean getBoolean(String path){
        if(config == null) throw new ConfigException("Config not loaded");
        try{
            return config.get(path).getAsBoolean();
        }catch (NullPointerException e){
            throw new ConfigException("The path " + path + " is not found in the config file.");
        }
    }

    /**
     * Get a double from the config
     * @param path Path of the double
     * @return Double
     */
    @SuppressWarnings("unused")
    public static double getDouble(String path){
        if(config == null) throw new ConfigException("Config not loaded");
        try {
            return config.get(path).getAsDouble();
        }catch (NullPointerException e) {
            throw new ConfigException("The path " + path + " is not found in the config file.");
        }
    }

    /**
     * Get a float from the config
     * @param path Path of the float
     * @return Float
     */
    @SuppressWarnings("unused")
    public static JsonArray getJsonArray(String path){
        if(config == null) throw new ConfigException("Config not loaded");
        try{
            return config.get(path).getAsJsonArray();
        }catch (NullPointerException e){
            throw new ConfigException("The path " + path + " is not found in the config file.");
        }
    }

    /**
     * Get a JsonObject from the config
     * @param path Path of the JsonObject
     * @return JsonObject
     */
    @SuppressWarnings("unused")
    public static JsonObject getJsonObject(String path){
        if(config == null) throw new ConfigException("Config not loaded");
        try{
            return config.get(path).getAsJsonObject();
        }catch (NullPointerException e){
            throw new ConfigException("The path " + path + " is not found in the config file.");
        }
    }

    /**
     * Sets the value of the specified key in the loaded JSON configuration file.
     * @param key the key to modify the value for
     * @param value the new value to set for the specified key
     * @throws ConfigException if the JSON configuration file has not been loaded
     */
    public static void setValue(String key, String value){
        // Vérifier si le fichier JSON est déjà chargé dans l'objet config
        if (config == null) throw new ConfigException("Config not loaded");
        // Modifier la valeur de la clé spécifiée
        JsonElement element = config.get(key);
        try (FileWriter writer = new FileWriter(file)){
            config.addProperty(key, value);
            gson.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            throw new ConfigException("The key " + key + " is not found in the config file.");
        }
    }
}
