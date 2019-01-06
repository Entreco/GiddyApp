package nl.entreco.giddyapp.viewer

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import nl.entreco.giddyapp.core.BaseActivity
import nl.entreco.giddyapp.viewer.databinding.ActivityViewerBinding

class ViewerActivity : BaseActivity() {

    private val component by componentProvider { ViewerModule(intent.data?.path) }
    private val viewModel by viewModelProvider { component.viewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityViewerBinding>(this, R.layout.activity_viewer)
        binding.viewModel = viewModel
    }
}