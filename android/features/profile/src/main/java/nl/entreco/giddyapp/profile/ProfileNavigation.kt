package nl.entreco.giddyapp.profile

import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import nl.entreco.giddyapp.libcore.di.AppScope
import nl.entreco.giddyapp.libcore.launch.DynamicLauncher
import nl.entreco.giddyapp.libcore.launch.LaunchHelper
import javax.inject.Inject

class ProfileNavigation @Inject constructor(
    private val activity: ProfileActivity,
    @AppScope private val dynamicLauncher: DynamicLauncher
) {

    fun launchCreator() {

        dynamicLauncher.listen("creator").observe(activity, Observer { state ->
            when (state) {
                SplitInstallSessionStatus.INSTALLED -> {
                    LaunchHelper.launchCreator(activity, null)
                    activity.finish()
                }
                SplitInstallSessionStatus.FAILED -> Toast.makeText(
                    activity,
                    "Installation failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun launchSettings() {
//        LaunchHelper.launchSettings(activity, null)
//        activity.finish()
    }
}