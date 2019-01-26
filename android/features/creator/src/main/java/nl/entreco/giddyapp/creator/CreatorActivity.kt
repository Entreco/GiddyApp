package nl.entreco.giddyapp.creator

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import nl.entreco.giddyapp.core.base.BaseActivity
import nl.entreco.giddyapp.core.base.viewModelProvider
import nl.entreco.giddyapp.creator.databinding.ActivityCreatorBinding

class CreatorActivity : BaseActivity() {

    private val viewModel by viewModelProvider { CreatorViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityCreatorBinding>(this, R.layout.activity_creator)
        binding.viewModel = viewModel
    }
}