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
    protected final Map<JDA, Map<String, DBACommand>> COMMANDS_MAP;
    /**
     * Map of buttons
     */
    protected final Map<JDA, Map<String, DBAButton>> BUTTONS_MAP;
    /**
     * Map of stringSelectMenu
     */
    protected final Map<JDA, Map<String, DBAStringSelectMenu>> STRING_MENU_MAP;
    /**
     * Map of entitySelectMenu
     */
    protected final Map<JDA, Map<String, DBAEntitySelectMenu>> ENTITY_MENU_MAP;
    /**
     * Map of modals
     */
    protected final Map<JDA, Map<String, DBAModal>> MODALS_MAP;
    /**
     * Create a new DBALoader
     * @param jda JDA instance
     */
    private DBALoader(JDA jda) {
        this.jda = jda;
        this.COMMANDS_MAP = new HashMap<>();
        this.BUTTONS_MAP = new HashMap<>();
        this.STRING_MENU_MAP = new HashMap<>();
        this.ENTITY_MENU_MAP = new HashMap<>();
        this.MODALS_MAP = new HashMap<>();
        jda.addEventListener(new Dispatcher(this));
    }

    /**
     * Add commands to JDA
     * @param commandsClass Commands to add
     */
    @SuppressWarnings("unused")
    public DBALoader addDBACommandsToJDA(Object... commandsClass) {

        Map<String, DBACommand> DBACommands = new HashMap<>();
        List<CommandData> commands = new ArrayList<>();

        for (Object event : commandsClass) {

            Class<?> clazz = event.getClass();
            if (clazz.isAnnotationPresent(Command.class)) {
                DBACommands.put(clazz.getAnnotation(Command.class).name(), (DBACommand) event);
                commands.add(createCommand(clazz));
            } else {
                throw new RuntimeException(new InvalidClassException("The class " + clazz.getName() + " is not a valid DBACommand class"));
            }
        }

        this.COMMANDS_MAP.put(this.jda, DBACommands);
        this.jda.updateCommands().addCommands(commands).queue();

        return this;
    }

    /**
     * Add commands to a guild
     * @param guild Guild where commands will be added
     * @param commandsClass Commands to add
     */
    @SuppressWarnings("unused")
    public DBALoader addDBACommandsToGuild(Guild guild, Object... commandsClass) {

        Map<String, DBACommand> DBACommands = new HashMap<>();
        List<CommandData> commands = new ArrayList<>();

        for (Object event : commandsClass) {

            Class<?> clazz = event.getClass();
            if (clazz.isAnnotationPresent(Command.class)) {
                DBACommands.put(clazz.getAnnotation(Command.class).name(), (DBACommand) event);
                commands.add(createCommand(clazz));
            } else {
                throw new RuntimeException(new InvalidClassException("The class " + clazz.getName() + " is not a valid DBACommand class"));
            }
        }

        this.COMMANDS_MAP.put(this.jda, DBACommands);
        guild.updateCommands().addCommands(commands).queue();

        return this;
    }

    /**
     * Create a CommandData from annotation in class
     * @param clazz  Class where command is
     * @return CommandData
     */
    private CommandData createCommand(Class<?> clazz) {
        String cmdName = (clazz.getAnnotation(Command.class)).name();
        String cmdDesc = clazz.getAnnotation(Command.class).description();
        return (new CommandDataImpl(cmdName, cmdDesc)).addOptions(createOptions(clazz));
    }

    /**
     * Create all options from annotation in class
     * @param clazz Class where options are
     * @return List of OptionData
     */
    private List<OptionData> createOptions(Class<?> clazz) {
        List<OptionData> options = new ArrayList<>();
        for (Option option : clazz.getAnnotationsByType(Option.class)) {
            OptionType optType = option.type();
            String optName = option.name();
            String optDesc = option.description();
            boolean optReq = option.required();
            boolean optcomplete = option.autocomplete();
            List<net.dv8tion.jda.api.interactions.commands.Command.Choice> choices = new ArrayList<>();
            for (Choice choice : option.choices()) {
                String choiceName = choice.name();
                String choiceValue = choice.value();
                choices.add(new net.dv8tion.jda.api.interactions.commands.Command.Choice(choiceName, choiceValue));
            }
            options.add((new OptionData(optType, optName, optDesc, optReq, optcomplete)).addChoices(choices));
        }
        return options;
    }

    /**
     * Link class with annotations with Discord listener
     * @param dbaClass Class who need link with listener
     * @return Instance of DBALoader
     */
    @SuppressWarnings("unused")
    public DBALoader initDBAEvent(Object... dbaClass) {

        for (Object event : dbaClass) {

            Class<?> clazz = event.getClass();
            if (clazz.isAnnotationPresent(Button.class)) {

                String id = clazz.getAnnotation(Button.class).id();
                this.BUTTONS_MAP.put(this.jda, Map.of(id, (DBAButton)event));

            } else if (clazz.isAnnotationPresent(StringSelectMenu.class)) {

                String id = clazz.getAnnotation(StringSelectMenu.class).id();
                this.STRING_MENU_MAP.put(this.jda, Map.of(id, (DBAStringSelectMenu)event));

            } else if (clazz.isAnnotationPresent(EntitySelectMenu.class)) {

                String id = clazz.getAnnotation(EntitySelectMenu.class).id();
                this.ENTITY_MENU_MAP.put(this.jda, Map.of(id, (DBAEntitySelectMenu)event));

            } else if (clazz.isAnnotationPresent(Modal.class)) {

                String id = clazz.getAnnotation(Modal.class).id();
                this.MODALS_MAP.put(this.jda, Map.of(id, (DBAModal)event));

            } else {
                throw new RuntimeException(new InvalidClassException("The class " + clazz.getName() + " is not a valid DBAEvent class"));
            }
        }
        return this;
    }

    /**
     * Get the instance of DBALoader
     * @param jda JDA instance
     * @return DBALoader instance
     */
    @SuppressWarnings("unused")
    public static DBALoader getInstance(JDA jda) {
        if (instance == null)
            instance = new DBALoader(jda);
        return instance;
    }
}
