package fr.leonarddoo.dba.element;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
 * @author Leonarddoo
 */
public interface CustomCommand extends EventImplementation {

    /**
     * Method called when the command is executed
     */
    void execute(SlashCommandInteractionEvent event);
}
