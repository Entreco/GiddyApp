package nl.entreco.giddyapp.profile

import nl.entreco.giddyapp.libcore.di.AppScope
import nl.entreco.giddyapp.libcore.launch.DynamicLauncher
import nl.entreco.giddyapp.libcore.launch.features.CreatorNavigator
import javax.inject.Inject

class ProfileNavigation @Inject constructor(
    private val activity: ProfileActivity,
    @AppScope private val dynamicLauncher: DynamicLauncher
) {

    fun launchCreator() {
        CreatorNavigator.launch(activity) { progress, intent ->
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