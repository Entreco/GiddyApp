package nl.entreco.giddyapp.viewer

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import nl.entreco.giddyapp.libcore.base.BaseActivity
import nl.entreco.giddyapp.libcore.base.viewModelProvider
import nl.entreco.giddyapp.libcore.di.DiProvider
import nl.entreco.giddyapp.libcore.observeOnce
import nl.entreco.giddyapp.viewer.databinding.ActivityViewerBinding
import nl.entreco.giddyapp.viewer.di.ViewerComponent
import nl.entreco.giddyapp.viewer.di.ViewerInjector.fromModule
import nl.entreco.giddyapp.viewer.di.ViewerModule
import nl.entreco.giddyapp.viewer.ui.filter.FilterPanel
import nl.entreco.giddyapp.viewer.ui.swiper.OnSwipedListener
import nl.entreco.giddyapp.viewer.ui.swiper.SwipeFragment

class ViewerActivity : BaseActivity(), DiProvider<ViewerComponent>, OnSwipedListener {

    private lateinit var binding: ActivityViewerBinding
    private val component by fromModule { ViewerModule(intent.data?.lastPathSegment, binding) }
    private val viewModel by viewModelProvider { component.viewModel() }
    private val sheet by lazy { component.sheet() }
    private val filter by lazy { component.filter() }
    private val navigation by lazy { component.navigation() }
    private val animator by lazy { component.animator() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_viewer)
        binding.viewModel = viewModel
        binding.navigation = navigation
        sheet.slideListener(viewModel, animator)
        filter.slideListener(object : FilterPanel.SlideListener {
            override fun onSlide(offset: Float) {
                binding.fab.animate().alpha(1F - offset).setDuration(0).start()
            }
        })
        filter.collapse()

        viewModel.current().observeOnce(Observer {
            nextFragment()
        })
    }

    override fun onResume() {
        super.onResume()
        setSupportActionBar(binding.includeToolbarViewer.toolbar)
        supportActionBar?.title = ""
    }

    override fun onNext() {
        viewModel.onNext()
        nextFragment()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // if Install Intent -> reload and update (e)FAB's
        binding.eFab.extend(true)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun get(): ViewerComponent = component

    private fun nextFragment() =
        supportFragmentManager.beginTransaction()
            .replace(R.id.swipeFragmentContainer, SwipeFragment(), "Swipe")
            .commitAllowingStateLoss()
}