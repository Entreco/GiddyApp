package nl.entreco.giddyapp.libcore.launch

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.ShareCompat
import nl.entreco.giddyapp.libcore.R

object LaunchHelper {

    private const val URL_BASE = "https://giddy.entreco.nl"
    const val URL_VIEWER = "$URL_BASE/viewer/"
    const val URL_CREATOR = "$URL_BASE/creator/"
    const val URL_PROFILE = "$URL_BASE/profile/"

    fun launchViewer(activity: Activity, options: ActivityOptions? = null, id: String? = "launch") {
        val viewer = viewerIntent(activity, id)
        launch(viewer, options, activity)
    }

    fun launchCreator(activity: Activity, options: ActivityOptions? = null) {
        val creator = creatorIntent(activity)
        launch(creator, options, activity)
    }

    fun launchProfile(activity: Activity, options: ActivityOptions? = null) {
        val profile = profileIntent(activity)
        launch(profile, options, activity)
    }

    fun share(activity: Activity, horseId: String) {
        ShareCompat.IntentBuilder.from(activity)
            .setChooserTitle(R.string.share_chooser_title)
            .setText(activity.getString(R.string.share_content, URL_VIEWER, horseId))
            .setType("text/plain")
            .startChooser()
    }

    private fun viewerIntent(context: Context, id: String? = null) = baseIntent(
        URL_VIEWER + id,
        context
    )

    private fun creatorIntent(context: Context) = baseIntent(
        URL_CREATOR,
        context
    )

    private fun profileIntent(context: Context) = baseIntent(
        URL_PROFILE,
        context
    )

    private fun baseIntent(url: String, context: Context): Intent {
        return Intent(Intent.ACTION_VIEW, Uri.parse(url))
            .setPackage(context.packageName)
            .addCategory(Intent.CATEGORY_BROWSABLE)
    }

    private fun launch(intent: Intent, options: ActivityOptions?, activity: Activity) {
        if (options == null) {
            activity.startActivity(intent)
        } else {
            activity.startActivity(intent, options.toBundle())
        }
    }
}