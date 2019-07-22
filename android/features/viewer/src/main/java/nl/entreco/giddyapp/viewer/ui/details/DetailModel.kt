package nl.entreco.giddyapp.viewer.ui.details

import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import nl.entreco.giddyapp.libhorses.*

class DetailModel(horse: Horse) {

    val horseId = ObservableField(horse.id)
    val name = ObservableField(horse.name)

    val desc = ObservableField(
        when (horse) {
            is Horse.Normal -> horse.details.desc
            is Horse.Loading -> "Please wait"
            is Horse.Error -> horse.msg
            else -> ""
        }
    )

    val genderIcon = ObservableInt(
        when (horse) {
            is Horse.Normal -> horse.details.gender.icon()
            else -> HorseGender.Unknown.icon()
        }
    )

    val level = ObservableField(
        when (horse) {
            is Horse.Normal -> horse.details.type
            else -> HorseLevel.Unknown
        }
    )

    val price = ObservableField(
        when (horse) {
            is Horse.Normal -> horse.details.price
            else -> HorsePrice.NotForSale
        }
    )

    val category = ObservableField(
        when (horse) {
            is Horse.Normal -> horse.details.category
            else -> HorseCategory.Unknown
        }
    )

    val visibility = ObservableInt(
        when (horse) {
            is Horse.Normal -> View.VISIBLE
            else -> View.INVISIBLE
        }
    )

}