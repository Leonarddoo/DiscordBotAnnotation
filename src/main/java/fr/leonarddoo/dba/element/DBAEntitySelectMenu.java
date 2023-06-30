package fr.leonarddoo.dba.element;

import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;

public interface DBAEntitySelectMenu extends DBAEvent {

    /**
     * Execute the EntitySelectMenu event
     * @param event Event
     */
    @SuppressWarnings("unused")
    void execute(EntitySelectInteractionEvent event);

}
