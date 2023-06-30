package fr.leonarddoo.dba.loader;

import fr.leonarddoo.dba.annotation.*;
import fr.leonarddoo.dba.element.*;
import fr.leonarddoo.dba.exception.InvalidClassException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to load the commands and events
 * @author Leonarddoo
 */
public class DBALoader {

    /**
     * Instance of DBALoader
     */
    public static DBALoader instance;
    /**
     * JDA instance
     */
    private final JDA jda;
    /**
     * Map of commands
     */
    private final Map<String, DBACommand> COMMANDS_MAP;
    /**
     * Map of buttons
     */
    private final Map<String, DBAButton> BUTTONS_MAP;
    /**
     * Map of stringSelectMenu
     */
    private final Map<String, DBAStringSelectMenu> STRING_MENU_MAP;
    /**
     * Map of entitySelectMenu
     */
    private final Map<String, DBAEntitySelectMenu> ENTITY_MENU_MAP;
    /**
     * Map of modals
     */
    private final Map<String, DBAModal> MODALS_MAP;

    /**
     * Create a new DBALoader
     * @param jda JDA instance
     */
    private DBALoader(JDA jda){
        this.jda = jda;
        COMMANDS_MAP = new HashMap<>();
        BUTTONS_MAP = new HashMap<>();
        STRING_MENU_MAP = new HashMap<>();
        ENTITY_MENU_MAP = new HashMap<>();
        MODALS_MAP = new HashMap<>();
        jda.addEventListener(new Dispatcher(this));
    }

    /**
     * Add commands to JDA
     * @param commandsClass Commands to add
     */
    @SuppressWarnings("unused")
    public DBALoader addDBACommandsToJDA(DBACommand... commandsClass){
        for(DBACommand event : commandsClass){
            Class<?> clazz = event.getClass();
            if(clazz.isAnnotationPresent(Command.class)){
                this.jda.upsertCommand(createCommand(clazz)).queue();
                String cmdName = clazz.getAnnotation(Command.class).name();
                COMMANDS_MAP.put(cmdName, event);
            }else{
                throw new RuntimeException(new InvalidClassException("The class " + clazz.getName() + " is not a valid DBACommand class"));
            }
        }
        return this;
    }

    /**
     * Add commands to a guild
     * @param guild Guild where commands will be added
     * @param commandsClass Commands to add
     */
    @SuppressWarnings("unused")
    public DBALoader addDBACommandsToGuild(Guild guild, DBACommand... commandsClass){
        for(DBACommand event : commandsClass){
            Class<?> clazz = event.getClass();
            if(clazz.isAnnotationPresent(Command.class)){
                guild.upsertCommand(createCommand(clazz)).queue();
                String cmdName = clazz.getAnnotation(Command.class).name();
                COMMANDS_MAP.put(cmdName, event);
            }else{
                throw new RuntimeException(new InvalidClassException("The class " + clazz.getName() + " is not a valid DBACommand class"));
            }
        }
        return this;
    }

    /**
     * Create a CommandData from annotation in class
     * @param clazz  Class where command is
     * @return CommandData
     */
    private CommandData createCommand(Class<?> clazz){
        String cmdName = clazz.getAnnotation(Command.class).name();
        String cmdDesc = clazz.getAnnotation(Command.class).description();
        return new CommandDataImpl(cmdName, cmdDesc).addOptions(createOptions(clazz));
    }

    /**
     * Create all options from annotation in class
     * @param clazz Class where options are
     * @return List of OptionData
     */
    private List<OptionData> createOptions(Class<?> clazz) {

        List<OptionData> options = new ArrayList<>();

        for (Option a : clazz.getAnnotationsByType(Option.class)) {
            OptionType optType = a.type();
            String optName = a.name();
            String optDesc = a.description();
            boolean optReq = a.required();
            options.add(new OptionData(optType, optName, optDesc, optReq));
        }
        return options;
    }

    /**
     * Link class with annotations with Discord listener
     * @param dbaClass Class who need link with listener
     * @return Instance of DBALoader
     */
    public DBALoader initDBAEvent(DBAEvent... dbaClass){
        for(DBAEvent event : dbaClass){
            Class<?> clazz = event.getClass();

            if(clazz.isAnnotationPresent(Button.class)) {
                String id = clazz.getAnnotation(Button.class).id();
                BUTTONS_MAP.put(id, (DBAButton) event);
            }else if(clazz.isAnnotationPresent(StringSelectMenu.class)) {
                String id = clazz.getAnnotation(StringSelectMenu.class).id();
                STRING_MENU_MAP.put(id, (DBAStringSelectMenu) event);
            }else if(clazz.isAnnotationPresent(EntitySelectMenu.class)){
                String id = clazz.getAnnotation(EntitySelectMenu.class).id();
                ENTITY_MENU_MAP.put(id, (DBAEntitySelectMenu) event);
            }else if(clazz.isAnnotationPresent(Modal.class)) {
                String id = clazz.getAnnotation(Modal.class).id();
                MODALS_MAP.put(id, (DBAModal) event);
            }else{
                throw new RuntimeException(new InvalidClassException("The class " + clazz.getName() + " is not a valid DBAEvent class"));
            }
        }
        return this;
    }


    /**
     * Get the map of commands
     * @return Map of commands
     */
    protected Map<String, DBACommand> getCOMMANDS_MAP() {
        return COMMANDS_MAP;
    }

    /**
     * Get the map of buttons
     * @return Map of buttons
     */
    protected Map<String, DBAButton> getBUTTONS_MAP() {
        return BUTTONS_MAP;
    }

    /**
     * Get the map of stringsSelectMenu
     * @return Map of stringSelectMenu
     */
    public Map<String, DBAStringSelectMenu> getSTRING_MENU_MAP() {
        return STRING_MENU_MAP;
    }

    /**
     * Get the map of entitySelectMenu
     * @return Map of entitySelectMenu
     */
    public Map<String, DBAEntitySelectMenu> getENTITY_MENU_MAP() {
        return ENTITY_MENU_MAP;
    }

    /**
     * Get the map of modals
     * @return Map of modals
     */
    protected Map<String, DBAModal> getMODALS_MAP() {
        return MODALS_MAP;
    }

    /**
     * Get the instance of DBALoader
     * @param jda JDA instance
     * @return DBALoader instance
     */
    @SuppressWarnings("unused")
    public static DBALoader getInstance(JDA jda) {
        if (instance == null) {
            instance = new DBALoader(jda);
        }
        return instance;
    }
}