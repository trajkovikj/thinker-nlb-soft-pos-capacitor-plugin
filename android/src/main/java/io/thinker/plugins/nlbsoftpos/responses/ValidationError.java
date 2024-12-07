package io.thinker.plugins.nlbsoftpos.responses;

public class ValidationError {
    private String message;
    private int errorCode;

    public ValidationError() {

    }

    public ValidationError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.message = errorMessage;
    }

    // region getters & setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
    // endregion
}
