package nl.entreco.giddyapp.viewer

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.core.Api
import nl.entreco.giddyapp.core.onBg
import nl.entreco.giddyapp.core.onUi
import nl.entreco.giddyapp.viewer.fetch.FetchHorseRequest
import nl.entreco.giddyapp.viewer.fetch.FetchHorseUsecase
import java.net.URL
import javax.inject.Inject


class ViewerViewModel @Inject constructor(
    api: Api,
    uri: String,
    fetchHorseUsecase: FetchHorseUsecase
) : ViewModel() {

    val url = ObservableField<String>(uri + " rand: ${api.fetch()}")
    val horse = ObservableField<String>()
    val background = ObservableField<Drawable>(gradient("#ff6600", "#00ffff"))
    val src = ObservableField<Uri>()

    init {
        fetchHorseUsecase.go(FetchHorseRequest(uri)) { response ->
            response.horse?.let { result ->
                horse.set(result.name)
                background.set(gradient(result.start, result.end))
                fetchHorseUsecase.image(result.imageRef) { uri ->
                    src.set(uri)
                }
            }
        }
    }

    private fun gradient(start: String, end: String) = GradientDrawable(
        GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(
            Color.parseColor(start), Color.parseColor(end)
        )
    )

    companion object {
        @JvmStatic
        @BindingAdapter("android:src")
        fun setImageUri(view: ImageView, imageUri: Uri?) {
            Log.i("WOW", "uri:$imageUri")
            onBg {
                val bmp = BitmapFactory.decodeStream(URL(imageUri.toString()).openConnection().getInputStream())
                onUi {
                    view.setImageBitmap(bmp)
                }
            }
        }
    }
}