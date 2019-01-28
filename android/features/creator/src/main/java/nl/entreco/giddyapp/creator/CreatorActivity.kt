package nl.entreco.giddyapp.creator

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import nl.entreco.giddyapp.core.base.BaseActivity
import nl.entreco.giddyapp.core.base.viewModelProvider
import nl.entreco.giddyapp.creator.databinding.ActivityCreatorBinding
import nl.entreco.giddyapp.creator.di.CreatorInjector.fromModule
import nl.entreco.giddyapp.creator.di.CreatorModule

class CreatorActivity : BaseActivity() {

    private val component by fromModule { CreatorModule() }
    private val viewModel by viewModelProvider { component.viewModel() }
    private val picker by lazy { component.picker() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityCreatorBinding>(this, R.layout.activity_creator)
        binding.viewModel = viewModel
        binding.picker = picker
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        picker.get(requestCode, resultCode, data){ images ->
            viewModel.selectedImages(images)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}