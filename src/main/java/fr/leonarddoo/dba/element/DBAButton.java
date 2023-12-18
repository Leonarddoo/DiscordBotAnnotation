package fr.leonarddoo.dba.element;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public interface DBAButton extends DBAEvent {

    /**
     * Execute the button
     * @param event Event
     */
    @SuppressWarnings("unused")
    void execute(ButtonInteractionEvent event);
}
