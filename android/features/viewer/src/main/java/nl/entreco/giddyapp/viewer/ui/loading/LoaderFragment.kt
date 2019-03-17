package nl.entreco.giddyapp.viewer.ui.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import nl.entreco.giddyapp.viewer.databinding.FragmentLoaderBinding

class LoaderFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentLoaderBinding.inflate(inflater, container, false)
        return binding.root
    }
}