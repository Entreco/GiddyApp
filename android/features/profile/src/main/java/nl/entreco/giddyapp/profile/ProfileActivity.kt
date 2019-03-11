package nl.entreco.giddyapp.profile

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import nl.entreco.giddyapp.libcore.base.BaseActivity
import nl.entreco.giddyapp.libcore.base.viewModelProvider
import nl.entreco.giddyapp.profile.databinding.ActivityProfileBinding
import nl.entreco.giddyapp.profile.di.ProfileInjector.fromModule
import nl.entreco.giddyapp.profile.di.ProfileModule

class ProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val component by fromModule { ProfileModule() }
    private val viewModel by viewModelProvider { component.viewModel() }
    private val navigation by lazy { component.navigation() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.viewModel = viewModel
        binding.navigation = navigation

        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .add(R.id.swipeFragmentContainer, LoaderFragment(), "loader")
//                .commitAllowingStateLoss()
        }
        setSupportActionBar(binding.includeToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}