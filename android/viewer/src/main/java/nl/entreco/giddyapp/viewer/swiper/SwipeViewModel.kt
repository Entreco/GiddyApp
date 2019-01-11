package nl.entreco.giddyapp.viewer.swiper

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.View
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.viewer.fetch.FetchImageRequest
import nl.entreco.giddyapp.viewer.fetch.FetchImageUsecase
import java.net.URL
import javax.inject.Inject

class SwipeViewModel @Inject constructor(
    private val fetchImageUsecase: FetchImageUsecase
) : ViewModel() {

    val nextProgress = ObservableFloat(0F)
    val next = ObservableField<SwipeHorseModel>()
    val current = ObservableField<SwipeHorseModel>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                val ref = sender as? ObservableField<SwipeHorseModel>
                ref?.get()?.horse?.imageRef?.let {
                    refreshCurrentImage(it)
                }
            }
        })
    }

    fun trackProgress(view: View, progress: Float) {
        nextProgress.set(progress)
    }

    private fun refreshCurrentImage(imageRef: String) {
        fetchImageUsecase.go(FetchImageRequest("$imageRef.jpg")) { response ->
            current.get()?.image?.set(BitmapDrawable(response.image))
        }
    }
}