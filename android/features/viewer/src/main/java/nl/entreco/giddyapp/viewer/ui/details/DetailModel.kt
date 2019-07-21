package nl.entreco.giddyapp.viewer.ui.details

import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import nl.entreco.giddyapp.libhorses.Horse
import nl.entreco.giddyapp.libhorses.icon

class DetailModel(horse: Horse) {
    val visibility = ObservableInt(
        when (horse) {
            is Horse.Normal -> View.VISIBLE
            else -> View.INVISIBLE
        }
    )
    val horseId = ObservableField(horse.id)
    val name = ObservableField(horse.name)
    val desc = ObservableField(horse.desc)
    val genderIcon = ObservableInt(horse.gender.icon())
    val level = ObservableField(horse.type)
    val price = ObservableField(horse.price)
    val category = ObservableField(horse.category)
}