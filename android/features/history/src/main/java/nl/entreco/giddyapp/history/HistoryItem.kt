package nl.entreco.giddyapp.history

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.libcore.R

data class HistoryItem(val userLike: UserLike, val selected: Boolean = false){

    val horseId = userLike.horseId
    val name = ObservableField(userLike.horseName)
    val ref = ObservableField(userLike.horseRef)
    val stroke = ObservableInt(if(selected) R.color.colorPrimary else android.R.color.transparent)

    fun toggleSelected() = copy(selected = !this.selected)
}