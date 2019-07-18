package nl.entreco.giddyapp.profile

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import nl.entreco.giddyapp.libcore.base.BaseActivity
import nl.entreco.giddyapp.libcore.base.viewModelProvider
import nl.entreco.giddyapp.libcore.di.DiProvider
import nl.entreco.giddyapp.profile.databinding.ActivityProfileBinding
import nl.entreco.giddyapp.profile.di.ProfileComponent
import nl.entreco.giddyapp.profile.di.ProfileInjector.fromModule
import nl.entreco.giddyapp.profile.di.ProfileModule
import nl.entreco.giddyapp.profile.matches.MatchPagerAdapter

class ProfileActivity : BaseActivity(), DiProvider<ProfileComponent> {

    private val component by fromModule { ProfileModule }
    private val viewModel by viewModelProvider { component.viewModel() }
    private val navigation by lazy { component.navigation() }
    private val loader by lazy { component.loader() }
    private val adapter by lazy { MatchPagerAdapter(loader) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityProfileBinding>(this, R.layout.activity_profile)
        binding.viewModel = viewModel
        binding.navigation = navigation
//        setupPager(binding.includeMatches.profileMatchRecycler)

        viewModel.state().observe(this, Observer { profile ->
            navigation.onStateChanged(profile)
        })
        viewModel.clicks().observe(this, Observer { item ->
            navigation.onProfileItemClicked(item)
        })
        viewModel.matches().observe(this, Observer { list ->
            adapter.postItems(list)
        })
        setSupportActionBar(binding.includeToolbar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupPager(recycler: RecyclerView) {
        recycler.adapter = adapter
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recycler)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun get() = component

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.menu_logout -> navigation.onLogout()
        }
        return super.onOptionsItemSelected(item)
    }
}