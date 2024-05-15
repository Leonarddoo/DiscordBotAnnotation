package fr.leonarddoo.dba.loader;

import fr.leonarddoo.dba.element.*;
import fr.leonarddoo.dba.exception.UnreachableDBAEventException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
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
        Map<String, DBACommand> commands = this.loader.COMMANDS_MAP.get(event.getJDA());
        if (commands != null) {
            DBACommand customCommand = commands.get(event.getName());
            if (customCommand != null) {
                customCommand.execute(event);
            } else {
                throw new RuntimeException(new UnreachableDBAEventException("Unable to find command with name: " + event.getName()));
            }
        } else {
            throw new RuntimeException(new UnreachableDBAEventException("Unable to find commands for JDA instance: " + event.getJDA()));
        }
    }

    /**
     * Event for dispatch CommandAutoCompleteInteractionEvent
     */
    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        Map<String, DBACommand> commands = this.loader.COMMANDS_MAP.get(event.getJDA());
        if (commands != null) {
            DBACommand customCommand = commands.get(event.getName());
            if (customCommand != null) {
                customCommand.autoComplete(event);
            } else {
                throw new RuntimeException(new UnreachableDBAEventException("Unable to find command with name: " + event.getName()));
            }
        } else {
            throw new RuntimeException(new UnreachableDBAEventException("Unable to find commands for JDA instance: " + event.getJDA()));
        }
    }

    /**
     * Event for dispatch ButtonInteractionEvent
     */
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        Map<String, DBAButton> buttons = this.loader.BUTTONS_MAP.get(event.getJDA());
        if (buttons != null) {
            DBAButton customButton = null;
            for (String buttonId : buttons.keySet()) {
                if (Objects.requireNonNull(event.getButton().getId()).startsWith(buttonId)) {
                    customButton = buttons.get(buttonId);
                    break;
                }
            }
            if (customButton != null) {
                customButton.execute(event);
            } else {
                throw new RuntimeException(new UnreachableDBAEventException("Unable to find button with ID: " + event.getButton().getId()));
            }
        } else {
            throw new RuntimeException(new UnreachableDBAEventException("Unable to find buttons for JDA instance: " + event.getJDA()));
        }
    }

    /**
     * Event for dispatch StringSelectMenuInteraction
     */
    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        Map<String, DBAStringSelectMenu> menus = this.loader.STRING_MENU_MAP.get(event.getJDA());
        if (menus != null) {
            DBAStringSelectMenu customSelectMenu = null;
            for (String selectMenuId : menus.keySet()) {
                if (Objects.requireNonNull(event.getSelectMenu().getId()).startsWith(selectMenuId)) {
                    customSelectMenu = menus.get(selectMenuId);
                    break;
                }
            }
            if (customSelectMenu != null) {
                customSelectMenu.execute(event);
            } else {
                throw new RuntimeException(new UnreachableDBAEventException("Unable to find select menu with ID: " + event.getSelectMenu().getId()));
            }
        } else {
            throw new RuntimeException(new UnreachableDBAEventException("Unable to find select menus for JDA instance: " + event.getJDA()));
        }
    }

    /**
     * Event for dispatch EntitySelectMenuInteraction
     */
    @Override
    public void onEntitySelectInteraction(@NotNull EntitySelectInteractionEvent event) {
        Map<String, DBAEntitySelectMenu> menus = this.loader.ENTITY_MENU_MAP.get(event.getJDA());
        if (menus != null) {
            DBAEntitySelectMenu customSelectMenu = null;
            for (String selectMenuId : menus.keySet()) {
                if (Objects.requireNonNull(event.getSelectMenu().getId()).startsWith(selectMenuId)) {
                    customSelectMenu = menus.get(selectMenuId);
                    break;
                }
            }
            if (customSelectMenu != null) {
                customSelectMenu.execute(event);
            } else {
                throw new RuntimeException(new UnreachableDBAEventException("Unable to find select menu with ID: " + event.getSelectMenu().getId()));
            }
        } else {
            throw new RuntimeException(new UnreachableDBAEventException("Unable to find select menus for JDA instance: " + event.getJDA()));
        }
    }

    /**
     * Event for dispatch ModalInteractionEvent
     */
    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        JDA jda = event.getJDA();
        Map<String, DBAModal> modals = this.loader.MODALS_MAP.get(jda);
        if (modals != null) {
            DBAModal customModal = null;
            for (String modalId : modals.keySet()) {
                if (event.getModalId().startsWith(modalId)) {
                    customModal = modals.get(modalId);
                    break;
                }
            }
            if (customModal != null) {
                customModal.execute(event);
            } else {
                throw new RuntimeException(new UnreachableDBAEventException("Unable to find modal with ID: " + event.getModalId()));
            }
        } else {
            throw new RuntimeException(new UnreachableDBAEventException("Unable to find modals for JDA instance: " + jda));
        }
    }
}
