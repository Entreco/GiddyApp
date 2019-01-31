package nl.entreco.giddyapp.creator.ui.crop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import nl.entreco.giddyapp.core.base.parentViewModelProvider
import nl.entreco.giddyapp.core.base.viewModelProvider
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.CreatorViewModel
import nl.entreco.giddyapp.creator.R
import nl.entreco.giddyapp.creator.databinding.FragmentCropBinding
import nl.entreco.giddyapp.creator.di.CreatorInjector.fromActivity
import nl.entreco.giddyapp.creator.di.StepsModule

class CropFragment : Fragment() {

    private lateinit var binding: FragmentCropBinding
    private val parentViewModel by parentViewModelProvider { CreatorViewModel::class.java }
    private val component by fromActivity { StepsModule(parentViewModel.state().value) }
    private val viewModel by viewModelProvider { component.crop() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_crop,
            container,
            false
        )
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.events().observe(this, Observer {
            parentViewModel.postEvent(it)
        })

        parentViewModel.events().observe(this, Observer {
            when (it) {
                is CreatorState.Event.Resize -> viewModel.resize(binding.croppedImageView.getCroppedImage()) {
                    parentViewModel.imageCropped(it)
                }
            }
        })
    }
}