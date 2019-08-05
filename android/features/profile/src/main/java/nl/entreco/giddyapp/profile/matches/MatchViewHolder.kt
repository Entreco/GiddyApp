package nl.entreco.giddyapp.profile.matches

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.libimg.loader.ImageLoader
import nl.entreco.giddyapp.profile.databinding.ViewMatchItemBinding

data class MatchViewHolder(
    private val binding: ViewMatchItemBinding,
    private val imageLoader: ImageLoader
) : RecyclerView.ViewHolder(binding.root) {

    internal val imageView : ImageView = binding.matchImage

    fun bind(
        detail: MatchDetailItem,
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