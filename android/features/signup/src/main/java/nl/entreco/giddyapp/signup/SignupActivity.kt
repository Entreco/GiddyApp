package nl.entreco.giddyapp.signup

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import nl.entreco.giddyapp.libcore.base.BaseActivity
import nl.entreco.giddyapp.libcore.base.viewModelProvider
import nl.entreco.giddyapp.libcore.launch.LaunchHelper
import nl.entreco.giddyapp.signup.databinding.ActivitySignupBinding
import nl.entreco.giddyapp.signup.di.SignupInjector.fromModule
import nl.entreco.giddyapp.signup.di.SignupModule
import nl.entreco.giddyapp.signup.link.LinkAccountResponse

class SignupActivity : BaseActivity() {

    private val component by fromModule { SignupModule }
    private val viewModel by viewModelProvider { component.viewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySignupBinding>(this, R.layout.activity_signup)
        binding.viewModel = viewModel
        viewModel.clicks().observe(this, Observer { intent ->
            startActivityForResult(intent, RC_SIGN_IN)
        })
        setSupportActionBar(binding.includeToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            viewModel.handleResult(this, resultCode, data) { response ->
                when (response) {
                    is LinkAccountResponse.Success -> finish()
                    is LinkAccountResponse.Failed -> Toast.makeText(
                        this,
                        "Unable to signin (${response.msg})",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val RC_SIGN_IN = 1234
    }
}