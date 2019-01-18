package nl.entreco.giddyapp.viewer

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import nl.entreco.giddyapp.core.ComponentProvider
import nl.entreco.giddyapp.core.base.BaseActivity
import nl.entreco.giddyapp.core.base.viewModelProvider
import nl.entreco.giddyapp.viewer.databinding.ActivityViewerBinding
import nl.entreco.giddyapp.viewer.di.ViewerComponent
import nl.entreco.giddyapp.viewer.di.ViewerInjector.fromModule
import nl.entreco.giddyapp.viewer.di.ViewerModule
import nl.entreco.giddyapp.viewer.ui.swiper.OnSwipedListener
import nl.entreco.giddyapp.viewer.ui.swiper.SwipeFragment

class ViewerActivity : BaseActivity(), ComponentProvider<ViewerComponent>, OnSwipedListener {

    private lateinit var binding : ActivityViewerBinding
    private val component by fromModule { ViewerModule(intent.data?.lastPathSegment, binding.includeSheet.sheet) }
    private val viewModel by viewModelProvider { component.viewModel() }
    private val sheet by lazy { component.sheet() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_viewer)
        binding.viewModel = viewModel
        sheet.slideListener = viewModel

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.swipeFragmentContainer, SwipeFragment(), "swipe")
                .commitAllowingStateLoss()
        }
    }

    override fun get(): ViewerComponent {
        return component
    }

    override fun onNext() {
        viewModel.onNext()
        supportFragmentManager.beginTransaction()
            .replace(R.id.swipeFragmentContainer, SwipeFragment(), "Swipe")
            .commitAllowingStateLoss()
    }
}