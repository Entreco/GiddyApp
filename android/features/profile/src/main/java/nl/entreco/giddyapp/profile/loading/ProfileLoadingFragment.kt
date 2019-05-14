package nl.entreco.giddyapp.profile.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import nl.entreco.giddyapp.profile.databinding.FragmentLoadingBinding

class ProfileLoadingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }
}