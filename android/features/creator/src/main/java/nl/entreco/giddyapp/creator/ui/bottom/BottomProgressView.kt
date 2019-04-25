package nl.entreco.giddyapp.creator.ui.bottom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
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
            configureFab(mod.fab())
        }
    }

    private fun configureFab(@DrawableRes icon: Int) {
        when (icon) {
            0 -> hide()
            else -> show(icon)
        }
    }

    private fun show(@DrawableRes icon: Int) {
        binding.fab.setImageResource(icon)
        binding.fab.show()
    }

    private fun hide() {
        binding.fab.hide()
    }

    override fun setOnClickListener(listener: OnClickListener) {
        binding.fab.setOnClickListener { fab ->
            binding.fab.animate()
                .withStartAction {
                    binding.fab.hide()
                    binding.fab.setImageResource(binding.model?.waiting() ?: 0)
                    listener.onClick(fab)
                }
                .withEndAction {
                    binding.fab.show()
                }
                .start()
        }
    }
}