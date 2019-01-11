package nl.entreco.giddyapp.viewer.swiper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import nl.entreco.giddyapp.core.base.parentViewModelProvider
import nl.entreco.giddyapp.core.base.viewModelProvider
import nl.entreco.giddyapp.viewer.Horse
import nl.entreco.giddyapp.viewer.ViewerViewModel
import nl.entreco.giddyapp.viewer.databinding.FragmentSwipeBinding
import nl.entreco.giddyapp.viewer.di.SwipeModule
import nl.entreco.giddyapp.viewer.di.ViewerInjector.fromActivity

class SwipeFragment : Fragment() {

    private val parentViewModel by parentViewModelProvider { ViewerViewModel::class.java }
    private val component by fromActivity { SwipeModule() }
    private val viewModel by viewModelProvider { component.viewModel() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentSwipeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.listener = activity as? OnSwipedListener
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentViewModel.current().observe(this, currentObserver)
        parentViewModel.next().observe(this, nextObserver)
    }

    private val currentObserver = Observer<Horse> { horse ->
        Log.i("CYCLING", "current: $horse $this")
        viewModel.current.set(SwipeHorseModel(horse, true))
    }

    private val nextObserver = Observer<Horse> { horse ->
        Log.i("CYCLING", "next: $horse $this")
        viewModel.next.set(SwipeHorseModel(horse))
    }
}