package nl.entreco.giddyapp.viewer.ui.swiper

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import androidx.databinding.ObservableField
import nl.entreco.giddyapp.libhorses.Horse
import nl.entreco.giddyapp.libhorses.horses.R

data class SwipeHorseModel(private val horse: Horse, private val isCurrent: Boolean = true) {

    val horseId = horse.id
    val horseName = horse.name
    val horseRef = horse.imageRef
    val draggable = isCurrent && (horse is Horse.Normal || horse is Horse.NotFound)
    val ref = ObservableField(horseRef)
    val image = ObservableField<Uri>(horse.imageUri)

    val drawable = when (horse) {
        is Horse.NotFound -> R.drawable.empty
        is Horse.Install -> R.drawable.install
        else -> 0
    }

    val gradient = when (horse) {
        is Horse.Normal -> GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(horse.start.color(), horse.end.color())
        )
        else -> GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(Color.TRANSPARENT, Color.BLACK)
        )
    }
}