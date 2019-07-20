package nl.entreco.giddyapp.viewer.ui.details

import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import nl.entreco.giddyapp.libhorses.Horse
import nl.entreco.giddyapp.libhorses.HorseDetail
import nl.entreco.giddyapp.libhorses.icon

class DetailModel(horse: Horse, horseDetail: HorseDetail) {
    val visibility = ObservableInt(
        when (horse.imageRef) {
            "none", "error", "notFound" -> View.INVISIBLE
            else -> View.VISIBLE
        }
    )
    val horseId = ObservableField(horse.id)
    val name = ObservableField(horseDetail.name)
    val desc = ObservableField(horseDetail.desc)
    val genderIcon = ObservableInt(horseDetail.gender.icon())
    val level = ObservableField(horseDetail.type)
    val price = ObservableField(horseDetail.price)
    val category = ObservableField(horseDetail.category)
}