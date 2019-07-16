package nl.entreco.giddyapp.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import nl.entreco.giddyapp.libauth.account.SignupResponse
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

        startActivityForResult(viewModel.intent(), RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            viewModel.handleResult(this, resultCode, data) { response ->
                when (response.ok) {
                    true -> finish()
                    else -> Toast.makeText(this, "Unable to signin", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 1234
    }
}