package nl.entreco.giddyapp.creator.ui.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import nl.entreco.giddyapp.core.base.viewModelProvider
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.R
import nl.entreco.giddyapp.creator.databinding.FragmentEntryBinding
import nl.entreco.giddyapp.creator.di.CreatorInjector.componentFromSheet
import nl.entreco.giddyapp.creator.ui.CreateStepFragment

class EntryFragment : CreateStepFragment() {

    private lateinit var binding: FragmentEntryBinding
    private val component by componentFromSheet { binding.includeSheet.entrySheet }
    private val viewModel by viewModelProvider { component.entry() }
    private val sheet by lazy { component.sheet() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_entry,
            container, false
        )
        binding.viewModel = viewModel
        sheet.expand()
        sheet.slideListener { offset ->
            viewModel.constraint.set(offset)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.events().observe(this, Observer {
            parentViewModel.postEvent(it)
        })
        onEvents(CreatorState.Event.Enter) {
            sheet.collapse()
            viewModel.compose() {
                parentViewModel.entered(it)
            }
        }
    }
}