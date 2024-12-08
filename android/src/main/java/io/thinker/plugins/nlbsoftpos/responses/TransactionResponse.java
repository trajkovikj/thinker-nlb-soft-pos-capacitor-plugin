package io.thinker.plugins.nlbsoftpos.responses;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TransactionResponse {
    private String status;
    private int statusCode;

    private TransactionResult result;
    private List<ValidationError> validationErrors;

    public TransactionResponse(String status, int statusCode) {
        this.status = status;
        this.statusCode = statusCode;
    }

    public JSObject getJsonResponse() {
        JSObject response = new JSObject();
        response.put("status", this.status);
        response.put("statusCode", this.statusCode);

        if (this.result != null) {
            JSObject result = new JSObject();

            if (this.result.getStatus() != null) {
                JSObject status = new JSObject();
                status.put("code", this.result.getStatus().getCode());
                status.put("message", this.result.getStatus().getMessage());
                status.put("receiptData", this.result.getStatus().getReceiptData());

                result.put("status", status);
            }

            result.put("paymentIdentificator", this.result.getPaymentIdentificator());

            response.put("result", result);
        }

        if (this.validationErrors != null && !this.validationErrors.isEmpty()) {
            JSArray validationErrorsJsonArray = new JSArray();

            for (ValidationError validationError : this.validationErrors) {
                JSObject errorObject = new JSObject();
                errorObject.put("message", validationError.getMessage());
                errorObject.put("errorCode", validationError.getErrorCode());
                validationErrorsJsonArray.put(errorObject);
            }

            response.put("validationErrors", validationErrorsJsonArray);
        }

        return response;
    }

    public static TransactionResponse mapFromNlbResponse(JSObject nlbResponseWrapper) {
        JSObject response = nlbResponseWrapper.getJSObject("response");
        TransactionResultStatus responseStatus = new TransactionResultStatus();
        String paymentIdentificator = "";

        if (response != null) {
            JSObject status = response.getJSObject("status");
            if (status != null) {
                responseStatus.setCode(status.getString("code"));
                responseStatus.setMessage(status.getString("message"));
                responseStatus.setReceiptData(status.getString("receiptData"));
            }

            paymentIdentificator = response.getString("paymentIdentificator");
        }

        TransactionResult transactionResult = new TransactionResult();
        transactionResult.setStatus(responseStatus);
        transactionResult.setPaymentIdentificator(paymentIdentificator);

        TransactionResponseStatus status = TransactionResponseStatusFactory.getStatusByKey("NLB_STATUS_CODE_NOT_MAPPED");
        if (responseStatus.getCode() != null && !responseStatus.getCode().trim().isEmpty()) {
            status = TransactionResponseStatusFactory.getStatusByNlbCode(responseStatus.getCode());
        }

        TransactionResponse transactionResponse = new TransactionResponse(status.getStatus(), status.getStatusCode());
        transactionResponse.setResult(transactionResult);

        return transactionResponse;
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

    public TransactionResult getResult() {
        return result;
    }

    public void setResult(TransactionResult result) {
        this.result = result;
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    // endregion
}