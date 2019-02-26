package nl.entreco.giddyapp.viewer.navigation.installed

import android.app.Activity
import android.util.Log
import nl.entreco.giddyapp.core.LaunchHelper
import nl.entreco.giddyapp.viewer.R
import nl.entreco.giddyapp.viewer.navigation.ViewerNavigation
import javax.inject.Inject

class InstalledViewerNavigation @Inject constructor(private val activity: Activity) : ViewerNavigation {
    override val fabIcon: Int
        get() = R.drawable.ic_filter

    override fun onFabClicked() {
        Log.i("ViewerNavigation", "onFabClicked installed app")
    }

    override fun onProfileClicked() {
        LaunchHelper.launchCreator(activity, null)
    }

    override fun onSettingsClicked() {
//        LaunchHelper.launchSettings(activity, null)
    }
}