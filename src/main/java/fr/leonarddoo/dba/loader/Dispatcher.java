package fr.leonarddoo.dba.loader;

import fr.leonarddoo.dba.element.*;
import fr.leonarddoo.dba.exception.UnreachableDBAEventException;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Dispatcher of the different event
 * @author Leonarddoo
 */
public class Dispatcher extends ListenerAdapter {

    /**
     * Instance of DBALoader
     */
    private final DBALoader loader;

    /**
     * Create a new Dispatcher
     * @param loader DBALoader instance
     */
    public Dispatcher(DBALoader loader) {
        this.loader = loader;
    }

    /**
     * Event for dispatch SlashCommandInteractionEvent
     */
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        DBACommand customCommand = null;
        for (String commandName : this.loader.COMMANDS_MAP.keySet()) {
            if(event.getName().equalsIgnoreCase(commandName)){
                customCommand = this.loader.COMMANDS_MAP.get(commandName);
                break;
            }
        }
        if(customCommand != null){
            customCommand.execute(event);
        }else{
            throw new RuntimeException(new UnreachableDBAEventException("Unable to find command with name: " + event.getName()));
        }
    }

    /**
     * Event for dispatch CommandAutoCompleteInteractionEvent
     */
    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        DBACommand customCommand = null;
        for (String commandName : this.loader.COMMANDS_MAP.keySet()) {
            if(event.getName().equalsIgnoreCase(commandName)){
                customCommand = this.loader.COMMANDS_MAP.get(commandName);
                break;
            }
        }
        if(customCommand != null){
            customCommand.autoComplete(event);
        }else{
            throw new RuntimeException(new UnreachableDBAEventException("Unable to find command with name: " + event.getName()));
        }
    }

    /**
     * Event for dispatch ButtonInteractionEvent
     */
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        DBAButton customButton = null;
        for (String buttonId : this.loader.BUTTONS_MAP.keySet()) {
            if(Objects.requireNonNull(event.getButton().getId()).startsWith(buttonId)){
                customButton = this.loader.BUTTONS_MAP.get(buttonId);
                break;
            }
        }
        if(customButton != null){
            customButton.execute(event);
        }else{
            throw new RuntimeException(new UnreachableDBAEventException("Unable to find button with ID: " + event.getButton().getId()));
        }
    }

    /**
     * Event for dispatch StringSelectMenuInteraction
     */
    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        DBAStringSelectMenu customSelectMenu = null;
        for(String selectMenuId : this.loader.STRING_MENU_MAP.keySet()) {
            if(Objects.requireNonNull(event.getSelectMenu().getId()).startsWith(selectMenuId)) {
                customSelectMenu = this.loader.STRING_MENU_MAP.get(selectMenuId);
                break;
            }
        }
        if(customSelectMenu != null){
            customSelectMenu.execute(event);
        }else{
            throw new RuntimeException(new UnreachableDBAEventException("Unable to find selectmenu with ID: " + event.getSelectMenu().getId()));
        }
    }

    /**
     * Event for dispatch EntitySelectMenuInteraction
     */
    @Override
    public void onEntitySelectInteraction(@NotNull EntitySelectInteractionEvent event) {
        DBAEntitySelectMenu customSelectMenu = null;
        for(String selectMenuId : this.loader.ENTITY_MENU_MAP.keySet()) {
            if(Objects.requireNonNull(event.getSelectMenu().getId()).startsWith(selectMenuId)) {
                customSelectMenu = this.loader.ENTITY_MENU_MAP.get(selectMenuId);
                break;
            }
        }
        if(customSelectMenu != null){
            customSelectMenu.execute(event);
        }else{
            throw new RuntimeException(new UnreachableDBAEventException("Unable to find selectmenu with ID: " + event.getSelectMenu().getId()));
        }

    }

    /**
     * Event for dispatch ModalInteractionEvent
     */
    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        DBAModal customModal = null;
        for (String modalId : this.loader.MODALS_MAP.keySet()) {
            if(event.getModalId().startsWith(modalId)){
                customModal = this.loader.MODALS_MAP.get(modalId);
                break;
            }
        }
        if(customModal != null) {
            customModal.execute(event);
        }else{
            throw new RuntimeException(new UnreachableDBAEventException("Unable to find modal with ID: " + event.getModalId()));
        }
    }
}
