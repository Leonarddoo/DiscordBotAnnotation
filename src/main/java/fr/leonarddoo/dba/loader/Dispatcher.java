package fr.leonarddoo.dba.loader;

import fr.leonarddoo.dba.element.*;
import fr.leonarddoo.dba.exception.UnrechableDBAEventException;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * Dispatcher of the different event
 * @author Leonarddoo
 */
public class Dispatcher extends ListenerAdapter {

    private final DBALoader loader;

    public Dispatcher(DBALoader loader) {
        this.loader = loader;
    }

    /**
     * Event for dispatch SlashCommandInteractionEvent
     */
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        DBACommand customCommand = null;
        for (String commandName : this.loader.getCOMMANDS_MAP().keySet()) {
            if(event.getName().equalsIgnoreCase(commandName)){
                customCommand = this.loader.getCOMMANDS_MAP().get(commandName);
                break;
            }
        }
        if(customCommand != null){
            customCommand.execute(event);
        }else{
            throw new RuntimeException(new UnrechableDBAEventException("Unable to command with name: " + event.getName()));
        }
    }

    /**
     * Event for dispatch ButtonInteractionEvent
     */
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        DBAButton customButton = null;
        for (String buttonId : this.loader.getBUTTONS_MAP().keySet()) {
            if(event.getButton().getId().startsWith(buttonId)){
                customButton = this.loader.getBUTTONS_MAP().get(buttonId);
                break;
            }
        }
        if(customButton != null){
            customButton.execute(event);
        }else{
            throw new RuntimeException(new UnrechableDBAEventException("Unable to button with ID: " + event.getButton().getId()));
        }
    }

    /**
     * Event for dispatch StringSelectMenuInteraction
     */
    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        DBAStringSelectMenu customSelectMenu = null;
        for(String selectMenuId : this.loader.getSTRING_MENU_MAP().keySet()) {
            if(event.getSelectMenu().getId().startsWith(selectMenuId)) {
                customSelectMenu = this.loader.getSTRING_MENU_MAP().get(selectMenuId);
                break;
            }
        }
        if(customSelectMenu != null){
            customSelectMenu.execute(event);
        }else{
            throw new RuntimeException(new UnrechableDBAEventException("Unable to selectmenu with ID: " + event.getSelectMenu().getId()));
        }
    }

    /**
     * Event for dispatch EntitySelectMenuInteraction
     */
    @Override
    public void onEntitySelectInteraction(@NotNull EntitySelectInteractionEvent event) {
        DBAEntitySelectMenu customSelectMenu = null;
        for(String selectMenuId : this.loader.getENTITY_MENU_MAP().keySet()) {
            if(event.getSelectMenu().getId().startsWith(selectMenuId)) {
                customSelectMenu = this.loader.getENTITY_MENU_MAP().get(selectMenuId);
                break;
            }
        }
        if(customSelectMenu != null){
            customSelectMenu.execute(event);
        }else{
            throw new RuntimeException(new UnrechableDBAEventException("Unable to selectmenu with ID: " + event.getSelectMenu().getId()));
        }

    }

    /**
     * Event for dispatch ModalInteractionEvent
     */
    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        DBAModal customModal = null;
        for (String modalId : this.loader.getMODALS_MAP().keySet()) {
            if(event.getModalId().startsWith(modalId)){
                customModal = this.loader.getMODALS_MAP().get(modalId);
                break;
            }
        }
        if(customModal != null) {
            customModal.execute(event);
        }else{
            throw new RuntimeException(new UnrechableDBAEventException("Unable to modal with ID: " + event.getModalId()));
        }
    }
}
