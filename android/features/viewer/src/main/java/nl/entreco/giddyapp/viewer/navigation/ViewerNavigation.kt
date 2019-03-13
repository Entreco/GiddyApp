package nl.entreco.giddyapp.viewer.navigation

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

interface ViewerNavigation {
    val fabIcon: Int
    val fabText: Int
    val isInstant: Boolean
    fun onFabClicked(eFab: ExtendedFloatingActionButton, fab: FloatingActionButton)
    fun onProfileClicked()
    fun onShareClicked(horseId: String)
}