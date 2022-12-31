package fr.leonarddoo.dba;

import fr.leonarddoo.dba.exception.InvalidCommandException;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;

public class Dispatcher extends ListenerAdapter {

    protected HashMap<String, Class<?>> commands = new HashMap<>();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        Class<?> commandClass = commands.get(commandName);

        if(Arrays.asList(commandClass.getInterfaces()).contains(CustomCommand.class)) {
            try {
                ((CustomCommand)commandClass.getConstructor().newInstance()).execute(event);
            } catch (NoSuchMethodException e){
                throw new InvalidCommandException(commandClass.getName() + " need a default constructor without parameters  .");
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }else{
            throw new InvalidCommandException("You class must implement CustomCommand interface.");
        }
    }
}
