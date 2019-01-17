package nl.entreco.giddyapp.viewer

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import javax.inject.Inject

class SheetToggler @Inject constructor(view: View) {

    var listener : Listener? = null

    private val callback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(p0: View, p1: Float) {
            listener?.onSlide(p1)
        }

        override fun onStateChanged(p0: View, p1: Int) {
            when(p1){
                BottomSheetBehavior.STATE_COLLAPSED -> listener?.onSlide(0F)
                BottomSheetBehavior.STATE_EXPANDED -> listener?.onSlide(1F)
            }
        }
    }

    private val behavior = BottomSheetBehavior.from(view).apply {
        setBottomSheetCallback(callback)
    }
    internal var state: Int = BottomSheetBehavior.STATE_COLLAPSED
        set(value) {
            behavior.state = value
        }

    interface Listener {
        fun onSlide(offset: Float)
    }
}