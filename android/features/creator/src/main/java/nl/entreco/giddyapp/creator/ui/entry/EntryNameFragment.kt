package nl.entreco.giddyapp.creator.ui.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.R
import nl.entreco.giddyapp.creator.databinding.FragmentEntryNameBinding
import nl.entreco.giddyapp.creator.di.CreatorInjector.componentFromSheet
import nl.entreco.giddyapp.creator.ui.CreateStepFragment
import nl.entreco.giddyapp.libcore.base.viewModelProvider

class EntryNameFragment : CreateStepFragment() {

    private lateinit var binding: FragmentEntryNameBinding
    private val component by componentFromSheet { binding.entrySheet }
    private val viewModel by viewModelProvider { component.enterName() }
    private val sheet by lazy { component.sheet() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_entry_name,
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
        sheet.expand(true)
        setupKeyboard()
        onEvents(CreatorState.Event.EnterName::class) {
            parentViewModel.enteredName(viewModel.compose())
        }
    }

    private fun setupKeyboard() {
        binding.nameEntry.requestFocus()
        binding.nameEntry.setOnEditorActionListener { _, _, _ ->
            parentViewModel.enteredName(viewModel.compose())
            true
        }
        val imm = activity?.getSystemService(InputMethodManager::class.java)
        imm?.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}