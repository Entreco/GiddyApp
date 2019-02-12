package nl.entreco.giddyapp.creator.ui.bottom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import nl.entreco.giddyapp.creator.databinding.WidgetBottomProgressViewBinding

class BottomProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    private val binding: WidgetBottomProgressViewBinding
    private val inflater by lazy { LayoutInflater.from(context) }

    init {
        binding = WidgetBottomProgressViewBinding.inflate(inflater, this, true)
        clipChildren = false
        hide()
    }

    fun setModel(model: BottomProgressModel?) {
        model?.let { mod ->
            binding.model = mod
            binding.executePendingBindings()
            if (mod.showFab()) {
                show()
            } else {
                hide()
            }
        }
    }

    private fun show(){
        binding.fab.show()
    }

    private fun hide(){
        binding.fab.hide()
    }

    override fun setOnClickListener(listener: View.OnClickListener) {
        binding.fab.setOnClickListener {
            listener.onClick(it)
        }
    }
}