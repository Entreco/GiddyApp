package nl.entreco.giddyapp.creator.ui.entry

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import nl.entreco.giddyapp.creator.R
import nl.entreco.giddyapp.libpicker.SelectedImage
import nl.entreco.giddyapp.libs.horses.HorseDetail
import nl.entreco.giddyapp.libs.horses.HorseGender

data class EntryModel(val horseDetail: HorseDetail, val image: SelectedImage)

sealed class Form {
    data class Name(val title: Int = R.string.entry_name, val hint: String = "") : Form() {
        val input = ObservableField<String>("")
    }

    data class Desc(val title: Int = R.string.entry_desc, val hint: String = "") : Form() {
        val input = ObservableField<String>("")
    }

    data class Gender(val title: Int = R.string.entry_gender) : Form() {
        val input = ObservableInt(HorseGender.Unknown.number)
        val entries = listOf(
            HorseGender.Unknown,
            HorseGender.Male,
            HorseGender.Female,
            HorseGender.Gelding
        )
    }

    object Empty : Form()
}