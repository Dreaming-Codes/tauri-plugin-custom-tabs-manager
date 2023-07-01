package codes.dreaming.plugin.CustomTabsManager

import android.app.Activity
import app.tauri.annotation.Command
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.JSObject
import app.tauri.plugin.Plugin
import app.tauri.plugin.Invoke

@TauriPlugin
class CustomTabsManagerPlugin(private val activity: Activity): Plugin(activity) {
    private val implementation = CustomTabsManager(activity)

    @Command
    fun openCustomTabSimple(invoke: Invoke) {
        val url = invoke.getString("url")!!
        implementation.openCustomTabSimple(url)
        invoke.resolve()
    }
}
