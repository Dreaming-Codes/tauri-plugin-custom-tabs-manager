package codes.dreaming.plugin.CustomTabsManager

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsServiceConnection
import androidx.browser.customtabs.CustomTabsSession
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Build

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
    
    fun mayLaunchUrl(uri: Uri) {
        customTabsSession?.mayLaunchUrl(uri, null, null)
    }

    fun postMessage(message: String) {
        customTabsSession?.postMessage(message, null)
    }

    fun openCustomTabSimple(uri: Uri) {
        val builder = CustomTabsIntent.Builder(customTabsSession)
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(activity, uri)
    }

    fun launchNativeApi30(context: Context, uri: Uri): Boolean {
        val nativeAppIntent = Intent(Intent.ACTION_VIEW, uri)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER
            )
        return try {
            context.startActivity(nativeAppIntent)
            true
        } catch (ex: ActivityNotFoundException) {
            false
        }
    }

    private fun extractPackageNames(resolveInfos: List<ResolveInfo>): MutableSet<String> {
        val packageNames = mutableSetOf<String>()
        for (resolveInfo in resolveInfos) {
            val packageName = resolveInfo.activityInfo.packageName
            packageNames.add(packageName)
        }
        return packageNames
    }

    private fun launchNativeBeforeApi30(context: android.content.Context, uri: android.net.Uri): kotlin.Boolean {
        var pm: PackageManager = context.getPackageManager()

        // Get all Apps that resolve a generic url
        var browserActivityIntent: Intent = Intent()
            .setAction(Intent.ACTION_VIEW)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .setData(android.net.Uri.fromParts("http", "", null))
        var genericResolvedList: kotlin.collections.MutableSet<kotlin.String> = extractPackageNames(
            pm.queryIntentActivities(browserActivityIntent, 0)
        )

        // Get all apps that resolve the specific Url
        var specializedActivityIntent: Intent = Intent(Intent.ACTION_VIEW, uri)
            .addCategory(Intent.CATEGORY_BROWSABLE)
        var resolvedSpecializedList: kotlin.collections.MutableSet<kotlin.String> = extractPackageNames(
            pm.queryIntentActivities(specializedActivityIntent, 0)
        )

        // Keep only the Urls that resolve the specific, but not the generic
        // urls.
        resolvedSpecializedList.removeAll(genericResolvedList)

        // If the list is empty, no native app handlers were found.
        if (resolvedSpecializedList.isEmpty()) {
            return false
        }

        // We found native handlers. Launch the Intent.
        specializedActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(specializedActivityIntent)
        return true
    }

    fun openNativeApp(uri: Uri): Boolean {
        val context = activity.applicationContext;
        return if (android.os.Build.VERSION.SDK_INT >= 30) launchNativeApi30(context, uri) else launchNativeBeforeApi30(
            context,
            uri
        )
    }
}