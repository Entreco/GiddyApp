package nl.entreco.giddyapp.profile.header

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import nl.entreco.giddyapp.libcore.base.parentViewModelProvider
import nl.entreco.giddyapp.libcore.base.viewModelProvider
import nl.entreco.giddyapp.libcore.di.DiProvider
import nl.entreco.giddyapp.libimg.loader.ImageLoader
import nl.entreco.giddyapp.profile.ProfileNavigation
import nl.entreco.giddyapp.profile.ProfileViewModel
import nl.entreco.giddyapp.profile.R
import nl.entreco.giddyapp.profile.databinding.FragmentProfileHeaderBinding
import nl.entreco.giddyapp.profile.di.ProfileComponent

class ProfileHeaderFragment : Fragment() {

    private val profileViewModel by parentViewModelProvider { ProfileViewModel::class.java }
    private val component: ProfileComponent by lazy { (activity as DiProvider<ProfileComponent>).get() }
    private val viewModel: ProfileHeaderViewModel by viewModelProvider { component.header() }
    private val navigation: ProfileNavigation by lazy { component.navigation() }
    private val loader: ImageLoader by lazy { component.loader() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentProfileHeaderBinding.inflate(inflater, container, false)

        observeProfile()

        binding.viewModel = viewModel
        binding.navigator = navigation
        binding.loader = loader
        return binding.root
    }

    private fun observeProfile() {
        profileViewModel.state().observe(this, Observer { profile ->
            viewModel.onProfileChanged(profile, resources)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}