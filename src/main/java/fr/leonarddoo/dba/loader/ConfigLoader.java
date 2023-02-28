package fr.leonarddoo.dba.loader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.leonarddoo.dba.exception.ConfigException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

@SuppressWarnings("unused")
public class ConfigLoader {

    /**
     * Config object
     */
    private static JsonObject config;

    /**
     * Load the config file
     * @param configFile Config file
     * @throws FileNotFoundException If the file is not found
     */
    @SuppressWarnings("unused")
    public static void load(File configFile) throws FileNotFoundException {
        config = (JsonObject) JsonParser.parseReader(new FileReader(configFile));
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
}
