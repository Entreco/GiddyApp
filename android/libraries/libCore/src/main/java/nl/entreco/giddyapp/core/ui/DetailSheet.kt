package nl.entreco.giddyapp.core.ui

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import javax.inject.Inject

class DetailSheet @Inject constructor(view: View) {

    init {
        BottomSheetBehavior.from(view).setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {
                slideListener?.onSlide(p1)
            }

            override fun onStateChanged(p0: View, p1: Int) {}
        })
    }

    var slideListener: SlideListener? = null

    interface SlideListener {
        fun onSlide(offset: Float)
    }
}