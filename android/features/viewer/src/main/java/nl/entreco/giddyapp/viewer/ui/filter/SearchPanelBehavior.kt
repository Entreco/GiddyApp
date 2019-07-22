package nl.entreco.giddyapp.viewer.ui.filter

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import nl.entreco.giddyapp.viewer.di.FilterBehaviour
import nl.entreco.giddyapp.viewer.di.ViewerScope
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@ViewerScope
class SearchPanelBehavior @Inject constructor(
    @FilterBehaviour private val behaviour: BottomSheetBehavior<View>
) {

    private val isExpanded = AtomicBoolean(false)

    init {
        behaviour.apply {
            setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(view: View, offset: Float) {
                    slideListeners.forEach {
                        it.onSlide(offset)
                    }
                }

                override fun onStateChanged(view: View, state: Int) {
                    when (state) {
                        BottomSheetBehavior.STATE_COLLAPSED -> collapse()
                        BottomSheetBehavior.STATE_EXPANDED -> expand()
                        BottomSheetBehavior.STATE_DRAGGING,
                        BottomSheetBehavior.STATE_SETTLING,
                        BottomSheetBehavior.STATE_HALF_EXPANDED,
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
        if (!isExpanded.get()) {
            expand()
        } else {
            collapse()
        }
    }

    private var slideListeners: MutableList<SlideListener> = mutableListOf()
    fun slideListener(vararg listeners: SlideListener) {
        slideListeners.addAll(listeners)
    }

    interface SlideListener {
        fun onSlide(offset: Float)
    }
}