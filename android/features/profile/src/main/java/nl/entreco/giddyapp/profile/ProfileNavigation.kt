package nl.entreco.giddyapp.profile

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import nl.entreco.giddyapp.libcore.di.AppScope
import nl.entreco.giddyapp.libcore.launch.DynamicLauncher
import nl.entreco.giddyapp.libcore.launch.LaunchHelper
import nl.entreco.giddyapp.libcore.launch.features.CreatorNavigator
import nl.entreco.giddyapp.libcore.launch.features.ProfileNavigator
import javax.inject.Inject

class ProfileNavigation @Inject constructor(
    private val activity: ProfileActivity,
    @AppScope private val dynamicLauncher: DynamicLauncher
) {

    fun launchCreator() {
        CreatorNavigator.launch(activity) { progress, intent ->
            Log.i("WOOP", "progress: $progress")
            intent?.let {
                activity.startActivity(intent)
                activity.finish()
            }
        }
    }

    fun launchSettings() {
//        LaunchHelper.launchSettings(activity, null)
//        activity.finish()
    }
}