package fr.leonarddoo.dba.element;

import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;

public interface CustomSelectMenu {

    /**
     * Method called when the select menu is executed
     */
    void execute(SelectMenuInteractionEvent event);
}
