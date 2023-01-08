package fr.leonarddoo.dba.loader;

import fr.leonarddoo.dba.annotation.*;
import fr.leonarddoo.dba.element.*;
import fr.leonarddoo.dba.exception.InvalidClassException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to load the commands and events
 * @author Leonarddoo
 */
public class Loader {

    /**
     * Map of all commands with their class
     */
    protected static final Map<String, CustomCommand> COMMANDS_MAP = new HashMap<>();
    /**
     * Map of all buttons with their class
     */
    protected static final Map<String, CustomButton> BUTTONS_MAP = new HashMap<>();
    /**
     * Map of all select menus with their class
     */
    protected static final Map<String, CustomSelectMenu> SELECT_MENU_MAP = new HashMap<>();
    /**
     * Map of all modals with their class
     */
    protected static final Map<String, CustomModal> MODAL_MAP = new HashMap<>();


    /**
     * Load all elements with annotation in classes. Load commands in all guilds.
     * @param jda     JDA instance
     * @param classes List of classes where commands are
     */
    @SuppressWarnings("unused")
    public void load(JDA jda, EventImplementation... classes){
        jda.updateCommands().addCommands(test(classes)).queue();
        jda.addEventListener(new Dispatcher());
    }

    /**
     * Load all elements with annotation in classes. Load commands in a guild
     * @param guild   Guild where commands will be loaded
     * @param classes List of classes where commands are
     */
    @SuppressWarnings("unused")
    public void load(Guild guild, EventImplementation... classes) {
        guild.updateCommands().addCommands(test(classes)).queue();
        guild.getJDA().addEventListener(new Dispatcher());
    }

    /**
     * Load all elements with annotation in classes.
     * @param classes List of classes where have a custom element
     * @return List of SlashCommandData
     */
    private List<SlashCommandData> test(EventImplementation... classes){
        List<SlashCommandData> commands = new ArrayList<>();
        for (EventImplementation eventImplementation : classes) {
            Class<?> clazz = eventImplementation.getClass();
            if(clazz.isAnnotationPresent(Command.class)) {

                String cmdName = clazz.getAnnotation(Command.class).name();
                String cmdDesc = clazz.getAnnotation(Command.class).description();

                commands.add(new CommandDataImpl(cmdName, cmdDesc).addOptions(createOptions(clazz)));
                COMMANDS_MAP.put(cmdName, (CustomCommand) eventImplementation);
            }

            else if(clazz.isAnnotationPresent(Button.class)){
                String id = clazz.getAnnotation(Button.class).id();
                BUTTONS_MAP.put(id, (CustomButton) eventImplementation);
            }

            else if(clazz.isAnnotationPresent(SelectMenu.class)){
                String id = clazz.getAnnotation(SelectMenu.class).id();
                SELECT_MENU_MAP.put(id, (CustomSelectMenu) eventImplementation);
            }

            else if(clazz.isAnnotationPresent(Modal.class)){
                String id = clazz.getAnnotation(Modal.class).id();
                MODAL_MAP.put(id, (CustomModal) eventImplementation);
            }

            else {
                throw new InvalidClassException("Class " + clazz.getName() + " didn't have any annotation.");
            }
        }
        return commands;
    }

    /**
     * Create all options with annotation in class
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
}