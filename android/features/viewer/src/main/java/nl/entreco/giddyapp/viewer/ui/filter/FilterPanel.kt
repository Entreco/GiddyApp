package nl.entreco.giddyapp.viewer.ui.filter

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import nl.entreco.giddyapp.viewer.di.FilterBehaviour
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class FilterPanel @Inject constructor(
    @FilterBehaviour private val behaviour: BottomSheetBehavior<View>
) {

    private val isExpanded = AtomicBoolean(true)

    init {
        behaviour.apply {
            setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(p0: View, p1: Float) {}

                override fun onStateChanged(p0: View, p1: Int) {
                    when (p1) {
                        BottomSheetBehavior.STATE_DRAGGING, BottomSheetBehavior.STATE_SETTLING -> { }
                        BottomSheetBehavior.STATE_COLLAPSED -> collapse()
                        BottomSheetBehavior.STATE_EXPANDED -> expand()
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> { }
                        BottomSheetBehavior.STATE_HIDDEN -> { }
                    }
                }
            })
        }
    }

    private fun expand() {
        isExpanded.set(true)
        behaviour.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun collapse() {
        isExpanded.set(false)
        behaviour.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun toggle() {
        if (isExpanded.get()) {
            collapse()
        } else {
            expand()
        }
    }
}