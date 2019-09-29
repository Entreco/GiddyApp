package nl.entreco.giddyapp.history

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.history.details.MatchDetailFragment

class DetailsPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val items = mutableListOf<UserLike>()

    override fun getItemCount() = items.size

    override fun createFragment(position: Int): Fragment = MatchDetailFragment.newInstance(items[position])

    fun postItems(list: List<UserLike>?) {
        items.clear()
        items.addAll(list ?: emptyList())
        notifyDataSetChanged()
    }

    fun itemAtPosition(position: Int): UserLike? = items.getOrNull(position)
    fun positionForItem(userLike: UserLike): Int = items.indexOf(userLike)
}