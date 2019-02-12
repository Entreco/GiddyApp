package nl.entreco.giddyapp.creator.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import nl.entreco.giddyapp.core.base.parentViewModelProvider
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.CreatorViewModel

abstract class CreateStepFragment : Fragment() {
    val parentViewModel by parentViewModelProvider { CreatorViewModel::class.java }
    private var parentEventObserver = Observer<CreatorState.Event> { event ->
        if (listenToEvents.contains(event)) {
            onEvent(event)
        }
    }

    private var onEvent: (CreatorState.Event) -> Unit = {}
    private var listenToEvents = emptyList<CreatorState.Event>()
    protected fun onEvents(vararg events: CreatorState.Event, f: (CreatorState.Event) -> Unit) {
        onEvent = f
        listenToEvents = events.map { it }
        parentViewModel.events().observe(this, parentEventObserver)
    }
}