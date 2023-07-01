import UIKit
import WebKit
import Tauri
import SwiftRs

class CustomTabsManagerPlugin: Plugin {
	@objc public func ping(_ invoke: Invoke) throws {
		let value = invoke.getString("value")
		invoke.resolve(["value": value as Any])
	}
}

@_cdecl("init_plugin_custom_tabs_manager")
func initPlugin() -> Plugin {
	return CustomTabsManagerPlugin()
}
