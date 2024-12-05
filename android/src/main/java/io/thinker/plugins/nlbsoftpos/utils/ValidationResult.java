package io.thinker.plugins.nlbsoftpos.utils;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

    private boolean isSuccessful;
    private List<String> errorMessages = new ArrayList<String>();

    public boolean getIsSuccessful() {
      return this.isSuccessful;
    }

    public void setIsSuccessful(boolean isSuccessful) {
       this.isSuccessful = isSuccessful;
    }

    public List<String> getErrorMessages() {
       return this.errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
       this.errorMessages = errorMessages;
    }
}
