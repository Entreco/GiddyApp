package nl.entreco.giddyapp.viewer

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import nl.entreco.giddyapp.GiddyApp
import nl.entreco.giddyapp.core.ApiModule
import nl.entreco.giddyapp.core.BaseActivity
import nl.entreco.giddyapp.viewer.databinding.ActivityViewerBinding

class ViewerActivity : BaseActivity() {

    private val featureComponent by lazy { (applicationContext as GiddyApp).get() }
    private val apiComponent by lazy { featureComponent.plus(ApiModule()) }

    private val component by lazy {
        DaggerViewerComponent.builder()
            .apiComponent(apiComponent)
            .viewerModule(ViewerModule(intent.data?.path))
            .build()
    }

    private val viewModel by viewModelProvider { component.viewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityViewerBinding>(this, R.layout.activity_viewer)
        binding.viewModel = viewModel
    }
}