package fr.leonarddoo.dba.loader;

import fr.leonarddoo.dba.annotation.Command;
import fr.leonarddoo.dba.annotation.Option;
import fr.leonarddoo.dba.error.InvalidGuildError;
import fr.leonarddoo.dba.error.InvalidJDAError;
import fr.leonarddoo.dba.exception.InvalidCommandException;
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
 * @author Leonarddoo
 * Load all commands with annotation
 */
@SuppressWarnings("unused")
public class CommandLoader {

    /**
     * JDA instance
     */
    private final JDA jda;

    /**
     * Map of all commands with their class
     */
    protected static final Map<String, Class<?>> COMMANDS_MAP = new HashMap<>();

    public CommandLoader(JDA jda) {
        this.jda = jda;
        Dispatcher dispatcher = new Dispatcher();
        jda.addEventListener(dispatcher);
    }

    /**
     * Load all commands in a guild with annotation in classes
     * @param guild   Guild where commands will be loaded
     * @param classes List of classes where commands are
     */
    public void loadGuildCommands(Guild guild, Class<?>... classes) throws InvalidCommandException {

        List<SlashCommandData> commands = createCommands(classes);

        if (guild == null) {
            throw new InvalidGuildError("Guild cannot be find.");
        }
        guild.updateCommands().addCommands(commands).queue();
    }

    /**
     * Load all commands in a guild with annotation in classes
     * @param classes List of classes where commands are
     */
    public void loadCommands(Class<?>... classes) throws InvalidCommandException {

        List<SlashCommandData> commands = createCommands(classes);

        if (this.jda == null) {
            throw new InvalidJDAError("Guild cannot be find.");
        }
        jda.updateCommands().addCommands(commands).queue();
    }

    /**
     * Create all commands with annotation in classes
     * @param classes List of classes where commands are
     * @return List of SlashCommandData
     */
    private List<SlashCommandData> createCommands(Class<?>... classes) {
        List<SlashCommandData> commands = new ArrayList<>();

        for (Class<?> clazz : classes) {

            if(clazz.isAnnotationPresent(Command.class)) {

                String cmdName = null;
                String cmdDesc = null;

                for (Command cmd : clazz.getAnnotationsByType(Command.class)) {
                    cmdName = cmd.name();
                    cmdDesc = cmd.description();
                }
                if (cmdName == null || cmdDesc == null) {
                    throw new InvalidCommandException("Command name or description cannot be null.");
                }
                commands.add(new CommandDataImpl(cmdName, cmdDesc).addOptions(createOptions(clazz)));
                COMMANDS_MAP.put(cmdName, clazz);
            }else{
                throw new InvalidCommandException("Class " + clazz.getName() + " do not use @Command annotation.");
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