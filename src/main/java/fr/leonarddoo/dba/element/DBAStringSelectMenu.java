package fr.leonarddoo.dba.element;

import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

public interface DBAStringSelectMenu extends DBAEvent {

    /**
     * Execute the StringSelectMenu event
     * @param event Event
     */
    @SuppressWarnings("unused")
    void execute(StringSelectInteractionEvent event);
}
