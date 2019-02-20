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
    private val keyboard by lazy { component.keyboard() }
    private val pager by lazy { binding.includeSheet.entryPager }
    private val entryData by lazy {
        listOf(
            Form.Name(),
            Form.Desc(),
            Form.Gender(),
            Form.Level(),
            Form.Price(),
            Form.Category()
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
        viewModel.events().observe(this, Observer {
            parentViewModel.postEvent(it)
        })
        onEvents(CreatorState.Event.Enter) {
            handleEvent()
        }

        pager.adapter = EntryPager(requireContext(), entryData, object : EntryListeners.OnNextPlease {
            override fun onNext(current: Form) {
                when(current){
                    is Form.Name -> keyboard.show()
                    is Form.Desc -> keyboard.show()
                    else -> keyboard.hide()
                }
                handleEvent()
            }
        })
    }

    private fun handleEvent() {
        viewModel.enter(entryData[pager.currentItem])
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