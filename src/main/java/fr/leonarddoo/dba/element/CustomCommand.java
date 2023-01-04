package fr.leonarddoo.dba.element;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;


public interface CustomCommand {

    /**
     * Method called when the command is executed
     * @param event SlashCommandInteractionEvent
     */
    @SuppressWarnings("unused")
    void execute(SlashCommandInteractionEvent event);
}
