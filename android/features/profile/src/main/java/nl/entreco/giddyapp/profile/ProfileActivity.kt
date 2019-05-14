package nl.entreco.giddyapp.profile

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import nl.entreco.giddyapp.libcore.base.BaseActivity
import nl.entreco.giddyapp.libcore.base.viewModelProvider
import nl.entreco.giddyapp.libcore.di.DiProvider
import nl.entreco.giddyapp.libcore.observeOnce
import nl.entreco.giddyapp.profile.databinding.ActivityProfileBinding
import nl.entreco.giddyapp.profile.di.ProfileComponent
import nl.entreco.giddyapp.profile.di.ProfileInjector.fromModule
import nl.entreco.giddyapp.profile.di.ProfileModule

class ProfileActivity : BaseActivity(), DiProvider<ProfileComponent> {

    private lateinit var binding: ActivityProfileBinding
    private val component by fromModule { ProfileModule }
    private val viewModel by viewModelProvider { component.viewModel() }
    private val navigation by lazy { component.navigation() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.viewModel = viewModel
        binding.navigation = navigation
        viewModel.state().observe(this, Observer { profile ->
            navigation.onStateChanged(profile)
        })
        viewModel.clicks().observe(this, Observer { item ->
            navigation.onProfileItemClicked(item)
        })
        setSupportActionBar(binding.includeToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun get() = component

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}