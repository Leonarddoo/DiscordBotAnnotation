package fr.leonarddoo.dba.exception;

/**
 * Thrown when a class is not valid
 * @author Leonarddoo
 */
public class InvalidClassException extends RuntimeException {

    /**
     * Constructor
     */
    public InvalidClassException(String message) {
        super(message);
    }
}
