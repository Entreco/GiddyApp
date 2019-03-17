package nl.entreco.giddyapp.viewer.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import nl.entreco.giddyapp.viewer.R
import nl.entreco.giddyapp.viewer.databinding.FragmentFilterBinding

class FilterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentFilterBinding>(inflater, R.layout.fragment_filter, container, false)
        return binding.root
    }
}