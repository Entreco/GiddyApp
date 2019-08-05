package nl.entreco.giddyapp.profile.matches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.libimg.loader.ImageLoader
import nl.entreco.giddyapp.libimg.loader.glide.GlideApp
import nl.entreco.giddyapp.profile.databinding.ViewMatchItemBinding

class MatchPagerAdapter(
    private val imageLoader: ImageLoader,
    private val onClick: (UserLike)->Unit
) : RecyclerView.Adapter<MatchViewHolder>() {

    private val items by lazy { mutableListOf<MatchDetailItem>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewMatchItemBinding.inflate(inflater, parent, false)
        return MatchViewHolder(binding, imageLoader)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(items[position], onClick)
    }

    override fun onViewRecycled(holder: MatchViewHolder) {
        super.onViewRecycled(holder)
        imageLoader.clear(holder.imageView)
    }

    override fun getItemCount(): Int = items.size

    fun postItems(list: List<UserLike>?) {
        val origSize = items.size
        items.clear()
        notifyItemRangeChanged(0, origSize)

        items.addAll(list?.map { MatchDetailItem(it, false) } ?: emptyList())
        notifyItemRangeInserted(0, items.size)
    }

    fun setSelectedItem(userLike: UserLike?) {
        deselectOldItem()
        selectItem(userLike)
    }

    private fun deselectOldItem() {
        val oldSelection = items.firstOrNull { it.selected }
        val oldPosition = items.indexOf(oldSelection)
        if (oldPosition >= 0) {
            items[oldPosition] = oldSelection!!.toggleSelected()
            notifyItemChanged(oldPosition)
        }
    }

    private fun selectItem(userLike: UserLike?) {
        val selection = items.firstOrNull { it.horseId == userLike?.horseId }
        val position = items.indexOf(selection)
        if (position >= 0) {
            items[position] = selection!!.toggleSelected()
            notifyItemChanged(position)
        }
    }
}