package io.thinker.plugins.nlbsoftpos.responses;

public class TransactionResponseStatus {
    private String status;
    private int statusCode;

    public TransactionResponseStatus(String status, int statusCode) {
        this.status = status;
        this.statusCode = statusCode;
    }

    // region getters & setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    // endregion
}
