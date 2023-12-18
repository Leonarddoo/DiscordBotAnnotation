package fr.leonarddoo.dba.element;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface DBACommand extends DBAEvent {

    /**
     * Execute the command
     * @param event Event
     */
    @SuppressWarnings("unused")
    void execute(SlashCommandInteractionEvent event);

    /**
     * Auto complete the command
     * @param event Event
     */
    default void autoComplete(CommandAutoCompleteInteractionEvent event) {}

}
