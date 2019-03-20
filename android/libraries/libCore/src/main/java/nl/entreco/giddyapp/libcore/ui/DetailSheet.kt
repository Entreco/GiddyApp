package nl.entreco.giddyapp.libcore.ui

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class DetailSheet @Inject constructor(
    @SheetBehavior private val behaviour: BottomSheetBehavior<View>
) {
    private val isLockedExpanded = AtomicBoolean(false)
    private val isLockedCollapsed = AtomicBoolean(false)

    init {
        behaviour.apply {
            setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(p0: View, p1: Float) {
                    if (isLockedExpanded.get() || isLockedCollapsed.get()) return
                    slideListeners.forEach {
                        it.onSlide(p1)
                    }
                }

                override fun onStateChanged(p0: View, p1: Int) {
                    when (p1) {
                        BottomSheetBehavior.STATE_DRAGGING, BottomSheetBehavior.STATE_SETTLING -> handleLock()
                        BottomSheetBehavior.STATE_COLLAPSED,
                        BottomSheetBehavior.STATE_EXPANDED,
                        BottomSheetBehavior.STATE_HALF_EXPANDED,
                        BottomSheetBehavior.STATE_HIDDEN -> { }
                    }
                }
            })
        }
        collapse()
    }

    private fun handleLock() {
        if (isLockedExpanded.get()) {
            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
        } else if (isLockedCollapsed.get()) {
            behaviour.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    fun collapse(lock: Boolean = false) {
        isLockedCollapsed.set(lock)
        isLockedExpanded.set(false)
        behaviour.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun expand(lock: Boolean = false) {
        isLockedExpanded.set(lock)
        isLockedCollapsed.set(false)
        behaviour.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private var slideListeners: MutableList<SlideListener> = mutableListOf()

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
}