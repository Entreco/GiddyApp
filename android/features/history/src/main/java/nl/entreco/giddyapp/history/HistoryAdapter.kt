package nl.entreco.giddyapp.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nl.entreco.giddyapp.history.databinding.ListHistoryItemBinding
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.libimg.loader.ImageLoader

class HistoryAdapter(
    private val imageLoader: ImageLoader,
    private val onClick: (UserLike) -> Unit
) : RecyclerView.Adapter<HistoryViewHolder>() {

    private val items by lazy { mutableListOf<HistoryItem>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListHistoryItemBinding.inflate(inflater, parent, false)
        return HistoryViewHolder(binding, imageLoader)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(items[position], onClick)
    }

    override fun onViewRecycled(holder: HistoryViewHolder) {
        super.onViewRecycled(holder)
        imageLoader.clear(holder.imageView)
    }

    override fun getItemCount(): Int = items.size

    fun postItems(list: List<UserLike>?) {
        items.clear()
        items.addAll(list?.map { HistoryItem(it, false) } ?: emptyList())
        notifyDataSetChanged()
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