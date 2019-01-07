package nl.entreco.giddyapp.core

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityCompat

object LaunchHelper {

    private const val URL_BASE = "https://giddy.entreco.nl"
    private const val URL_SIGNIN = "$URL_BASE/signin"
    private const val URL_VIEWER = "$URL_BASE/viewer/MN3XJRk5anrEbAWWyEXY"

    fun launchViewer(activity: Activity, options: ActivityOptions? = null) {
        val viewer = viewerIntent(activity)
        if (options == null) {
            activity.startActivity(viewer)
        } else {
            ActivityCompat.startActivity(activity, viewer, options.toBundle())
        }
    }

    private fun viewerIntent(context: Context? = null) = baseIntent(URL_VIEWER, context)

    private fun baseIntent(url: String, context: Context? = null): Intent {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            .addCategory(Intent.CATEGORY_DEFAULT)
            .addCategory(Intent.CATEGORY_BROWSABLE)
        if (context != null) {
            intent.`package` = context.packageName
        }
        return intent
    }
}