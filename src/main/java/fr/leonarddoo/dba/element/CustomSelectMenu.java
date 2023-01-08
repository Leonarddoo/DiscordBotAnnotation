package fr.leonarddoo.dba.element;

import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;

/**
 * @author Leonarddoo
 */
public interface CustomSelectMenu extends EventImplementation {

    /**
     * Method called when the select menu is used
     */
    void execute(SelectMenuInteractionEvent event);
}
