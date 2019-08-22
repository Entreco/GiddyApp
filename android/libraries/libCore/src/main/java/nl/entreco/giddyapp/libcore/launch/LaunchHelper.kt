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

    private const val PACKAGE_BASE = "nl.entreco.giddyapp"
    private const val CLASS_NAME_VIEWER = "$PACKAGE_BASE.viewer.ViewerActivity"
    private const val CLASS_NAME_CREATOR = "$PACKAGE_BASE.creator.CreatorActivity"
    private const val CLASS_NAME_PROFILE = "$PACKAGE_BASE.profile.ProfileActivity"

    fun launchViewer(activity: Activity, options: ActivityOptions? = null, id: String? = "launch") {
        viewerIntent(activity)?.let { intent ->
            intent.putExtra("id", id)
            launch(intent, options, activity)
        }
    }

    fun launchCreator(activity: Activity, options: ActivityOptions? = null) {
        creatorIntent(activity)?.let { intent ->
            launch(intent, options, activity)
        }
    }

    fun launchProfile(activity: Activity, options: ActivityOptions? = null) {
        profileIntent(activity)?.let { intent ->
            launch(intent, options, activity)
        }
    }

    fun share(activity: Activity, horseId: String) {
        ShareCompat.IntentBuilder.from(activity)
            .setChooserTitle(R.string.share_chooser_title)
            .setText(activity.getString(R.string.share_content, URL_VIEWER, horseId))
            .setType("text/plain")
            .startChooser()
    }

    private fun viewerIntent(context: Context) = dynamicIntent(
        CLASS_NAME_VIEWER,
        context
    )

    private fun creatorIntent(context: Context) = dynamicIntent(
        CLASS_NAME_CREATOR,
        context
    )

    private fun profileIntent(context: Context) = dynamicIntent(
        CLASS_NAME_PROFILE,
        context
    )

    private fun deepLinkIntent(url: String, context: Context): Intent {
        return Intent(Intent.ACTION_VIEW, Uri.parse(url))
            .setPackage(context.packageName)
            .addCategory(Intent.CATEGORY_DEFAULT)
            .addCategory(Intent.CATEGORY_BROWSABLE)
    }

    private fun dynamicIntent(className: String, context: Context): Intent? {
        return try {
            Class.forName(className).run {
                Intent(Intent.ACTION_VIEW).setClassName(context.packageName, className)
            }
        } catch (e: ClassNotFoundException) {
            null
        }
    }

    private fun launch(intent: Intent, options: ActivityOptions?, activity: Activity) {
        if (options == null) {
            activity.startActivity(intent)
        } else {
            activity.startActivity(intent, options.toBundle())
        }
    }
}