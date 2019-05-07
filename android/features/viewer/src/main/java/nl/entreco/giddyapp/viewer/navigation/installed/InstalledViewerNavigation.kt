package nl.entreco.giddyapp.viewer.navigation.installed

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import nl.entreco.giddyapp.libcore.di.AppScope
import nl.entreco.giddyapp.libcore.launch.DynamicLauncher
import nl.entreco.giddyapp.libcore.launch.LaunchHelper
import nl.entreco.giddyapp.libcore.launch.features.ProfileNavigator
import nl.entreco.giddyapp.viewer.R
import nl.entreco.giddyapp.viewer.ViewerActivity
import nl.entreco.giddyapp.viewer.di.ViewerScope
import nl.entreco.giddyapp.viewer.navigation.ViewerNavigation
import nl.entreco.giddyapp.viewer.ui.filter.SearchPanelBehavior
import javax.inject.Inject

class InstalledViewerNavigation @Inject constructor(
    private val activity: ViewerActivity,
    @AppScope private val dynamicLauncher: DynamicLauncher,
    @ViewerScope private val filterPanel: SearchPanelBehavior
) : ViewerNavigation {

    override val fabIcon: Int
        get() = R.drawable.ic_search

    override val fabText: Int
        get() = 0

    override val isInstant: Boolean
        get() = false

    override fun onFabClicked(eFab: ExtendedFloatingActionButton, fab: FloatingActionButton) = filterPanel.toggle()

    override fun onProfileClicked() {
        ProfileNavigator.launch(activity) { progress, intent ->
            intent?.let {
                activity.startActivity(intent)
            }
        }
    }

    override fun onShareClicked(horseId: String) = LaunchHelper.share(activity, horseId)
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

