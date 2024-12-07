package io.thinker.plugins.nlbsoftpos.responses;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    private boolean isSuccessful;
    private List<ValidationError> validationErrors = new ArrayList<ValidationError>();

    public boolean getIsSuccessful() {
      return this.isSuccessful;
    }

    public void setIsSuccessful(boolean isSuccessful) {
       this.isSuccessful = isSuccessful;
    }

    public List<ValidationError> getValidationErrors() {
       return this.validationErrors;
    }

    public void setValidationErrors(List<ValidationError> validationErrors) {
       this.validationErrors = validationErrors;
    }
}
