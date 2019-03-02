package nl.entreco.giddyapp.creator.ui.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import nl.entreco.giddyapp.libcore.base.viewModelProvider
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
    private val keyboard by lazy { component.keyboard() }
    private val pager by lazy { binding.includeSheet.entryPager }
    private val entryData by lazy {
        listOf(
            Form.Name(),
            Form.Desc(),
            Form.Gender(),
            Form.Price(),
            Form.Category(),
            Form.Level()
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_entry,
            container, false
        )
        binding.viewModel = viewModel
        sheet.slideListener { offset ->
            viewModel.constraint.set(offset)
        }
        sheet.expand()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onEvents(CreatorState.Event.Enter::class) {
            proceed()
        }

        pager.adapter = EntryPager(requireContext(), entryData.toMutableList(), object : EntryListeners.OnNextPlease {
            override fun onNext(current: Form) {
                proceed()
            }
        })
    }

    private fun proceed() {
        viewModel.enter(entryData[pager.currentItem])
        keyboard.handle(entryData[pager.currentItem])
        if (pager.currentItem < entryData.size - 1) {
            pager.currentItem++
        } else {
            sheet.collapse()
            viewModel.compose() {
                parentViewModel.entered(it)
            }
        }
    }
}