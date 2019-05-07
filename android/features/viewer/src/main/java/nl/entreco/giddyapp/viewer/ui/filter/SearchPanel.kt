package nl.entreco.giddyapp.viewer.ui.filter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import nl.entreco.giddyapp.viewer.databinding.SearchPanelBinding

class SearchPanel @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: SearchPanelBinding
    private val inflater by lazy { LayoutInflater.from(context) }

    init {
        binding = SearchPanelBinding.inflate(inflater, this, true)
    }
}