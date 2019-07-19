package nl.entreco.giddyapp.creator.ui.upload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import nl.entreco.giddyapp.libcore.base.viewModelProvider
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.R
import nl.entreco.giddyapp.creator.databinding.FragmentUploadBinding
import nl.entreco.giddyapp.creator.di.CreatorInjector.componentFromSheet
import nl.entreco.giddyapp.creator.ui.CreateStepFragment

class UploadFragment : CreateStepFragment() {

    private lateinit var binding: FragmentUploadBinding
    private val component by componentFromSheet { binding.includeSheet.uploadSheet }
    private val viewModel by viewModelProvider { component.upload() }
    private val sheet by lazy { component.sheet() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_upload, container, false
        )
        binding.viewModel = viewModel
        sheet.slideListener { offset ->
            viewModel.constraint.set(offset)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onEvents(CreatorState.Event.Verify::class) {
            parentViewModel.startUpload(viewModel.model())
        }
    }
}