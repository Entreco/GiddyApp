package nl.entreco.giddyapp.profile.matches.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.libcore.base.viewModelProvider
import nl.entreco.giddyapp.libcore.di.DiProvider
import nl.entreco.giddyapp.profile.databinding.FragmentMatchDetailBinding
import nl.entreco.giddyapp.profile.di.MatchComponent

class MatchDetailFragment : Fragment() {

    private val component by lazy { (activity as DiProvider<MatchComponent>).get() }
    private val viewModel by viewModelProvider { component.detailViewModel() }
    private val loader by lazy { component.loader() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMatchDetailBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.loader = loader

        viewModel.fetch(arguments?.getString("id"))

        return binding.root
    }

    companion object {
        fun newInstance(like: UserLike): MatchDetailFragment {
            return MatchDetailFragment().apply {
                val bundle = Bundle()
                bundle.putString("name", like.horseName)
                bundle.putString("ref", like.horseRef)
                bundle.putString("id", like.horseId)
                arguments = bundle
            }
        }
    }
}