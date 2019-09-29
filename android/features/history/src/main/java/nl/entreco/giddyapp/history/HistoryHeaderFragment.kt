package nl.entreco.giddyapp.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import nl.entreco.giddyapp.history.databinding.FragmentHistoryHeaderBinding
import nl.entreco.giddyapp.history.di.HistoryComponent
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.libcore.base.parentViewModelProvider
import nl.entreco.giddyapp.libcore.base.viewModelProvider
import nl.entreco.giddyapp.libcore.di.DiProvider
import nl.entreco.giddyapp.libhorses.Horse

class HistoryHeaderFragment : Fragment() {

    private val matchViewModel by parentViewModelProvider { HistoryViewModel::class.java }
    private val component by lazy { (activity as DiProvider<HistoryComponent>).get() }
    private val viewModel by viewModelProvider { component.header() }
    private val loader by lazy { component.loader() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHistoryHeaderBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.loader = loader

        listenToHorseDetails()

        return binding.root
    }

    private fun listenToHorseDetails() {
        matchViewModel.details().observe(viewLifecycleOwner, Observer { details ->
            when (val horse = details[arguments?.getString(arg_like)]) {
                is Horse.Normal -> viewModel.show(horse)
                else -> viewModel.show(Horse.Loading)
            }
        })
    }

    companion object {

        private const val arg_like = "argument_like"

        fun newInstance(like: UserLike) = HistoryHeaderFragment().apply {
            arguments = Bundle().apply {
                putString(arg_like, like.horseId)
            }
        }
    }
}