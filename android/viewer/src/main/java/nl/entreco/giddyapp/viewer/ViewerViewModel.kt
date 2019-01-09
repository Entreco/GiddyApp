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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.core.onBg
import nl.entreco.giddyapp.core.onUi
import nl.entreco.giddyapp.viewer.fetch.FetchHorseRequest
import nl.entreco.giddyapp.viewer.fetch.FetchHorseUsecase
import java.net.URL
import javax.inject.Inject


class ViewerViewModel @Inject constructor(
    fetchHorseUsecase: FetchHorseUsecase
) : ViewModel() {

    private val current = MutableLiveData<Horse>()
    private val next = MutableLiveData<Horse>()

    init {
        fetchHorseUsecase.go(FetchHorseRequest()) { response ->
            current.postValue(response.current)
            next.postValue(response.next)
        }
    }

    fun current() : LiveData<Horse>{
        return current
    }

    fun next() : LiveData<Horse>{
        return next
    }

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