package nl.entreco.giddyapp.creator.ui.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import nl.entreco.giddyapp.libcore.base.viewModelProvider
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.R
import nl.entreco.giddyapp.creator.databinding.FragmentSelectBinding
import nl.entreco.giddyapp.creator.di.CreatorInjector.componentFromSheet
import nl.entreco.giddyapp.creator.ui.CreateStepFragment

class SelectFragment : CreateStepFragment() {

    private lateinit var binding : FragmentSelectBinding
    private val component by componentFromSheet { binding.includeSheet.selectSheet }
    private val viewModel by viewModelProvider { component.select() }
    private val sheet by lazy{ component.sheet() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_select, container, false)
        binding.viewModel = viewModel
        binding.callback = parentViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onEvents(CreatorState.Event.PickCamera::class) {
            viewModel.step.rejected.set("")
            sheet.collapse()
        }
        onEvents( CreatorState.Event.PickGallery::class) {
            viewModel.step.rejected.set("")
            sheet.collapse()
        }
        onEvents(CreatorState.Event.Rejected::class){
            viewModel.step.rejected.set(context?.getString(R.string.sheet_rejected, it.reason))
        }
    }

    override fun onResume() {
        super.onResume()
        sheet.expand()
    }
}