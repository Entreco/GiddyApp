package nl.entreco.giddyapp.viewer.ui.details

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import nl.entreco.giddyapp.libs.horses.*

class DetailModel(horseDetail: HorseDetail) {
    val name = ObservableField<String>(horseDetail.name)
    val desc = ObservableField<String>(horseDetail.desc)
    val genderIcon = ObservableInt(horseDetail.gender.icon())
    val level = ObservableField<HorseLevel>(horseDetail.type)
    val price = ObservableField<HorsePrice>(horseDetail.price)
    val category = ObservableField<HorseCategory>(horseDetail.category)
}