package nl.entreco.giddyapp.viewer.ui.swiper

import android.graphics.drawable.GradientDrawable
import android.net.Uri
import androidx.databinding.ObservableField
import nl.entreco.giddyapp.libhorses.Horse

data class SwipeHorseModel(private val horse: Horse, val draggable: Boolean = false) {

    val image = ObservableField<Uri>(horse.imageUri)
    val gradient = GradientDrawable(
        GradientDrawable.Orientation.TOP_BOTTOM,
        intArrayOf(horse.start.color(), horse.end.color())
    )
}