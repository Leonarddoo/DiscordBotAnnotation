package fr.leonarddoo.dba.element;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

public interface DBAModal extends DBAEvent {

    /**
     * Execute the modal
     * @param event Event
     */
    @SuppressWarnings("unused")
    void execute(ModalInteractionEvent event);
}
