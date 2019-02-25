package nl.entreco.giddyapp.creator.ui.crop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import nl.entreco.giddyapp.core.base.viewModelProvider
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.R
import nl.entreco.giddyapp.creator.databinding.FragmentCropBinding
import nl.entreco.giddyapp.creator.di.CreatorInjector.componentFromSheet
import nl.entreco.giddyapp.creator.ui.CreateStepFragment

class CropFragment : CreateStepFragment() {

    private lateinit var binding: FragmentCropBinding
    private val component by componentFromSheet { binding.includeSheet.cropSheet }
    private val viewModel by viewModelProvider { component.crop() }
    private val sheet by lazy { component.sheet() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_crop,
            container,
            false
        )
        sheet.collapse()
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onEvents(CreatorState.Event.Resize) {
            viewModel.resize(binding.cropView) {
                parentViewModel.imageCropped(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sheet.expand()
    }
}