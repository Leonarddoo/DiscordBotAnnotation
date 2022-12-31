package fr.leonarddoo.dba.exception;

/**
 * Exception when the command is invalid (name or description)
 */
public class InvalidCommandException extends RuntimeException{

    /**
     * Constructor
     */
    public InvalidCommandException(String message) {
        super(message);
    }
}
