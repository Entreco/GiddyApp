package nl.entreco.giddyapp.profile.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import nl.entreco.giddyapp.libcore.base.parentViewModelProvider
import nl.entreco.giddyapp.libcore.base.viewModelProvider
import nl.entreco.giddyapp.libcore.di.DiProvider
import nl.entreco.giddyapp.profile.ProfileViewModel
import nl.entreco.giddyapp.profile.databinding.FragmentMenuBinding
import nl.entreco.giddyapp.profile.di.ProfileComponent

class MenuFragment : Fragment() {

    private val profileViewModel by parentViewModelProvider { ProfileViewModel::class.java }
    private val component by lazy { (activity as DiProvider<ProfileComponent>).get() }
    private val viewModel by viewModelProvider { component.menu() }
    private val navigation by lazy { component.menuNavigation() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("PROFILE", "PROFILE MenuFragment($this) onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentMenuBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        observeProfile()
        observeClicks()
        return binding.root
    }

    private fun observeProfile() {
        profileViewModel.state().observe(viewLifecycleOwner, Observer { profile ->
            viewModel.generateItems(profile.account)
        })
    }

    private fun observeClicks() {
        viewModel.clicks().observe(viewLifecycleOwner, Observer { item ->
            item?.let {
                navigation.onProfileItemClicked(item)
            }
            viewModel.clearClicks()
        })
    }

    override fun onPause() {
        super.onPause()
        Log.i("PROFILE", "PROFILE MenuFragment($this) onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("PROFILE", "PROFILE MenuFragment($this) onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("PROFILE", "PROFILE MenuFragment($this) onDestroy")
    }
}