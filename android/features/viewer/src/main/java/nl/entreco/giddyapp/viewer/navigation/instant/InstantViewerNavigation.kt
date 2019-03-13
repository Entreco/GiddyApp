package nl.entreco.giddyapp.viewer.navigation.instant

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.BindingAdapter
import com.google.android.gms.instantapps.InstantApps
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import nl.entreco.giddyapp.libcore.launch.LaunchHelper
import nl.entreco.giddyapp.viewer.R
import nl.entreco.giddyapp.viewer.ViewerActivity
import nl.entreco.giddyapp.viewer.navigation.ViewerNavigation
import javax.inject.Inject

class InstantViewerNavigation @Inject constructor(
    private val activity: ViewerActivity
) : ViewerNavigation {

    override val fabIcon: Int
        get() = R.drawable.ic_get_app

    override val fabText: Int
        get() = R.string.install

    override val isInstant: Boolean
        get() = true

    companion object {
        private val REFERRER = "InstallApiActivity"
        private val REQUEST_CODE = 7
    }

    override fun onFabClicked(eFab: ExtendedFloatingActionButton, fab: FloatingActionButton) {
        hide(eFab)
        InstantApps.showInstallPrompt(activity, postInstallIntent, REQUEST_CODE, REFERRER)
    }

    private fun hide(eFab: ExtendedFloatingActionButton) = eFab.shrink(true)
    private fun show(eFab: ExtendedFloatingActionButton) = eFab.extend(true)
    private val postInstallIntent = Intent(Intent.ACTION_VIEW, Uri.parse(LaunchHelper.URL_VIEWER))
        .addCategory(Intent.CATEGORY_DEFAULT)
        .addCategory(Intent.CATEGORY_BROWSABLE)

    override fun onProfileClicked() {
        throw IllegalStateException("InstantViewerNavigation should not have Profile")
    }

    override fun onShareClicked(horseId: String) {
        throw IllegalStateException("InstantViewerNavigation should not have Share")
    }
}

object InstantBinding {

    @JvmStatic
    @BindingAdapter("ga_fab")
    fun efabIcon(eFab: ExtendedFloatingActionButton, @DrawableRes resource: Int) {
        eFab.setIconResource(resource)
        eFab.extend(true)
    }

    @JvmStatic
    @BindingAdapter("ga_text")
    fun efabText(eFab: ExtendedFloatingActionButton, @StringRes resource: Int) {
        if (resource != 0) eFab.setText(resource)
    }

    @JvmStatic
    @BindingAdapter("ga_show")
    fun efabVisibility(eFab: ExtendedFloatingActionButton, isInstant: Boolean) {
        if (isInstant){
            eFab.visibility = View.VISIBLE
            val lp = eFab.layoutParams as CoordinatorLayout.LayoutParams
            lp.anchorId = R.id.bar
            eFab.layoutParams = lp
            eFab.requestLayout()
        }
        else eFab.visibility = View.GONE
    }
}