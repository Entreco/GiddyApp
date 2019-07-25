package nl.entreco.giddyapp.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import nl.entreco.giddyapp.libcore.base.BaseActivity
import nl.entreco.giddyapp.libcore.base.viewModelProvider
import nl.entreco.giddyapp.libcore.di.DiProvider
import nl.entreco.giddyapp.libcore.launch.features.CREATOR_INSTALL_CODE
import nl.entreco.giddyapp.profile.databinding.ActivityProfileBinding
import nl.entreco.giddyapp.profile.di.ProfileComponent
import nl.entreco.giddyapp.profile.di.ProfileInjector.fromModule
import nl.entreco.giddyapp.profile.di.ProfileModule

class ProfileActivity : BaseActivity(), DiProvider<ProfileComponent> {

    private val component by fromModule { ProfileModule }
    private val viewModel by viewModelProvider { component.viewModel() }
    private val navigation by lazy { component.navigation() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeProfile()
        observeClicks()

        val binding = DataBindingUtil.setContentView<ActivityProfileBinding>(this, R.layout.activity_profile)
        binding.viewModel = viewModel
        binding.navigation = navigation

        setSupportActionBar(binding.includeToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun observeProfile() {
        viewModel.state().observe(this, Observer { profile ->
            navigation.onStateChanged(profile)
        })
    }

    private fun observeClicks() {
        viewModel.clicks().observe(this, Observer { item ->
            item?.let {
                navigation.onProfileItemClicked(item)
            }
            viewModel.removeClicks()
        })
    }

    override fun get() = component

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.menu_logout -> navigation.onLogout()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CREATOR_INSTALL_CODE) {
            // Handle the user's decision. For example, if the user selects "Cancel",
            // you may want to disable certain functionality that depends on the module.
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "User cancelled", Toast.LENGTH_LONG).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}