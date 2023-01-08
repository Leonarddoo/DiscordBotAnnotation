package fr.leonarddoo.dba.element;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

/**
 * @author Leonarddoo
 */
public interface CustomButton extends EventImplementation{

    /**
     * Method called when the button is clicked
     */
    void execute(ButtonInteractionEvent event);
}
