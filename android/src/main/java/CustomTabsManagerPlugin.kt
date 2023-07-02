package codes.dreaming.plugin.CustomTabsManager

import android.app.Activity
import android.net.Uri
import app.tauri.annotation.Command
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.JSObject
import app.tauri.plugin.Plugin
import app.tauri.plugin.Invoke

@TauriPlugin
class CustomTabsManagerPlugin(private val activity: Activity) : Plugin(activity) {
    private val implementation = CustomTabsManager(activity)

    @Command
    fun openCustomTabSimple(invoke: Invoke) {
        val tryNativeApp = invoke.getBoolean("tryNativeApp")!!
        val url = invoke.getString("url")!!
        val uri = Uri.parse(url)
        if (tryNativeApp && implementation.openNativeApp(uri)) {
            invoke.resolve()
            return
        }

        implementation.openCustomTabSimple(uri)
        invoke.resolve()
    }
    
    @Command
    fun mayLaunchUrl(invoke: Invoke) {
        val url = invoke.getString("url")!!
        val uri = Uri.parse(url)
        implementation.mayLaunchUrl(uri)
        invoke.resolve()
    }
}
