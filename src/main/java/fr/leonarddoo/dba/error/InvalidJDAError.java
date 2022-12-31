package fr.leonarddoo.dba.error;

/**
 * Error when the JDA is invalid
 */
public class InvalidJDAError extends Error{

        /**
        * Constructor
        */
        public InvalidJDAError(String message) {
            super(message);
        }
}
