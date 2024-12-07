package io.thinker.plugins.nlbsoftpos.requests;

import io.thinker.plugins.nlbsoftpos.responses.ValidationError;
import io.thinker.plugins.nlbsoftpos.responses.ValidationErrorFactory;
import io.thinker.plugins.nlbsoftpos.responses.ValidationResult;

import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VoidRequest {

    public String pin; // optional | PIN code for logging into the IPS application | 4 character
    public String amount; // required | Transaction amount | Two decimals (XXX.YY)
    public String packageName; // required | The package name of the application sending the intent
    public String transactionType; // required | Defines type of the transaction (POS, IPS) | POS, IPS
    public String transactionClass; // required | Defines class of the transaction (purchase, void)
    public String authorizationCode; // required for void | Sent in void transaction, receipt data of original request contains this code
    public String merchantUniqueID; // required | Unique identifier of the merchant’s transaction


    public static VoidRequest mapFromPluginCall(PluginCall call) throws Exception {
        VoidRequest request = new VoidRequest();

        request.pin = call.getString("pin");
        request.amount = call.getString("amount");
        request.packageName = call.getString("packageName");
        request.transactionType = call.getString("transactionType");
        request.transactionClass = call.getString("transactionClass");
        request.authorizationCode = call.getString("authorizationCode");
        request.merchantUniqueID = call.getString("merchantUniqueID");

        return request;
    }

    public ValidationResult validateRequestData() {
        ValidationResult result = new ValidationResult();
        result.setIsSuccessful(true);
        result.setValidationErrors(new ArrayList<>());

        // validate pin
        if (pin != null && !pin.trim().isEmpty() && pin.length() != 4) {
            result.setIsSuccessful(false);
            ValidationError foundValidationError = ValidationErrorFactory.getValidationErrorByCode(3000);
            if(foundValidationError != null) result.getValidationErrors().add(foundValidationError);
        }

        // validate amount - required and must have at max 2 decimals
        if (amount == null || amount.trim().isEmpty()) {
            result.setIsSuccessful(false);
            ValidationError foundValidationError = ValidationErrorFactory.getValidationErrorByCode(3001);
            if(foundValidationError != null) result.getValidationErrors().add(foundValidationError);
        }

        if (amount != null && !amount.trim().isEmpty()) {
            String regex = "^\\d+(\\.\\d{2})?$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(amount);

            if (!matcher.matches()) {
                result.setIsSuccessful(false);
                ValidationError foundValidationError = ValidationErrorFactory.getValidationErrorByCode(3002);
                if(foundValidationError != null) result.getValidationErrors().add(foundValidationError);
            }
        }

        // validate packageName - required param
        if (packageName == null || packageName.trim().isEmpty()) {
            result.setIsSuccessful(false);
            ValidationError foundValidationError = ValidationErrorFactory.getValidationErrorByCode(3003);
            if(foundValidationError != null) result.getValidationErrors().add(foundValidationError);
        }

        // validate transactionType
        if (transactionType == null || transactionType.trim().isEmpty() || (!Objects.equals(transactionType, "POS") && !Objects.equals(transactionType, "IPS"))) {
            result.setIsSuccessful(false);
            ValidationError foundValidationError = ValidationErrorFactory.getValidationErrorByCode(3004);
            if(foundValidationError != null) result.getValidationErrors().add(foundValidationError);
        }

        // validate transactionClass
        if (transactionClass == null || transactionClass.trim().isEmpty() || (!Objects.equals(transactionClass, "purchase") && !Objects.equals(transactionClass, "void"))) {
            result.setIsSuccessful(false);
            ValidationError foundValidationError = ValidationErrorFactory.getValidationErrorByCode(3005);
            if(foundValidationError != null) result.getValidationErrors().add(foundValidationError);
        }

        // transactionClass must be void
        if (!Objects.equals(transactionClass, "void")) {
            result.setIsSuccessful(false);
            ValidationError foundValidationError = ValidationErrorFactory.getValidationErrorByCode(3007);
            if(foundValidationError != null) result.getValidationErrors().add(foundValidationError);
        }

        // validate authorizationCode
        if (Objects.equals(transactionClass, "void") && (authorizationCode == null || authorizationCode.trim().isEmpty())) {
            result.setIsSuccessful(false);
            ValidationError foundValidationError = ValidationErrorFactory.getValidationErrorByCode(3008);
            if(foundValidationError != null) result.getValidationErrors().add(foundValidationError);
        }

        // merchantUniqueID
        if (merchantUniqueID == null || merchantUniqueID.trim().isEmpty()) {
            result.setIsSuccessful(false);
            ValidationError foundValidationError = ValidationErrorFactory.getValidationErrorByCode(3009);
            if(foundValidationError != null) result.getValidationErrors().add(foundValidationError);
        }

        return result;
    }

    public void prefillData() {
        this.transactionClass = "void";
    }

    public String getJsonString() {
        JSObject requestObject = new JSObject();
        if (this.pin != null) {
            requestObject.put("pin", this.pin);
        }
        requestObject.put("amount", this.amount);
        requestObject.put("packageName", this.packageName);
        requestObject.put("transactionType", this.transactionType);
        requestObject.put("transactionClass", this.transactionClass);
        requestObject.put("authorizationCode", this.authorizationCode);
        requestObject.put("merchantUniqueID", this.merchantUniqueID);

        JSObject wrapperObject = new JSObject();
        wrapperObject.put("request", requestObject);

        return wrapperObject.toString();
    }
}
