package nl.entreco.giddyapp.profile.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import nl.entreco.giddyapp.libcore.base.parentViewModelProvider
import nl.entreco.giddyapp.libcore.base.viewModelProvider
import nl.entreco.giddyapp.libcore.di.DiProvider
import nl.entreco.giddyapp.libimg.loader.ImageLoader
import nl.entreco.giddyapp.profile.ProfileViewModel
import nl.entreco.giddyapp.profile.databinding.FragmentMatchesBinding
import nl.entreco.giddyapp.profile.di.ProfileComponent

class MatchFragment : Fragment() {

    private val profileViewModel by parentViewModelProvider { ProfileViewModel::class.java }
    private val component by lazy { (activity as DiProvider<ProfileComponent>).get() }
    private val viewModel by viewModelProvider { component.matches() }
    private val loader: ImageLoader by lazy { component.loader() }
    private val adapter by lazy { MatchPagerAdapter(loader) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentMatchesBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setupPager(binding.includeMatches.profileMatchRecycler)
        observeProfile()
        observeMatches()

        return binding.root
    }

    private fun observeMatches() {
        viewModel.matches().observe(this, Observer { list ->
            adapter.postItems(list)
        })
    }

    private fun observeProfile() {
        profileViewModel.state().observe(this, Observer { profile ->
            viewModel.retrieveMatches(profile)
        })
    }

    private fun setupPager(recycler: RecyclerView) {
        recycler.adapter = adapter
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recycler)
        recycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

}