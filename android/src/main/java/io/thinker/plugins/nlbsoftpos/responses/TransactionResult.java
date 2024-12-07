package io.thinker.plugins.nlbsoftpos.responses;

public class TransactionResult {
    private TransactionResultStatus status; // Contains status code, transaction message and slip data
    private String paymentIdentificator; // Unique transaction reference/Approval code

    // region getters & setters

    public TransactionResultStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionResultStatus status) {
        this.status = status;
    }

    public String getPaymentIdentificator() {
        return paymentIdentificator;
    }

    public void setPaymentIdentificator(String paymentIdentificator) {
        this.paymentIdentificator = paymentIdentificator;
    }

    // endregion
}
