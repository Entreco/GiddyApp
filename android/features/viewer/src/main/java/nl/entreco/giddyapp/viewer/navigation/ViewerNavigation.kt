package nl.entreco.giddyapp.viewer.navigation

import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

interface ViewerNavigation {
    val fabIcon: Int
    val hasMenu: Boolean
    fun onFabClicked()
    fun onProfileClicked()
    fun onShareClicked(horseId: String)
}

object ViewerBinding {
    @JvmStatic
    @BindingAdapter("ga_fab")
    fun fabIcon(fab: FloatingActionButton, @DrawableRes resource: Int) {
        fab.setImageResource(resource)
    }
}