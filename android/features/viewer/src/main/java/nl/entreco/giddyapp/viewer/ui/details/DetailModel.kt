package nl.entreco.giddyapp.viewer.ui.details

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.ObservableField
import nl.entreco.giddyapp.viewer.R
import nl.entreco.giddyapp.libs.horses.HorseDetail
import nl.entreco.giddyapp.libs.horses.HorseGender

class DetailModel(horseDetail: HorseDetail) {
    val name = ObservableField<String>(horseDetail.name)
    val desc = ObservableField<String>(horseDetail.desc)
    val genderIcon = ObservableField<Int>(iconFor(horseDetail.gender))
    val level = ObservableField<String>(horseDetail.type.toString())
    val price = ObservableField<String>(horseDetail.price.toString())
    val category = ObservableField<String>(horseDetail.category.toString())

    @StringRes
    private fun genderFor(gender: HorseGender): Int {
        return when (gender) {
            HorseGender.Male -> R.string.gender_male
            HorseGender.Female -> R.string.gender_female
            HorseGender.Gelding -> R.string.gender_gelding
            else -> R.string.gender_unknown
        }
    }

    @DrawableRes
    private fun iconFor(gender: HorseGender): Int {
        return when (gender) {
            HorseGender.Male -> R.drawable.ic_male
            HorseGender.Female -> R.drawable.ic_female
            HorseGender.Gelding -> R.drawable.ic_gelding
            else -> R.drawable.ic_gender_unknown
        }
    }
}