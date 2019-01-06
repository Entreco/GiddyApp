package nl.entreco.giddyapp.viewer

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import nl.entreco.giddyapp.core.BaseActivity
import nl.entreco.giddyapp.viewer.databinding.ActivityViewerBinding

class ViewerActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityViewerBinding>(this, R.layout.activity_viewer)
        binding.viewModel = ViewerViewModel(intent.data?.path, intent.data?.encodedQuery)
    }
}