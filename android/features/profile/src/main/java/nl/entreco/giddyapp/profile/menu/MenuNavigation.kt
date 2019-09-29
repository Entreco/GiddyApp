package nl.entreco.giddyapp.profile.menu

import android.widget.Toast
import nl.entreco.giddyapp.libcore.launch.LaunchHelper
import nl.entreco.giddyapp.libcore.launch.features.CreatorNavigator
import nl.entreco.giddyapp.profile.ProfileActivity
import javax.inject.Inject

class MenuNavigation @Inject constructor(
    private val activity: ProfileActivity
) {

    private val fm = activity.supportFragmentManager

    fun onProfileItemClicked(item: MenuItem) {
        when (item) {
            is MenuItem.Upload -> launchCreator(item)
            is MenuItem.Matches -> showMatches(item.uid)
            is MenuItem.Settings -> showSettings()
            is MenuItem.About -> showAbout()
            else -> Toast.makeText(activity, "NOT IMPLEMENTED: $item", Toast.LENGTH_SHORT).show()
        }
    }


    private fun launchCreator(item: MenuItem) {
        CreatorNavigator.launch(activity) { progress, max, launch ->
            item.progress.set(progress.toInt())
            item.max.set(max.toInt())
            if (launch) {
                LaunchHelper.launchCreator(activity)
                activity.finish()
            }
        }
    }

    private fun showMatches(uid: String) {
        LaunchHelper.launchHistory(activity, null, uid)
    }

    private fun showSettings() {
//        LaunchHelper.launchSettings(activity, null)
//        activity.finish()
    }

    private fun showAbout() {
//        LaunchHelper.launchAbout(activity, null)
//        activity.finish()
    }
}