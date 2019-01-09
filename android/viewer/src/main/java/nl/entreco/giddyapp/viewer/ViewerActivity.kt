package nl.entreco.giddyapp.viewer

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import nl.entreco.giddyapp.core.base.BaseActivity
import nl.entreco.giddyapp.core.base.viewModelProvider
import nl.entreco.giddyapp.viewer.databinding.ActivityViewerBinding
import nl.entreco.giddyapp.viewer.di.ViewerInjector.fromModule
import nl.entreco.giddyapp.viewer.di.ViewerModule
import nl.entreco.giddyapp.viewer.swiper.SwipeFragment

class ViewerActivity : BaseActivity() {

    private val component by fromModule { ViewerModule(intent.data?.lastPathSegment) }
    private val viewModel by viewModelProvider { component.viewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityViewerBinding>(this, R.layout.activity_viewer)
        binding.viewModel = viewModel

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(binding.swipeFragmentContainer.id, SwipeFragment(), "swipe")
                .commitAllowingStateLoss()
        }
    }
}