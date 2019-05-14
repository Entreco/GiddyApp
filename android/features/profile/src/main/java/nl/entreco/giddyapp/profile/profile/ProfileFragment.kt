package nl.entreco.giddyapp.profile.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import nl.entreco.giddyapp.libcore.base.parentViewModelProvider
import nl.entreco.giddyapp.libcore.di.DiProvider
import nl.entreco.giddyapp.profile.ProfileNavigation
import nl.entreco.giddyapp.profile.ProfileViewModel
import nl.entreco.giddyapp.profile.databinding.FragmentProfileBinding
import nl.entreco.giddyapp.profile.di.ProfileComponent

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by parentViewModelProvider { ProfileViewModel::class.java }
    private val component: ProfileComponent by lazy { (activity as DiProvider<ProfileComponent>).get() }
    private val navigation: ProfileNavigation by lazy { component.navigation() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.profile = viewModel.state().value
        return binding.root
    }
}