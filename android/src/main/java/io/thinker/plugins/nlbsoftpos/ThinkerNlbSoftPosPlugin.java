package io.thinker.plugins.nlbsoftpos;

import android.app.Activity;
import android.content.Intent;

import androidx.activity.result.ActivityResult;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import io.thinker.plugins.nlbsoftpos.requests.PurchaseRequest;
import io.thinker.plugins.nlbsoftpos.requests.VoidRequest;
import io.thinker.plugins.nlbsoftpos.responses.TransactionResponse;
import io.thinker.plugins.nlbsoftpos.responses.TransactionResponseStatus;
import io.thinker.plugins.nlbsoftpos.responses.TransactionResponseStatusFactory;
import io.thinker.plugins.nlbsoftpos.responses.ValidationError;
import io.thinker.plugins.nlbsoftpos.responses.ValidationErrorFactory;
import io.thinker.plugins.nlbsoftpos.responses.ValidationResult;

@CapacitorPlugin(name = "ThinkerNlbSoftPos")
public class ThinkerNlbSoftPosPlugin extends Plugin {

    private ThinkerNlbSoftPos implementation = new ThinkerNlbSoftPos();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void initiatePurchaseTransaction(PluginCall call) {
        PurchaseRequest request = null;

        try {
            request = PurchaseRequest.mapFromPluginCall(call);
        } catch (Exception e) {
            TransactionResponseStatus validationErrorStatus = TransactionResponseStatusFactory.getStatusByKey("REQUEST_DATA_MALFORMED");
            TransactionResponse response = new TransactionResponse(
                    validationErrorStatus.getStatus(),
                    validationErrorStatus.getStatusCode());

            ValidationError validationError = ValidationErrorFactory.getValidationErrorByCode(3010);
            List<ValidationError> validationErrors = new ArrayList<>();
            validationErrors.add(validationError);
            response.setValidationErrors(validationErrors);

            call.resolve(response.getJsonResponse());
            return;
        }

        request.prefillData();
        ValidationResult validationResult = request.validateRequestData();

        if (!validationResult.getIsSuccessful()) {
            TransactionResponseStatus validationErrorStatus = TransactionResponseStatusFactory.getStatusByKey("VALIDATION_ERROR");
            TransactionResponse response = new TransactionResponse(
                    validationErrorStatus.getStatus(),
                    validationErrorStatus.getStatusCode());

            response.setValidationErrors(validationResult.getValidationErrors());

            call.resolve(response.getJsonResponse());
            return;
        }

        // open NLB soft pos app
        Intent openNlbSoftPosIntent = new Intent();
        openNlbSoftPosIntent.setClassName("com.payten.supercase", "com.payten.supercase.activities.SplashActivity");

        openNlbSoftPosIntent.putExtra("REQUEST_JSON_STRING", request.getJsonString());

        try {
            startActivityForResult(call, openNlbSoftPosIntent, "initiatePurchaseTransactionResult");
        } catch (Exception e) {
            TransactionResponseStatus validationErrorStatus = TransactionResponseStatusFactory.getStatusByKey("NLB_APP_START_FAILED");
            TransactionResponse response = new TransactionResponse(
                    validationErrorStatus.getStatus(),
                    validationErrorStatus.getStatusCode());

            response.setValidationErrors(validationResult.getValidationErrors());

            call.resolve(response.getJsonResponse());
        }
    }

