package nl.entreco.giddyapp.creator.ui.entry

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

    data class Desc(val title: Int = R.string.entry_desc, val hint: String = "") : Form() {
        val input = ObservableField<String>("")
    }

    data class Gender(val title: Int = R.string.entry_gender) : Form() {
        val input = ObservableInt(HorseGender.Unknown.ordinal)
        val entries = HorseGender.values().toList()
    }

    data class Level(val title: Int = R.string.entry_level) : Form() {
        val input = ObservableInt(HorseLevel.Unknown.ordinal)
    }

    data class Price(val title: Int = R.string.entry_price) : Form() {
        val input = ObservableInt(HorsePrice.NotForSale.ordinal)
    }

    data class Category(val title: Int = R.string.entry_category) : Form() {
        val input = ObservableInt(HorseCategory.Unknown.ordinal)
    }

    object Empty : Form()
}