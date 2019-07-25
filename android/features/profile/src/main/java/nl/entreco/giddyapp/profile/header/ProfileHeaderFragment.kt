package nl.entreco.giddyapp.profile.header

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import nl.entreco.giddyapp.libcore.base.parentViewModelProvider
import nl.entreco.giddyapp.libcore.base.viewModelProvider
import nl.entreco.giddyapp.libcore.di.DiProvider
import nl.entreco.giddyapp.profile.ProfileViewModel
import nl.entreco.giddyapp.profile.R
import nl.entreco.giddyapp.profile.databinding.FragmentProfileHeaderBinding
import nl.entreco.giddyapp.profile.di.ProfileComponent

class ProfileHeaderFragment : Fragment() {

    private val profileViewModel by parentViewModelProvider { ProfileViewModel::class.java }
    private val component by lazy { (activity as DiProvider<ProfileComponent>).get() }
    private val viewModel by viewModelProvider { component.header() }
    private val loader by lazy { component.loader() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Log.i("PROFILE", "PROFILE ProfileHeaderFragment($this) onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentProfileHeaderBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.loader = loader

        observeProfile()

        return binding.root
    }

    private fun observeProfile() {
        profileViewModel.state().observe(viewLifecycleOwner, Observer { profile ->
            viewModel.onProfileChanged(profile, resources)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("PROFILE", "PROFILE ProfileHeaderFragment($this) onDestroy")
    }
}