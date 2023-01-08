package fr.leonarddoo.dba.element;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

/**
 * @author Leonarddoo
 */
public interface CustomModal {

    /**.
     * Method called when the modal is closed
     */
    void execute(ModalInteractionEvent event);
}
