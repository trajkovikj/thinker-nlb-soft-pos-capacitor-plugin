package io.thinker.plugins.nlbsoftpos;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

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
}
