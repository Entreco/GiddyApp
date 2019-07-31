package nl.entreco.giddyapp.profile.matches

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.profile.databinding.FragmentMatchBinding
import kotlin.random.Random

class MatchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentMatchBinding.inflate(inflater, container, false)


        val color = Color.argb(255, Random.nextInt(256),Random.nextInt(256),Random.nextInt(256))
        binding.root.setBackgroundColor(color)

        binding.name.text = arguments?.getString("name")
        binding.ref.text = arguments?.getString("ref")
        binding.id.text = arguments?.getString("id")

        return binding.root
    }

    companion object {
        fun newInstance(like: UserLike): MatchFragment {
            return MatchFragment().apply {
                val bundle = Bundle()
                bundle.putString("name", like.horseName)
                bundle.putString("ref", like.horseRef)
                bundle.putString("id", like.horseId)
                arguments = bundle
            }
        }
    }
}