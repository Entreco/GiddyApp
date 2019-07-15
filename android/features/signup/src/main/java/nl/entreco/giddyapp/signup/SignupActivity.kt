package nl.entreco.giddyapp.signup

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import nl.entreco.giddyapp.libcore.base.BaseActivity
import nl.entreco.giddyapp.libcore.base.viewModelProvider
import nl.entreco.giddyapp.signup.databinding.ActivitySignupBinding
import nl.entreco.giddyapp.signup.di.SignupInjector.fromModule
import nl.entreco.giddyapp.signup.di.SignupModule

class SignupActivity : BaseActivity() {

    private val component by fromModule { SignupModule }
    private val viewModel by viewModelProvider { component.viewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySignupBinding>(this, R.layout.activity_signup)
        binding.viewModel = viewModel
    }
}