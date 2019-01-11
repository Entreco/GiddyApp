package nl.entreco.giddyapp.viewer.swiper

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.databinding.ObservableField
import nl.entreco.giddyapp.viewer.Horse

data class SwipeHorseModel(private val horse: Horse?, val draggable: Boolean = false) {

    val name = ObservableField<String>(horse?.name)
    val image = ObservableField<Drawable>(BitmapDrawable(horse?.bitmap))
    val gradient = GradientDrawable(
        GradientDrawable.Orientation.TOP_BOTTOM,
        intArrayOf(Color.parseColor(horse?.start), Color.parseColor(horse?.end))
    )
}