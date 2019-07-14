package nl.entreco.giddyapp.libcore.launch

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.app.ShareCompat

object LaunchHelper {

    private const val URL_BASE = "https://giddy.entreco.nl"
    const val URL_VIEWER = "$URL_BASE/viewer/"
    const val URL_CREATOR = "$URL_BASE/creator/"
    const val URL_PROFILE = "$URL_BASE/profile/"

    fun launchViewer(activity: Activity, options: ActivityOptionsCompat? = null, id: String? = null) {
        val viewer = viewerIntent(activity, id)
        launch(viewer, options, activity)
    }

    fun launchCreator(activity: Activity, options: ActivityOptionsCompat? = null) {
        val creator = creatorIntent(activity)
        launch(creator, options, activity)
    }

    fun launchProfile(activity: Activity, options: ActivityOptionsCompat? = null) {
        val profile = profileIntent(activity)
        launch(profile, options, activity)
    }

    fun share(activity: Activity, horseId: String) {
        ShareCompat.IntentBuilder.from(activity)
            .setChooserTitle("Share URL")
            .setText(URL_VIEWER + horseId)
            .setType("text/plain")
            .startChooser()
    }

    private fun viewerIntent(context: Context, id: String? = null) =
        baseIntent(
            URL_VIEWER + id,
            context
        )
    private fun creatorIntent(context: Context) = baseIntent(
        URL_CREATOR,
        context
    )

    private fun profileIntent(context: Context) = Intent("nl.entreco.profile.open").apply {
        `package` = context.packageName
    }

    private fun baseIntent(url: String, context: Context? = null): Intent {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            .addCategory(Intent.CATEGORY_DEFAULT)
            .addCategory(Intent.CATEGORY_BROWSABLE)
        if (context != null) {
            intent.`package` = context.packageName
        }
        return intent
    }

    private fun launch(intent: Intent, options: ActivityOptionsCompat?, activity: Activity) {
        if (options == null) {
            activity.startActivity(intent)
        } else {
            ActivityCompat.startActivity(activity, intent, options.toBundle())
        }
    }
}