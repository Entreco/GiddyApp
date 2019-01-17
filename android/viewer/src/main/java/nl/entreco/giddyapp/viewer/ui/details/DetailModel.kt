package nl.entreco.giddyapp.viewer.ui.details

import androidx.databinding.ObservableField
import nl.entreco.giddyapp.viewer.domain.HorseDetail

class DetailModel(private val horseDetail: HorseDetail) {

    val name = ObservableField<String>(horseDetail.name)
    val desc = ObservableField<String>(horseDetail.desc)
    val likes = ObservableField<String>("${horseDetail.likes}")
    val category = ObservableField<String>(horseDetail.cat)
    val price = ObservableField<String>(horseDetail.price)
}