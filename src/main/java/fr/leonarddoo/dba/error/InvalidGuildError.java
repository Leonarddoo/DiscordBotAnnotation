package fr.leonarddoo.dba.error;

/**
 * Error when the guild is invalid
 */
public class InvalidGuildError extends Error{

    /**
     * Constructor
     */
    public InvalidGuildError(String message) {
        super(message);
    }
}
