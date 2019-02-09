package nl.entreco.giddyapp.creator.ui.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import nl.entreco.giddyapp.core.base.parentViewModelProvider
import nl.entreco.giddyapp.core.base.viewModelProvider
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.CreatorViewModel
import nl.entreco.giddyapp.creator.R
import nl.entreco.giddyapp.creator.databinding.FragmentSelectBinding
import nl.entreco.giddyapp.creator.di.CreatorInjector.fromActivity
import nl.entreco.giddyapp.creator.di.StepsModule

class SelectFragment : Fragment() {

    private val parentViewModel by parentViewModelProvider { CreatorViewModel::class.java }
    private val component by fromActivity { StepsModule(parentViewModel.state().value) }
    private val viewModel by viewModelProvider { component.select() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentSelectBinding>(inflater,
            R.layout.fragment_select, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentViewModel.postEvent(CreatorState.Event.Expand)
    }
}