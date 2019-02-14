package nl.entreco.giddyapp.viewer.ui.details

import androidx.databinding.ObservableField
import nl.entreco.giddyapp.libs.horses.HorseDetail
import nl.entreco.giddyapp.libs.horses.icon

class DetailModel(horseDetail: HorseDetail) {
    val name = ObservableField<String>(horseDetail.name)
    val desc = ObservableField<String>(horseDetail.desc)
    val genderIcon = ObservableField<Int>(horseDetail.gender.icon())
    val level = ObservableField<String>(horseDetail.type.toString())
    val price = ObservableField<String>(horseDetail.price.toString())
    val category = ObservableField<String>(horseDetail.category.toString())
}