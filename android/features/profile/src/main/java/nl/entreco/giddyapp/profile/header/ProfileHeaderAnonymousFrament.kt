package nl.entreco.giddyapp.profile.header

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import nl.entreco.giddyapp.libcore.base.viewModelProvider
import nl.entreco.giddyapp.libcore.di.DiProvider
import nl.entreco.giddyapp.profile.ProfileNavigation
import nl.entreco.giddyapp.profile.databinding.FragmentProfileHeaderAnonymousBinding
import nl.entreco.giddyapp.profile.di.ProfileComponent

class ProfileHeaderAnonymousFrament : Fragment() {

    private val component: ProfileComponent by lazy { (activity as DiProvider<ProfileComponent>).get() }
    private val viewModel: ProfileHeaderViewModel by viewModelProvider { component.header() }
    private val navigator: ProfileNavigation by lazy { component.navigation() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentProfileHeaderAnonymousBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.navigator = navigator
        return binding.root
    }
}