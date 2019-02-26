package nl.entreco.giddyapp.viewer

import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import nl.entreco.giddyapp.core.ComponentProvider
import nl.entreco.giddyapp.core.base.BaseActivity
import nl.entreco.giddyapp.core.base.viewModelProvider
import nl.entreco.giddyapp.core.observeOnce
import nl.entreco.giddyapp.viewer.databinding.ActivityViewerBinding
import nl.entreco.giddyapp.viewer.di.ViewerComponent
import nl.entreco.giddyapp.viewer.di.ViewerInjector.fromModule
import nl.entreco.giddyapp.viewer.di.ViewerModule
import nl.entreco.giddyapp.viewer.ui.swiper.OnSwipedListener
import nl.entreco.giddyapp.viewer.ui.swiper.SwipeFragment

class ViewerActivity : BaseActivity(), ComponentProvider<ViewerComponent>, OnSwipedListener {

    private lateinit var binding: ActivityViewerBinding
    private val component by fromModule { ViewerModule(intent.data?.lastPathSegment, binding) }
    private val viewModel by viewModelProvider { component.viewModel() }
    private val sheet by lazy { component.sheet() }
    private val navigation by lazy { component.navigation() }
    private val animator by lazy { component.animator() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_viewer)
        binding.viewModel = viewModel
        binding.navigation = navigation
        sheet.slideListener(viewModel, animator)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.swipeFragmentContainer, LoaderFragment(), "loader")
                .commitAllowingStateLoss()
        }

        viewModel.current().observeOnce(this, Observer {
            nextFragment()
        })
    }

    private fun nextFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.swipeFragmentContainer, SwipeFragment(), "Swipe")
            .commitAllowingStateLoss()
    }

    override fun onResume() {
        super.onResume()
        setSupportActionBar(binding.includeToolbarViewer.toolbar)
        supportActionBar?.title = ""
    }

    override fun get(): ViewerComponent {
        return component
    }

    override fun onNext() {
        viewModel.onNext()
        nextFragment()
    }
}