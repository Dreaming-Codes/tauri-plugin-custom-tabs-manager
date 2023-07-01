import UIKit
import WebKit
import Tauri
import SwiftRs

class CustomTabsManagerPlugin: Plugin {
	@objc public func openCustomTabSimple(_ invoke: Invoke) throws {
		let value = invoke.getString("url")
		invoke.resolve()
	}
}

@_cdecl("init_plugin_custom_tabs_manager")
func initPlugin() -> Plugin {
	return CustomTabsManagerPlugin()
}
