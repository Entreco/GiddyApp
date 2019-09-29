package nl.entreco.giddyapp.history.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import nl.entreco.giddyapp.history.HistoryViewModel
import nl.entreco.giddyapp.history.databinding.FragmentMatchDetailBinding
import nl.entreco.giddyapp.history.di.HistoryComponent
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.libcore.base.parentViewModelProvider
import nl.entreco.giddyapp.libcore.base.viewModelProvider
import nl.entreco.giddyapp.libcore.di.DiProvider
import nl.entreco.giddyapp.libhorses.Horse

class MatchDetailFragment : Fragment() {

    private val matchViewModel by parentViewModelProvider { HistoryViewModel::class.java }
    private val component by lazy { (activity as DiProvider<HistoryComponent>).get() }
    private val viewModel by viewModelProvider { component.detailViewModel() }
    private val adapter by lazy { component.adapter() }
    private val loader by lazy { component.loader() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMatchDetailBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.loader = loader

        setupRecycler(binding)
        listenToHorseDetails()

        return binding.root
    }

    private fun listenToHorseDetails() {
        matchViewModel.details().observe(viewLifecycleOwner, Observer { details ->
            when (val horse = details[arguments?.getString(arg_like)]) {
                is Horse.Normal -> viewModel.show(horse.details)
                else -> viewModel.showLoading()
            }
        })
    }

    private fun setupRecycler(binding: FragmentMatchDetailBinding) {
        binding.detailRecycler.adapter = adapter
        binding.detailRecycler.setHasFixedSize(true)
        binding.detailRecycler.itemAnimator = DefaultItemAnimator()
        binding.detailRecycler.layoutManager = LinearLayoutManager(requireContext())
        viewModel.details().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    companion object {

        private const val arg_like = "argument_like"

        fun newInstance(like: UserLike) = MatchDetailFragment().apply {
            arguments = Bundle().apply {
                putString(arg_like, like.horseId)
            }
        }
    }
}