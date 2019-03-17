package nl.entreco.giddyapp.viewer.navigation.installed

import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import nl.entreco.giddyapp.libcore.launch.DynamicLauncher
import nl.entreco.giddyapp.libcore.launch.LaunchHelper
import nl.entreco.giddyapp.viewer.R
import nl.entreco.giddyapp.viewer.ViewerActivity
import nl.entreco.giddyapp.viewer.navigation.ViewerNavigation
import javax.inject.Inject

class InstalledViewerNavigation @Inject constructor(
    private val activity: ViewerActivity,
    private val dynamicLauncher: DynamicLauncher
) : ViewerNavigation {

    override val fabIcon: Int
        get() = R.drawable.ic_filter

    override val fabText: Int
        get() = 0

    override val isInstant: Boolean
        get() = false

    override fun onFabClicked(eFab: ExtendedFloatingActionButton, fab: FloatingActionButton) {
        fab.hide()
    }

    override fun onProfileClicked() {
        dynamicLauncher.launch(activity.lifecycle, "profile")
        dynamicLauncher.listen().observe(activity, Observer { state ->
            when (state) {
                SplitInstallSessionStatus.INSTALLED -> LaunchHelper.launchProfile(activity, null)
                SplitInstallSessionStatus.FAILED -> Toast.makeText(
                    activity,
                    "Installation failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onShareClicked(horseId: String) {
        LaunchHelper.share(activity, horseId)
    }
}

object InstalledBinding {

    @JvmStatic
    @BindingAdapter("ga_fab")
    fun fabIcon(fab: FloatingActionButton, @DrawableRes resource: Int) {
        fab.setImageResource(resource)
    }

    @JvmStatic
    @BindingAdapter("ga_text")
    fun fabText(fab: FloatingActionButton, @StringRes resource: Int) {
    }

    @JvmStatic
    @BindingAdapter("ga_show")
    fun fabVisibility(fab: FloatingActionButton, isInstant: Boolean) {
        if (isInstant) fab.visibility = View.GONE
        else {
            fab.visibility = View.VISIBLE
            val lp = fab.layoutParams as CoordinatorLayout.LayoutParams
            lp.anchorId = R.id.bar
            fab.layoutParams = lp
            fab.requestLayout()
        }
    }
}

