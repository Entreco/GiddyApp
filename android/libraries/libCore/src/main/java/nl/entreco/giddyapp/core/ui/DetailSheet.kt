package nl.entreco.giddyapp.core.ui

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import javax.inject.Inject

class DetailSheet @Inject constructor(
    private val behaviour: BottomSheetBehavior<View>
) {

    init {
        behaviour.apply {
            setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(p0: View, p1: Float) {
                    slideListeners.forEach {
                        it.onSlide(p1)
                    }
                }

                override fun onStateChanged(p0: View, p1: Int) {
                    when (p1) {
                        BottomSheetBehavior.STATE_COLLAPSED -> collapseListener?.onCollapsed()
                        BottomSheetBehavior.STATE_EXPANDED -> expandListener?.onExpanded()
                        else -> { }
                    }
                }
            })
        }
        collapse()
    }

    fun collapse() {
        behaviour.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun expand() {
        behaviour.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private var collapseListener: CollapseListener? = null
    private var expandListener: ExpandListener? = null
    private var slideListeners: MutableList<SlideListener> = mutableListOf()

    fun collapseListener(listener: CollapseListener) {
        collapseListener = listener
    }

    fun expandListener(listener: ExpandListener) {
        expandListener = listener
    }

    fun slideListener(vararg listeners: SlideListener) {
        slideListeners.addAll(listeners)
    }

    fun slideListener(f: (Float) -> Unit) {
        slideListeners.add(object : SlideListener {
            override fun onSlide(offset: Float) {
                f(offset)
            }
        })
    }

    interface SlideListener {
        fun onSlide(offset: Float)
    }

    interface CollapseListener {
        fun onCollapsed()
    }

    interface ExpandListener {
        fun onExpanded()
    }
}