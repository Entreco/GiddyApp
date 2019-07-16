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
import nl.entreco.giddyapp.viewer.ui.filter.SearchPanelBehavior
import nl.entreco.giddyapp.viewer.ui.swiper.OnSwipedListener
import nl.entreco.giddyapp.viewer.ui.swiper.SwipeFragment

class ViewerActivity : BaseActivity(), DiProvider<ViewerComponent>, OnSwipedListener {

    private lateinit var binding: ActivityViewerBinding
    private val component by fromModule { ViewerModule(binding) }
    private val viewModel by viewModelProvider { component.viewModel() }
    private val sheet by lazy { component.sheet() }
    private val filter by lazy { component.filter() }
    private val navigation by lazy { component.navigation() }
    private val animator by lazy { component.animator() }
    private val loader by lazy { component.loader() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_viewer)
        binding.viewModel = viewModel
        binding.navigation = navigation
        binding.loader = loader
        sheet.slideListener(viewModel, animator)
        filter.slideListener(object : SearchPanelBehavior.SlideListener {
            override fun onSlide(offset: Float) {
                binding.fab.animate().alpha(1F - offset).setDuration(0).start()
            }
        })
        filter.collapse()

        viewModel.current().observeOnce(Observer {
            nextFragment()
        })

        loadHorse(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        loadHorse(intent)
    }

    private fun loadHorse(intent: Intent?) {
        val url = intent?.data?.lastPathSegment
        val id = when {
            url.isNullOrBlank() -> null
            url == "viewer" -> null
            url == "null" -> null
            else -> url
        }
        viewModel.load(id)
    }

    override fun onResume() {
        super.onResume()
        setSupportActionBar(binding.includeToolbarViewer.toolbar)
        supportActionBar?.title = ""
    }

    override fun onStop() {
        super.onStop()
        viewModel.submitRatings()
    }

    override fun onNext(like: Boolean) {
        viewModel.onNext(like)
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