package fr.leonarddoo.dba.element;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

public interface CustomModal {

    /**
     * Method called when the modal is executed
     */
    void execute(ModalInteractionEvent event);
}
