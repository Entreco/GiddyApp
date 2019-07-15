package nl.entreco.giddyapp.profile.profile

import androidx.annotation.StringRes
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import nl.entreco.giddyapp.profile.R

sealed class ProfileItem(@StringRes name: Int, @StringRes desc: Int, private val onClick: OnClick? = null) {
    val title = ObservableInt(name)
    val description = ObservableInt(desc)
    val enabled = ObservableBoolean(onClick != null)

    fun onClick() {
        onClick?.onClick(this)
    }

    interface OnClick {
        fun onClick(item: ProfileItem)
    }

    companion object {
        fun all(onClick: OnClick): List<ProfileItem> = listOf(
            Creator(onClick),
            About(onClick),
            Settings(onClick)
        )

        fun anonymous(onClick: OnClick): List<ProfileItem> = listOf(
            Creator(),
            About(onClick),
            Settings(onClick)
        )

        fun error() : List<ProfileItem> = listOf(
            Error()
        )
    }

    data class Creator(val _onClick: OnClick? = null) : ProfileItem(R.string.profile_item_creator, R.string.profile_item_creator_description, _onClick)
    data class Error(val _onClick: OnClick? = null) : ProfileItem(R.string.profile_item_error, R.string.profile_item_error_description, null)
    data class About(val _onClick: OnClick) : ProfileItem(R.string.profile_item_about, R.string.profile_item_about_description, _onClick)
    data class Settings(val _onClick: OnClick) : ProfileItem(R.string.profile_item_settings, R.string.profile_item_settings_description, _onClick)
}