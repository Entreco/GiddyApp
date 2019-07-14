package nl.entreco.giddyapp.viewer.navigation

import android.view.View
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

interface ViewerNavigation {
    val fabIcon: Int
    val fabText: Int
    val isInstant: Boolean
    fun onFabClicked(eFab: ExtendedFloatingActionButton, fab: FloatingActionButton)
    fun onProfileClicked(view: View)
    fun onShareClicked(horseId: String)
}