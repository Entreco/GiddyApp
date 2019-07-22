package nl.entreco.giddyapp.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import nl.entreco.giddyapp.profile.databinding.ProfileItemBinding
import nl.entreco.giddyapp.profile.profile.ProfileItem

object ProfileBinding {

    @JvmStatic
    @BindingAdapter("menuItems")
    fun showMenuItems(viewGroup: ViewGroup, items: List<ProfileItem>?) {
        val inflater = LayoutInflater.from(viewGroup.context)
        viewGroup.removeAllViews()
        items?.forEach { item ->
            val binding = ProfileItemBinding.inflate(inflater)
            binding.item = item
            viewGroup.addView(binding.root)
        }
    }
}