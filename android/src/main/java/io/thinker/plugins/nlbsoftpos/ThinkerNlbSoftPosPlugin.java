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

import io.thinker.plugins.nlbsoftpos.requests.PurchaseRequest;
import io.thinker.plugins.nlbsoftpos.requests.VoidRequest;
import io.thinker.plugins.nlbsoftpos.responses.TransactionResponse;
import io.thinker.plugins.nlbsoftpos.responses.TransactionResponseStatus;
import io.thinker.plugins.nlbsoftpos.responses.TransactionResponseStatusFactory;
import io.thinker.plugins.nlbsoftpos.responses.ValidationResult;

@CapacitorPlugin(name = "ThinkerNlbSoftPos")
public class ThinkerNlbSoftPosPlugin extends Plugin {

    private ThinkerNlbSoftPos implementation = new ThinkerNlbSoftPos();

//    @Override
//    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
//        super.handleOnActivityResult(requestCode, resultCode, data);
//
//        // Check if this requestCode matches what you sent
//        if (requestCode == 1234) {
//            PluginCall savedCall = getSavedCall();
//            if (savedCall == null) {
//                return;
//            }
//
//            if (resultCode == android.app.Activity.RESULT_OK) {
//                // Process the result
//                String resultData = data.getStringExtra("resultKey");
//                savedCall.resolve(new JSObject().put("result", resultData));
//            } else {
//                savedCall.reject("Activity result failed");
//            }
//        }
//    }

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void MakePurchaseTransaction(PluginCall call) {
        PurchaseRequest request = null;

        try {
            request = PurchaseRequest.mapFromPluginCall(call);
        } catch (Exception e) {
            call.reject("Problem in getting the data from the request! Please check your request object!");
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

        // OPEN NLB APP
        Intent openNlbSoftPosIntent = new Intent();
        openNlbSoftPosIntent.setClassName("com.payten.supercase", "com.payten.supercase.activities.SplashActivity");

        openNlbSoftPosIntent.putExtra("REQUEST_JSON_STRING", request.getJsonString());

        // this is how it is set in the documentation
        // startActivityForResult(openNlbSoftPosIntent,1);

        // this is how capacitor specifies to be
        startActivityForResult(call, openNlbSoftPosIntent, "makePurchaseTransactionResult");

        // This is probably from old version of capacitor to save the call so you can get it in the next method
//        call.resolve();
//        void saveCall(PluginCall call)
//        PluginCall getSavedCall(String callbackId)
//        void releaseCall(PluginCall call)
//        void releaseCall(String callbackId)
    }

    @ActivityCallback
    private void makePurchaseTransactionResult(PluginCall call, ActivityResult result) {
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
    public void MakeVoidTransaction(PluginCall call) {
        VoidRequest request = null;

        try {
            request = VoidRequest.mapFromPluginCall(call);
        } catch (Exception e) {
            call.reject("Problem in getting the data from the request! Please check your request object!");
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

        // OPEN NLB APP
        Intent openNlbSoftPosIntent = new Intent();
        openNlbSoftPosIntent.setClassName("com.payten.supercase", "com.payten.supercase.activities.SplashActivity");

        openNlbSoftPosIntent.putExtra("REQUEST_JSON_STRING", request.getJsonString());

        startActivityForResult(call, openNlbSoftPosIntent, "makeVoidTransactionResult");
    }

    @ActivityCallback
    private void makeVoidTransactionResult(PluginCall call, ActivityResult result) {
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
