package io.thinker.plugins.nlbsoftpos.responses;

public class TransactionResultStatus {
    private String code; // status code - 00 = Executed | 05 = Rejected | 82 = Timeout | networkError = Network error
    private String message; // transaction message
    private String receiptData; // pin slip data


    // region getters & setters

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiptData() {
        return receiptData;
    }

    public void setReceiptData(String receiptData) {
        this.receiptData = receiptData;
    }

    // endregion
}
