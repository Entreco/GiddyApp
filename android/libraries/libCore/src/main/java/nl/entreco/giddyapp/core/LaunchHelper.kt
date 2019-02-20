package nl.entreco.giddyapp.core

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityCompat

object LaunchHelper {

    private const val URL_BASE = "https://giddy.entreco.nl"
    private const val URL_MAIN = "$URL_BASE/launch/"
    private const val URL_VIEWER = "$URL_BASE/viewer/"
    private const val URL_CREATOR = "$URL_BASE/creator/"

    fun launchMain(activity: Activity, options: ActivityOptions? = null) {
        val main = mainIntent(activity)
        launch(main, options, activity)
    }

    fun launchViewer(activity: Activity, options: ActivityOptions? = null, id: String? = null) {
        val viewer = viewerIntent(activity, id)
        launch(viewer, options, activity)
    }

    fun launchCreator(activity: Activity, options: ActivityOptions? = null) {
        val creator = creatorIntent(activity)
        launch(creator, options, activity)
    }

    private fun mainIntent(context: Context? = null) = baseIntent(URL_MAIN, context)
    private fun viewerIntent(context: Context? = null, id: String? = null) = baseIntent(URL_VIEWER + id ?: "", context)
    private fun creatorIntent(context: Context? = null) = baseIntent(URL_CREATOR, context)

    private fun baseIntent(url: String, context: Context? = null): Intent {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            .addCategory(Intent.CATEGORY_DEFAULT)
            .addCategory(Intent.CATEGORY_BROWSABLE)
        if (context != null) {
            intent.`package` = context.packageName
        }
        return intent
    }

    private fun launch(intent: Intent, options: ActivityOptions?, activity: Activity) {
        if (options == null) {
            activity.startActivity(intent)
        } else {
            ActivityCompat.startActivity(activity, intent, options.toBundle())
        }
    }
}