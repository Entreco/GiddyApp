package nl.entreco.giddyapp.viewer.swiper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_swipe.*
import kotlinx.android.synthetic.main.widget_swipe_horse_view.view.*
import nl.entreco.giddyapp.core.base.parentViewModelProvider
import nl.entreco.giddyapp.core.base.viewModelProvider
import nl.entreco.giddyapp.viewer.Horse
import nl.entreco.giddyapp.viewer.R
import nl.entreco.giddyapp.viewer.ViewerViewModel
import nl.entreco.giddyapp.viewer.databinding.FragmentSwipeBinding
import nl.entreco.giddyapp.viewer.di.SwipeModule
import nl.entreco.giddyapp.viewer.di.ViewerInjector.fromActivity

class SwipeFragment : Fragment() {

    private lateinit var binding : FragmentSwipeBinding
    private val parentViewModel by parentViewModelProvider { ViewerViewModel::class.java }
    private val component by fromActivity { SwipeModule() }
    private val viewModel by viewModelProvider { component.viewModel() }
    private val loader by lazy { component.loader() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSwipeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.listener = activity as? OnSwipedListener
        binding.loader = loader

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentViewModel.toggler().observe(this, toggleObserver)
        parentViewModel.current().observe(this, currentObserver)
        parentViewModel.next().observe(this, nextObserver)
    }

    private val toggleObserver = Observer<Float> { offset ->
        val start = resources.getDimension(R.dimen.sheet_height_peek)
        val end = resources.getDimension(R.dimen.sheet_height_expanded)
        val diff = end - start
        val view = current.image
        Log.i("WOAH2", "slide: $offset end:${offset * diff}")
        Log.i("WOAH2", "image: ${view.drawable}")
        Log.i("WOAH2", "image intw: ${view.drawable?.intrinsicWidth}")
        Log.i("WOAH2", "image inth: ${view.drawable?.intrinsicHeight}")
        Log.i("WOAH2", "image: ${view.drawable?.bounds}")
        Log.i("WOAH2", "width: ${view.width} height: ${view.height}")
        Log.i("WOAH2", "bounds: ${view.clipBounds}")
        guide?.setGuidelineEnd((start + (offset * diff)).toInt())
    }

    private val currentObserver = Observer<Horse> { horse ->
        Log.i("CYCLING", "current: $horse $this")
//        parentViewModel.onSlide(400F.dp())
        parentViewModel.onCollapse()
        viewModel.current.set(SwipeHorseModel(horse, true))
    }

    private val nextObserver = Observer<Horse> { horse ->
        Log.i("CYCLING", "next: $horse $this")
        viewModel.next.set(SwipeHorseModel(horse))
    }
}