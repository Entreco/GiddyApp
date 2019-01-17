package nl.entreco.giddyapp.viewer

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import javax.inject.Inject

class SheetToggler @Inject constructor(view: View) {

    init {
        BottomSheetBehavior.from(view).setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {
                listener?.onSlide(p1)
            }

            override fun onStateChanged(p0: View, p1: Int) {}
        })
    }

    var listener : Listener? = null
    interface Listener {
        fun onSlide(offset: Float)
    }
}