package nl.entreco.giddyapp.profile.header

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import nl.entreco.giddyapp.profile.databinding.FragmentProfileHeaderLoadingBinding

class ProfileHeaderLoadingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentProfileHeaderLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }
}