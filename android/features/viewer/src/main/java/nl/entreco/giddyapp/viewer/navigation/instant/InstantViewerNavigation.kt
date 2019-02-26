package nl.entreco.giddyapp.viewer.navigation.instant

import android.app.Activity
import android.util.Log
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import nl.entreco.giddyapp.core.LaunchHelper
import nl.entreco.giddyapp.viewer.R
import nl.entreco.giddyapp.viewer.navigation.ViewerNavigation
import javax.inject.Inject

class InstantViewerNavigation @Inject constructor(private val activity: Activity) : ViewerNavigation {
    override val fabIcon: Int
        get() = R.drawable.ic_get_app

    override fun onFabClicked() {
        Log.i("ViewerNavigation", "onFabClicked instant app")
        val request = SplitInstallRequest.newBuilder().addModule("viewer").build()
        val manager = SplitInstallManagerFactory.create(activity)
        manager.startInstall(request)

//        InstantApps.showInstallPrompt(activity,
//            postInstallIntent,
//            REQUEST_CODE,
//            REFERRER)

    }

    override fun onProfileClicked() {
        LaunchHelper.launchCreator(activity, null)
    }

    override fun onSettingsClicked() {
//        LaunchHelper.launchSettings(activity, null)
    }
}