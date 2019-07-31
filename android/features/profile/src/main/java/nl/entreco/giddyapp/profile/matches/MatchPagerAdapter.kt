package nl.entreco.giddyapp.profile.matches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.libimg.loader.ImageLoader
import nl.entreco.giddyapp.profile.databinding.ViewMatchItemBinding

class MatchPagerAdapter(
    private val imageLoader: ImageLoader,
    private val onClick: (UserLike)->Unit
) : RecyclerView.Adapter<MatchViewHolder>() {

    private val items by lazy { mutableListOf<UserLike>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewMatchItemBinding.inflate(inflater, parent, false)
        return MatchViewHolder(binding, imageLoader)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(items[position], onClick)
    }

    override fun getItemCount(): Int = items.size

    fun postItems(list: List<UserLike>?) {
        val origSize = items.size
        items.clear()
        notifyItemRangeChanged(0, origSize)

        items.addAll(list ?: emptyList())
        notifyItemRangeInserted(0, items.size)
    }
}