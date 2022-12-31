package fr.leonarddoo.dba;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;


public interface CustomCommand {

    @SuppressWarnings("unused")
    void execute(SlashCommandInteractionEvent event);
}
