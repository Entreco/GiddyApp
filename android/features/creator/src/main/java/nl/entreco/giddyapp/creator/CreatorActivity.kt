package nl.entreco.giddyapp.creator

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import nl.entreco.giddyapp.core.ComponentProvider
import nl.entreco.giddyapp.core.LaunchHelper
import nl.entreco.giddyapp.core.base.BaseActivity
import nl.entreco.giddyapp.core.base.viewModelProvider
import nl.entreco.giddyapp.creator.databinding.ActivityCreatorBinding
import nl.entreco.giddyapp.creator.di.CreatorComponent
import nl.entreco.giddyapp.creator.di.CreatorInjector.fromModule
import nl.entreco.giddyapp.creator.di.CreatorModule
import nl.entreco.giddyapp.creator.ui.bottom.BottomProgressModel
import nl.entreco.giddyapp.creator.ui.bottom.BottomStepModel
import nl.entreco.giddyapp.creator.ui.crop.CropFragment
import nl.entreco.giddyapp.creator.ui.entry.EntryFragment
import nl.entreco.giddyapp.creator.ui.select.SelectFragment
import nl.entreco.giddyapp.creator.ui.upload.UploadFragment

class CreatorActivity() : BaseActivity(), ComponentProvider<CreatorComponent> {

    private lateinit var binding : ActivityCreatorBinding
    private val component by fromModule { CreatorModule( binding.includeSheet.sheet) }
    private val viewModel by viewModelProvider { component.viewModel() }
    private val picker by lazy { component.picker() }
    private val sheet by lazy { component.sheet() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_creator)
        binding.viewModel = viewModel
        sheet.slideListener = viewModel
        viewModel.state().observe(this, stateObserver)
        viewModel.events().observe(this, eventObserver)
    }

    private val stateObserver = Observer<CreatorState> { state ->
        viewModel.currentState.set(BottomProgressModel(state))
        viewModel.currentStep.set(BottomStepModel(state))
        render(state)
    }

    private val eventObserver = Observer<CreatorState.Event> { event ->
        when(event){
            is CreatorState.Event.Collapse -> sheet.collapse()
            is CreatorState.Event.Expand -> sheet.expand()
            is CreatorState.Event.PickCamera -> picker.selectImage(true)
            is CreatorState.Event.PickGallery -> picker.selectImage(false)
        }
    }

    override fun get(): CreatorComponent {
        return component
    }

    private fun render(state: CreatorState?) {
        when (state) {
            is CreatorState.Select -> replaceWith(SelectFragment(), state.toString())
            is CreatorState.Crop -> replaceWith(CropFragment(), state.toString())
            is CreatorState.Entry -> replaceWith(EntryFragment(), state.toString())
            is CreatorState.Upload -> replaceWith(UploadFragment(), state.toString())
            is CreatorState.Done -> LaunchHelper.launchViewer(this, null, state.horseId)
        }
    }

    private fun replaceWith(frag: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.createFragmentContainer, frag, tag)
            .commitNowAllowingStateLoss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        picker.get(requestCode, resultCode, data) { images ->
            viewModel.imageSelected(images)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}