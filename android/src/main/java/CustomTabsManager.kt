package codes.dreaming.plugin.CustomTabsManager

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent

class CustomTabsManager(private val activity: Activity) {
    fun openCustomTabSimple(url: String) {
        val intent = CustomTabsIntent.Builder().build()
        intent.launchUrl(activity, Uri.parse(url))
    }
}
