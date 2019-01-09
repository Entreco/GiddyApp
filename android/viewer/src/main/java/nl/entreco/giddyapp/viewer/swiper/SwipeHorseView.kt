package nl.entreco.giddyapp.viewer.swiper

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import nl.entreco.giddyapp.viewer.databinding.WidgetSwipeHorseViewBinding

class SwipeHorseView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: WidgetSwipeHorseViewBinding
    private val inflater by lazy { LayoutInflater.from(context) }

    init {
        binding = WidgetSwipeHorseViewBinding.inflate(inflater, this, true)
    }

    fun setModel(model: SwipeHorseModel?) {
        binding.model = model
        binding.executePendingBindings()
        background = model?.gradient
    }
}