package nl.entreco.giddyapp.viewer.navigation

import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

interface ViewerNavigation {
    val fabIcon : Int
    fun onFabClicked()
    fun onProfileClicked()
    fun onSettingsClicked()
}

object ViewerBinding{
    @JvmStatic
    @BindingAdapter("ga_fab")
    fun fabIcon(fab: FloatingActionButton, @DrawableRes resource: Int){
        fab.setImageResource(resource)
    }
}