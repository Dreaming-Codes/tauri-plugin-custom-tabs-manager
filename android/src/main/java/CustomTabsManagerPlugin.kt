package codes.dreaming.plugin.CustomTabsManager

import android.app.Activity
import android.net.Uri
import app.tauri.annotation.Command
import app.tauri.annotation.InvokeArg
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.Plugin
import app.tauri.plugin.Invoke

@InvokeArg
class OpenCustomTabSimpleArgs {
    var tryNativeApp: Boolean = true
    lateinit var url: String
}

@InvokeArg
class MayLaunchUrlArgs {
    lateinit var url: String
}

@InvokeArg
class PostMessageArgs {
    lateinit var message: String
}

@TauriPlugin
class CustomTabsManagerPlugin(private val activity: Activity) : Plugin(activity) {
    private val implementation = CustomTabsManager(activity)

    @Command
    fun openCustomTabSimple(invoke: Invoke) {
        val args = invoke.parseArgs(OpenCustomTabSimpleArgs::class.java)

        val uri = Uri.parse(args.url!!)
        if (args.tryNativeApp && implementation.openNativeApp(uri)) {
            invoke.resolve()
            return
        }

        implementation.openCustomTabSimple(uri)
        invoke.resolve()
    }

    @Command
    fun mayLaunchUrl(invoke: Invoke) {
        val args = invoke.parseArgs(MayLaunchUrlArgs::class.java)

        val uri = Uri.parse(args.url!!)
        implementation.mayLaunchUrl(uri)
        invoke.resolve()
    }

    @Command
    fun postMessage(invoke: Invoke) {
        val args = invoke.parseArgs(PostMessageArgs::class.java)

        implementation.postMessage(args.message!!)
        invoke.resolve()
    }
}
