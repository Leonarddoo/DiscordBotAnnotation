package fr.leonarddoo.dba.element;

import net.dv8tion.jda.api.events.Event;

/**
 * Interface for event implementations
 * @author Leonarddoo
 */
public interface EventImplementation {

    /**
     * Method called when Discord event are called
     * @param event Event
     */
    void execute(Event event);
}