    @ActivityCallback
    private void initiatePurchaseTransactionResult(PluginCall call, ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            if (data != null && data.getStringExtra("RESPONSE_JSON_STRING") != null) {
                String responseString = data.getStringExtra("RESPONSE_JSON_STRING");

                try {
                    JSObject response = new JSObject(responseString);
                    TransactionResponse transactionResponse = TransactionResponse.mapFromNlbResponse(response);
                    call.resolve(transactionResponse.getJsonResponse());
                } catch (JSONException e) {
                    TransactionResponse response = this.getResponseJsonParseErrorResponseStatus();
                    call.resolve(response.getJsonResponse());
                }
            } else {
                TransactionResponse response = this.getMissingResponseData();
                call.resolve(response.getJsonResponse());
            }
        } else {
            TransactionResponse response = this.getUnknownErrorResponseStatus();
            call.resolve(response.getJsonResponse());
        }
    }

    @PluginMethod
    public void initiateVoidTransaction(PluginCall call) {
        VoidRequest request = null;

        try {
            request = VoidRequest.mapFromPluginCall(call);
        } catch (Exception e) {
            TransactionResponseStatus validationErrorStatus = TransactionResponseStatusFactory.getStatusByKey("REQUEST_DATA_MALFORMED");
            TransactionResponse response = new TransactionResponse(
                    validationErrorStatus.getStatus(),
                    validationErrorStatus.getStatusCode());

            ValidationError validationError = ValidationErrorFactory.getValidationErrorByCode(3010);
            List<ValidationError> validationErrors = new ArrayList<>();
            validationErrors.add(validationError);
            response.setValidationErrors(validationErrors);

            call.resolve(response.getJsonResponse());
            return;
        }

        request.prefillData();
        ValidationResult validationResult = request.validateRequestData();

        if (!validationResult.getIsSuccessful()) {
            TransactionResponseStatus validationErrorStatus = TransactionResponseStatusFactory.getStatusByKey("VALIDATION_ERROR");
            TransactionResponse response = new TransactionResponse(
                    validationErrorStatus.getStatus(),
                    validationErrorStatus.getStatusCode());

            response.setValidationErrors(validationResult.getValidationErrors());

            call.resolve(response.getJsonResponse());
            return;
        }

        // open NLB soft pos app
        Intent openNlbSoftPosIntent = new Intent();
        openNlbSoftPosIntent.setClassName("com.payten.supercase", "com.payten.supercase.activities.SplashActivity");

        openNlbSoftPosIntent.putExtra("REQUEST_JSON_STRING", request.getJsonString());

        try {
            startActivityForResult(call, openNlbSoftPosIntent, "initiatePurchaseTransactionResult");
        } catch (Exception e) {
            TransactionResponseStatus validationErrorStatus = TransactionResponseStatusFactory.getStatusByKey("NLB_APP_START_FAILED");
            TransactionResponse response = new TransactionResponse(
                    validationErrorStatus.getStatus(),
                    validationErrorStatus.getStatusCode());

            response.setValidationErrors(validationResult.getValidationErrors());

            call.resolve(response.getJsonResponse());
        }
    }

    @ActivityCallback
    private void initiateVoidTransactionResult(PluginCall call, ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            if (data != null && data.getStringExtra("RESPONSE_JSON_STRING") != null) {
                String responseString = data.getStringExtra("RESPONSE_JSON_STRING");

                try {
                    JSObject response = new JSObject(responseString);
                    TransactionResponse transactionResponse = TransactionResponse.mapFromNlbResponse(response);
                    call.resolve(transactionResponse.getJsonResponse());
                } catch (JSONException e) {
                    TransactionResponse response = this.getResponseJsonParseErrorResponseStatus();
                    call.resolve(response.getJsonResponse());
                }
            } else {
                TransactionResponse response = this.getMissingResponseData();
                call.resolve(response.getJsonResponse());
            }
        } else {
            TransactionResponse response = this.getUnknownErrorResponseStatus();
            call.resolve(response.getJsonResponse());
        }
    }

    // region helper functions

    private TransactionResponse getUnknownErrorResponseStatus() {
        TransactionResponseStatus validationErrorStatus = TransactionResponseStatusFactory.getStatusByKey("UNKNOWN_ERROR");
        return new TransactionResponse(
                validationErrorStatus.getStatus(),
                validationErrorStatus.getStatusCode());
    }

    private TransactionResponse getMissingResponseData() {
        TransactionResponseStatus validationErrorStatus = TransactionResponseStatusFactory.getStatusByKey("MISSING_RESPONSE_DATA");
        return new TransactionResponse(
                validationErrorStatus.getStatus(),
                validationErrorStatus.getStatusCode());
    }

    private TransactionResponse getResponseJsonParseErrorResponseStatus() {
        TransactionResponseStatus validationErrorStatus = TransactionResponseStatusFactory.getStatusByKey("RESPONSE_JSON_PARSE_ERROR");
        return new TransactionResponse(
                validationErrorStatus.getStatus(),
                validationErrorStatus.getStatusCode());
    }

    // endregion


}
