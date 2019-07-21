package nl.entreco.giddyapp.viewer.ui.swiper

import android.graphics.drawable.GradientDrawable
import android.net.Uri
import androidx.databinding.ObservableField
import nl.entreco.giddyapp.libhorses.Horse

data class SwipeHorseModel(private val horse: Horse, val draggable: Boolean = false) {

    val horseId = horse.id
    val horseName = horse.name
    val horseRef = horse.imageRef
    val ref = ObservableField(horse.imageRef)
    val image = ObservableField<Uri>(horse.imageUri)
    val gradient = horse.gradient
}