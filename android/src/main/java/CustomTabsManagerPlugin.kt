package codes.dreaming.plugin.CustomTabsManager

import android.app.Activity
import app.tauri.annotation.Command
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.JSObject
import app.tauri.plugin.Plugin
import app.tauri.plugin.Invoke

@TauriPlugin
class CustomTabsManagerPlugin(private val activity: Activity): Plugin(activity) {
    private val implementation = CustomTabsManager()

    @Command
    fun ping(invoke: Invoke) {
        val value = invoke.getString("value") ?: ""
        val ret = JSObject()
        ret.put("value", implementation.pong(value))
        invoke.resolve(ret)
    }
}
