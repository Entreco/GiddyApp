package nl.entreco.giddyapp.profile.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import nl.entreco.giddyapp.libcore.base.parentViewModelProvider
import nl.entreco.giddyapp.profile.ProfileViewModel
import nl.entreco.giddyapp.profile.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private val profileViewModel by parentViewModelProvider { ProfileViewModel::class.java }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.viewModel = profileViewModel
        return binding.root
    }
}