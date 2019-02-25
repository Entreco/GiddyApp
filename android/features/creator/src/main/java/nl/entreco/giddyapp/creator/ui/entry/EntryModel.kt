package nl.entreco.giddyapp.creator.ui.entry

import androidx.annotation.IdRes
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import nl.entreco.giddyapp.creator.R
import nl.entreco.giddyapp.libpicker.SelectedImage
import nl.entreco.giddyapp.libs.horses.*

data class EntryModel(val horseDetail: HorseDetail, val image: SelectedImage)

sealed class Form {
    data class Name(val title: Int = R.string.entry_name, val hint: String = "") : Form() {
        val input = ObservableField<String>("")
    }

    data class Desc(val hint: String = "") : Form() {
        val input = ObservableField<String>("")
    }

    data class Gender(val nono: Int = 0) : Form() {
        val input = ObservableInt(HorseGender.Unknown.ordinal)
        val checked = ObservableInt(0)

        fun checked(@IdRes itemId: Int){
            checked.set(itemId)
            when(itemId){
                R.id.stallion -> input.set(HorseGender.Stallion.ordinal)
                R.id.mare -> input.set(HorseGender.Mare.ordinal)
                R.id.gelding -> input.set(HorseGender.Gelding.ordinal)
                else -> input.set(HorseGender.Unknown.ordinal)
            }
        }
    }

    data class Level(val nono: Int = 0) : Form() {
        val input = ObservableInt(HorseLevel.Unknown.ordinal)
    }

    data class Price(val nono: Int = 0) : Form() {
        val input = ObservableInt(HorsePrice.NotForSale.ordinal)
    }

    data class Category(val title: Int = R.string.entry_category) : Form() {
        val input = ObservableInt(HorseCategory.Unknown.ordinal)
    }

    object Empty : Form()
}