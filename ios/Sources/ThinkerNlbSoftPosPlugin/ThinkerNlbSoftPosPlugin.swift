import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(ThinkerNlbSoftPosPlugin)
public class ThinkerNlbSoftPosPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "ThinkerNlbSoftPosPlugin"
    public let jsName = "ThinkerNlbSoftPos"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "echo", returnType: CAPPluginReturnPromise)
    ]
    private let implementation = ThinkerNlbSoftPos()

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
}
