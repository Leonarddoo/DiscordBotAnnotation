package fr.leonarddoo.dba.loader;

import fr.leonarddoo.dba.annotation.SelectMenu;
import fr.leonarddoo.dba.element.CustomButton;
import fr.leonarddoo.dba.element.CustomCommand;
import fr.leonarddoo.dba.element.CustomModal;
import fr.leonarddoo.dba.element.CustomSelectMenu;
import fr.leonarddoo.dba.exception.InvalidCommandException;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.Modal;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * @author Leonarddoo
 * Dispatcher of the different event
 */
public class Dispatcher extends ListenerAdapter {

    /**
     * Event for dispatch SlashCommandInteractionEvent
     */
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        Class<?> commandClass = CommandLoader.COMMANDS_MAP.get(commandName);
        if(commandClass == null) return;
        if(Arrays.asList(commandClass.getInterfaces()).contains(CustomCommand.class)) {
            try {
                ((CustomCommand)commandClass.getConstructor().newInstance()).execute(event);
            } catch (NoSuchMethodException e){
                throw new InvalidCommandException(commandClass.getName() + " need a default constructor without parameters.");
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }else{
            throw new InvalidCommandException("You class must implement CustomCommand interface.");
        }
    }

    /**
     * Event for dispatch ButtonInteractionEvent
     */
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        String id = event.getButton().getId();
        Class<?> buttonClass = null;
        for(String key : ButtonLoader.BUTTONS_MAP.keySet()){
            assert id != null;
            if(key.startsWith(id)){
                buttonClass = ButtonLoader.BUTTONS_MAP.get(key);
            }
        }
        if(buttonClass == null) return;
        if(Arrays.asList(buttonClass.getInterfaces()).contains(CustomButton.class)) {
            try {
                ((CustomButton) buttonClass.getConstructor().newInstance()).execute(event);
            } catch (NoSuchMethodException e) {
                throw new InvalidCommandException(buttonClass.getName() + " need a default constructor without parameters.");
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }else{
            throw new InvalidCommandException("You class must implement CustomButton interface.");
        }
    }

    /**
     * Event for dispatch SelectMenuInteractionEvent
     */
    @Override
    public void onSelectMenuInteraction(@NotNull SelectMenuInteractionEvent event) {
        String id = event.getSelectMenu().getId();
        Class<?> selectMenuClass = null;
        for(String key : SelectMenuLoader.SELECT_MENU_MAP.keySet()){
            assert id != null;
            if(key.startsWith(id)){
                selectMenuClass = SelectMenuLoader.SELECT_MENU_MAP.get(key);
            }
        }
        if(selectMenuClass == null) return;
        if(Arrays.asList(selectMenuClass.getInterfaces()).contains(SelectMenu.class)) {
            try {
                ((CustomSelectMenu) selectMenuClass.getConstructor().newInstance()).execute(event);
            } catch (NoSuchMethodException e) {
                throw new InvalidCommandException(selectMenuClass.getName() + " need a default constructor without parameters.");
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }else{
            throw new InvalidCommandException("You class must implement CustomSelectMenu interface.");
        }
    }

    /**
     * Event for dispatch ModalInteractionEvent
     */
    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        String id = event.getModalId();
        Class<?> modalClass = null;
        for(String key : ModalLoader.MODAL_MAP.keySet()){
            if(key.startsWith(id)){
                modalClass = ModalLoader.MODAL_MAP.get(key);
            }
        }
        if(modalClass == null) return;
        if(Arrays.asList(modalClass.getInterfaces()).contains(Modal.class)) {
            try {
                ((CustomModal) modalClass.getConstructor().newInstance()).execute(event);
            } catch (NoSuchMethodException e) {
                throw new InvalidCommandException(modalClass.getName() + " need a default constructor without parameters.");
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }else{
            throw new InvalidCommandException("You class must implement CustomModal interface.");
        }
    }
}
