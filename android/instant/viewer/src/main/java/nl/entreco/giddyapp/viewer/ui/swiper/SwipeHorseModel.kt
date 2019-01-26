package nl.entreco.giddyapp.viewer.ui.swiper

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import androidx.databinding.ObservableField
import nl.entreco.giddyapp.viewer.domain.Horse

data class SwipeHorseModel(private val horse: Horse?, val draggable: Boolean = false) {

    val image = ObservableField<Uri>(horse?.imageUri)
    val gradient = GradientDrawable(
        GradientDrawable.Orientation.TOP_BOTTOM,
        intArrayOf(Color.parseColor(horse?.start), Color.parseColor(horse?.end))
    )
}