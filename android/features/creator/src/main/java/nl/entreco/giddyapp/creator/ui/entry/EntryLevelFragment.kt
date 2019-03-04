package nl.entreco.giddyapp.creator.ui.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.R
import nl.entreco.giddyapp.creator.databinding.FragmentEntryLevelBinding
import nl.entreco.giddyapp.creator.di.CreatorInjector.componentFromSheet
import nl.entreco.giddyapp.creator.ui.CreateStepFragment
import nl.entreco.giddyapp.libcore.base.viewModelProvider

class EntryLevelFragment : CreateStepFragment() {

    private lateinit var binding: FragmentEntryLevelBinding
    private val component by componentFromSheet { binding.entrySheet }
    private val viewModel by viewModelProvider { component.enterLevel() }
    private val sheet by lazy { component.sheet() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_entry_level,
            container,
            false
        )
        binding.viewModel = viewModel
        sheet.slideListener { offset ->
            viewModel.constraint.set(offset)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sheet.expand()
        onEvents(CreatorState.Event.EnterLevel::class) {
            parentViewModel.entered(viewModel.compose())
        }
    }
}