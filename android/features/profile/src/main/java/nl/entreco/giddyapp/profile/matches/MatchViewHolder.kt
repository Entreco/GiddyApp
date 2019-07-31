package nl.entreco.giddyapp.profile.matches

import androidx.recyclerview.widget.RecyclerView
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.libimg.loader.ImageLoader
import nl.entreco.giddyapp.profile.databinding.ViewMatchItemBinding

data class MatchViewHolder(
    private val binding: ViewMatchItemBinding,
    private val imageLoader: ImageLoader
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        userLike: UserLike,
        onClick: (UserLike) -> Unit
    ) {
        binding.like = userLike
        binding.loader = imageLoader
        binding.executePendingBindings()

        binding.root.setOnClickListener {
            onClick(userLike)
        }
    }
}