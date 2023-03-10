package fr.leonarddoo.dba.loader;

import fr.leonarddoo.dba.element.*;
import fr.leonarddoo.dba.exception.InvalidClassException;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * Dispatcher of the different event
 * @author Leonarddoo
 */
public class Dispatcher extends ListenerAdapter {

    /**
     * Event for dispatch SlashCommandInteractionEvent
     */
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        CustomCommand customCommand= Loader.COMMANDS_MAP.get(commandName);
        if(customCommand != null){
            customCommand.execute(event);
        }else{
            throw new InvalidClassException("You class must implement CustomCommand interface.");
        }
    }

    /**
     * Event for dispatch ButtonInteractionEvent
     */
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        String id = event.getButton().getId();
        CustomButton customButton = Loader.BUTTONS_MAP.get(id);
        if(customButton != null) {
            customButton.execute(event);
        }else{
            throw new InvalidClassException("You class must implement CustomButton interface.");
        }
    }

    /**
     * Event for dispatch SelectMenuInteractionEvent
     */
    @Override
    public void onSelectMenuInteraction(@NotNull SelectMenuInteractionEvent event) {
        String id = event.getSelectMenu().getId();
        CustomSelectMenu customSelectMenu = Loader.SELECT_MENU_MAP.get(id);
        if(customSelectMenu != null) {
            customSelectMenu.execute(event);
        }else{
            throw new InvalidClassException("You class must implement CustomSelectMenu interface.");
        }
    }

    /**
     * Event for dispatch ModalInteractionEvent
     */
    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        String id = event.getModalId();
        CustomModal customModal = Loader.MODAL_MAP.get(id);
        if(customModal != null) {
            customModal.execute(event);
        }else{
            throw new InvalidClassException("You class must implement CustomModal interface.");
        }
    }
}
