package nl.entreco.giddyapp.history

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import nl.entreco.giddyapp.history.HistoryItem
import nl.entreco.giddyapp.history.databinding.ListHistoryItemBinding
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.libimg.loader.ImageLoader

data class HistoryViewHolder(
    private val binding: ListHistoryItemBinding,
    private val imageLoader: ImageLoader
) : RecyclerView.ViewHolder(binding.root) {

    internal val imageView: ImageView = binding.matchImage

    fun bind(
        detail: HistoryItem,
        onClick: (UserLike) -> Unit
    ) {
        binding.detail = detail
        binding.loader = imageLoader
        binding.executePendingBindings()

        binding.root.setOnClickListener {
            onClick(detail.userLike)
        }
    }
}