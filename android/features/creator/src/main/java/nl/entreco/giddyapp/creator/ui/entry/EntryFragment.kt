package nl.entreco.giddyapp.creator.ui.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import nl.entreco.giddyapp.core.base.parentViewModelProvider
import nl.entreco.giddyapp.core.base.viewModelProvider
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.CreatorViewModel
import nl.entreco.giddyapp.creator.R
import nl.entreco.giddyapp.creator.databinding.FragmentEntryBinding
import nl.entreco.giddyapp.creator.di.CreatorInjector.fromActivity
import nl.entreco.giddyapp.creator.di.StepsModule

class EntryFragment : Fragment() {

    private val parentViewModel by parentViewModelProvider { CreatorViewModel::class.java }
    private val component by fromActivity { StepsModule(parentViewModel.state().value) }
    private val viewModel by viewModelProvider { component.entry() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentEntryBinding>(
            inflater,
            R.layout.fragment_entry,
            container, false
        )
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.events().observe(this, Observer {
            parentViewModel.postEvent(it)
        })
        parentViewModel.toggler().observe(this, toggleObserver)
        parentViewModel.events().observe(this, parentEventObserver)
    }

    private val toggleObserver = Observer<Float> { offset ->
        viewModel.constraint.set(offset)
    }

    private var parentEventObserver =  Observer<CreatorState.Event> { event ->
        when (event) {
            is CreatorState.Event.Enter -> viewModel.compose() {
                parentViewModel.entered(it)
            }
        }
    }
}