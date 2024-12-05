package io.thinker.plugins.nlbsoftpos;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import com.google.gson.Gson;

import java.util.*;

import io.thinker.plugins.nlbsoftpos.requests.PurchaseRequest;
import io.thinker.plugins.nlbsoftpos.requests.VoidRequest;
import io.thinker.plugins.nlbsoftpos.utils.ValidationResult;

@CapacitorPlugin(name = "ThinkerNlbSoftPos")
public class ThinkerNlbSoftPosPlugin extends Plugin {

  private ThinkerNlbSoftPos implementation = new ThinkerNlbSoftPos();

  @Override
  protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
    super.handleOnActivityResult(requestCode, resultCode, data);

    // Check if this requestCode matches what you sent
    if (requestCode == 1234) {
      PluginCall savedCall = getSavedCall();
      if (savedCall == null) {
        return;
      }

      if (resultCode == android.app.Activity.RESULT_OK) {
        // Process the result
        String resultData = data.getStringExtra("resultKey");
        savedCall.resolve(new JSObject().put("result", resultData));
      } else {
        savedCall.reject("Activity result failed");
      }
    }
  }

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
      call.reject();
    }

    // OPEN NLB APP
    call.resolve();
  }

//  @PluginMethod
//  public void MakeVoidTransaction(PluginCall call) {
//    VoidRequest request = null;
//
//    try {
//      request = VoidRequest.mapFromPluginCall(call);
//    } catch (Exception e) {
//      call.reject();
//      return;
//    }
//
//    if(request == null) {
//      call.reject();
//      return;
//    }
//
//    request.prefillData();
//    ValidationResult validationResult = request.validateRequestData();
//
//    if (!validationResult.getIsSuccessful()) {
//      call.reject();
//    }
//
//    // OPEN NLB APP
//  }
}
