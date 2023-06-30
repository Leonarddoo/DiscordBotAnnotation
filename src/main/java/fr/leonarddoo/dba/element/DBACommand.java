package fr.leonarddoo.dba.element;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@SuppressWarnings("unused")
public interface DBACommand extends DBAEvent {

    /**
     * Execute the command
     * @param event Event
     */
    @SuppressWarnings("unused")
    void execute(SlashCommandInteractionEvent event);

}
