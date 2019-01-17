package nl.entreco.giddyapp.viewer.swiper

import android.os.Bundle
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

    private lateinit var binding: FragmentSwipeBinding
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
        viewModel.constraint.set(offset)
    }

    private val currentObserver = Observer<Horse> { horse ->
        parentViewModel.onCollapse()
        viewModel.current.set(SwipeHorseModel(horse, true))
    }

    private val nextObserver = Observer<Horse> { horse ->
        viewModel.next.set(SwipeHorseModel(horse))
    }
}