package nl.entreco.giddyapp.creator.ui.bottom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.Bindable
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.R
import nl.entreco.giddyapp.creator.databinding.WidgetBottomProgressViewBinding
import nl.entreco.giddyapp.creator.icon

class BottomProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    private val binding: WidgetBottomProgressViewBinding
    private val inflater by lazy { LayoutInflater.from(context) }
    private val animator by lazy { BottomAnimator(this) }

    init {
        binding = WidgetBottomProgressViewBinding.inflate(inflater, this, true)
        clipChildren = false
    }

    fun setModel(model: BottomProgressModel?) {
        model?.let { mod ->
            binding.model = mod
            binding.executePendingBindings()
            animator.slideTo(mod.state.index)
            binding.fab.setImageResource(mod.state.icon())
        }
    }

    override fun setOnClickListener(listener: View.OnClickListener){
        binding.fab.setOnClickListener {
            listener.onClick(it)
        }
    }
}