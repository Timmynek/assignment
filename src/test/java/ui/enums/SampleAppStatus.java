package ui.enums;

public enum SampleAppStatus {

    LOGGED_OUT("User logged out."),
    INVALID_CREDENTIALS("Invalid username/password"),
    WELCOME("Welcome, %s!");

    private final String message;

    SampleAppStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessage(String username) {
        return String.format(message, username);
    }
}
