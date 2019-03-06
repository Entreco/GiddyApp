package nl.entreco.giddyapp.creator.ui.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.R
import nl.entreco.giddyapp.creator.databinding.FragmentEntryDescriptionBinding
import nl.entreco.giddyapp.creator.di.CreatorInjector.componentFromSheet
import nl.entreco.giddyapp.creator.ui.CreateStepFragment
import nl.entreco.giddyapp.libcore.base.viewModelProvider

class EntryDescriptionFragment : CreateStepFragment() {

    private lateinit var binding: FragmentEntryDescriptionBinding
    private val component by componentFromSheet { binding.entrySheet }
    private val viewModel by viewModelProvider { component.enterDescription() }
    private val sheet by lazy { component.sheet() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_entry_description,
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
        sheet.expand(true)
        setupKeyboard()
        onEvents(CreatorState.Event.EnterDesc::class) {
            parentViewModel.enteredDescription(viewModel.compose())
        }
    }

    private fun setupKeyboard() {
        binding.entryDescription.requestFocus()
        binding.entryDescription.setOnEditorActionListener { _, _, _ ->
            parentViewModel.enteredDescription(viewModel.compose())
            true
        }
        val imm = activity?.getSystemService(InputMethodManager::class.java)
        imm?.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}