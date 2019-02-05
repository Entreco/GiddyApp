package nl.entreco.giddyapp.core

import androidx.databinding.BindingAdapter
import androidx.constraintlayout.widget.Guideline

object ConstraintBindings {

    @JvmStatic
    @BindingAdapter("ga_constraintEnd")
    fun applyRandomRotation(view: Guideline, offset: Float) {
        val header = view.context.resources.getDimension(R.dimen.sheet_header_height)
        val start = view.context.resources.getDimension(R.dimen.sheet_peek_height)
        val end = view.context.resources.getDimension(R.dimen.sheet_height)
        val difference = end - start
        val guidelineEnd = (start + (offset * difference) - header).toInt()
        view.setGuidelineEnd(guidelineEnd)
    }

}