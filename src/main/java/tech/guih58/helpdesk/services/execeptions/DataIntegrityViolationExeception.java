package tech.guih58.helpdesk.services.execeptions;

public class DataIntegrityViolationExeception extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataIntegrityViolationExeception(String message) {
        super(message);
    }

    public DataIntegrityViolationExeception(String message, Throwable cause) {
        super(message, cause);
    }
}
