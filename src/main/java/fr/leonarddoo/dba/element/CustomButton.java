package fr.leonarddoo.dba.element;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public interface CustomButton {

    /**
     * Method called when the button is clicked
     * @param event ButtonInteractionEvent
     */
    @SuppressWarnings("unused")
    void execute(ButtonInteractionEvent event);
}
