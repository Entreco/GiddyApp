package nl.entreco.giddyapp.viewer.ui.details

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import nl.entreco.giddyapp.libhorses.*

class DetailModel(horseId: String, horseDetail: HorseDetail) {
    val horseId = ObservableField<String>(horseId)
    val name = ObservableField<String>(horseDetail.name)
    val desc = ObservableField<String>(horseDetail.desc)
    val genderIcon = ObservableInt(horseDetail.gender.icon())
    val level = ObservableField<HorseLevel>(horseDetail.type)
    val price = ObservableField<HorsePrice>(horseDetail.price)
    val category = ObservableField<HorseCategory>(horseDetail.category)
}