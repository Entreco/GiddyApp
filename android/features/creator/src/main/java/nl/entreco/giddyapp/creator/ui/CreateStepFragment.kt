package nl.entreco.giddyapp.creator.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.CreatorViewModel
import nl.entreco.giddyapp.libcore.base.parentViewModelProvider
import kotlin.reflect.KClass

abstract class CreateStepFragment : Fragment() {
    val parentViewModel by parentViewModelProvider { CreatorViewModel::class.java }

    protected fun <T : CreatorState.Event> onEvents(kclass: KClass<T>, f: (T) -> Unit) {
        parentViewModel.events().observe(this, Observer<CreatorState.Event> { event ->
            if(event::class == kclass) {
                f(event as T)
            }
        })
    }
}