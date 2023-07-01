package codes.dreaming.plugin.CustomTabsManager

import android.app.Activity
import android.content.ComponentName
import android.net.Uri
import androidx.browser.customtabs.CustomTabsCallback
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsServiceConnection
import androidx.browser.customtabs.CustomTabsSession

class CustomTabsManager(private val activity: Activity) {
    private var customTabsClient: CustomTabsClient? = null
    private var customTabsSession: CustomTabsSession? = null
    private var connection: CustomTabsServiceConnection = object : CustomTabsServiceConnection() {
        override fun onCustomTabsServiceConnected(name: ComponentName, client: CustomTabsClient) {
            customTabsClient = client
            customTabsClient?.let {
                it.warmup(0L)
                customTabsSession = it.newSession(null)
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            customTabsClient = null
        }
    }

    init {
        CustomTabsClient.bindCustomTabsService(activity, "com.android.chrome", connection)
    }

    fun openCustomTabSimple(url: String) {
        val builder = CustomTabsIntent.Builder(customTabsSession)
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(activity, Uri.parse(url))
    }
}