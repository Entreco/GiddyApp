package nl.entreco.giddyapp.viewer.swiper

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import androidx.databinding.ObservableField
import nl.entreco.giddyapp.viewer.Horse

data class SwipeHorseModel(private val horse: Horse?, val draggable: Boolean = false) {

    val price = horse?.price
    val name = ObservableField<String>(horse?.name)
    val image = ObservableField<Uri>(horse?.imageUri)
    val gradient = GradientDrawable(
        GradientDrawable.Orientation.TOP_BOTTOM,
        intArrayOf(Color.parseColor(horse?.start), Color.parseColor(horse?.end))
    )
}