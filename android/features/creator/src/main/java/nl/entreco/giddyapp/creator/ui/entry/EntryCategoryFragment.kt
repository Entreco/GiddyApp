package nl.entreco.giddyapp.creator.ui.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.R
import nl.entreco.giddyapp.creator.databinding.FragmentEntryCategoryBinding
import nl.entreco.giddyapp.creator.di.CreatorInjector.componentFromSheet
import nl.entreco.giddyapp.creator.ui.CreateStepFragment
import nl.entreco.giddyapp.libcore.base.viewModelProvider

class EntryCategoryFragment : CreateStepFragment() {

    private lateinit var binding: FragmentEntryCategoryBinding
    private val component by componentFromSheet { binding.entrySheet }
    private val viewModel by viewModelProvider { component.enterCategory() }
    private val sheet by lazy { component.sheet() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_entry_category,
            container,
            false
        )
        binding.viewModel = viewModel
        sheet.slideListener { offset ->
            viewModel.constraint.set(offset)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sheet.expand()
        onEvents(CreatorState.Event.EnterCategory::class) {
            parentViewModel.enteredCategory(viewModel.compose())
        }
    }
}