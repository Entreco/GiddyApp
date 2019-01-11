package nl.entreco.giddyapp.viewer.swiper

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.Log
import androidx.databinding.ObservableField
import nl.entreco.giddyapp.core.onBg
import nl.entreco.giddyapp.core.onUi
import nl.entreco.giddyapp.viewer.Horse
import java.net.URL

data class SwipeHorseModel(val horse: Horse, val draggable: Boolean = false) {

    val name = ObservableField<String>(horse.name)
    val image = ObservableField<Drawable>()
    val gradient = GradientDrawable(
        GradientDrawable.Orientation.TOP_BOTTOM,
        intArrayOf(Color.parseColor(horse.start), Color.parseColor(horse.end))
    )

    init {
        Log.i("INFO", "fucking init")
        onBg {
            val bmp = BitmapFactory.decodeStream(URL(horse.imageRef).openConnection().getInputStream())
            onUi {
                image.set(BitmapDrawable(bmp))
            }
        }
    }
}