package nl.entreco.giddyapp.profile

import android.app.Activity
import nl.entreco.giddyapp.libcore.launch.LaunchHelper
import javax.inject.Inject

class ProfileNavigation @Inject constructor(
    private val activity: Activity
) {

    fun launchCreator(){
        LaunchHelper.launchCreator(activity, null)
        activity.finish()
    }

    fun launchSettings(){
//        LaunchHelper.launchSettings(activity, null)
//        activity.finish()
    }
}