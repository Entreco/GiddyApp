package nl.entreco.giddyapp.profile.menu

import androidx.annotation.StringRes
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import nl.entreco.giddyapp.profile.R

sealed class MenuItem(@StringRes name: Int, @StringRes desc: Int, private val onClick: OnClick? = null) {
    val title = ObservableInt(name)
    val description = ObservableInt(desc)
    val enabled = ObservableBoolean(onClick != null)
    val progress = ObservableInt(0)
    val max = ObservableInt(0)

    fun onClick() {
        onClick?.onClick(this)
    }

    interface OnClick {
        fun onClick(item: MenuItem)
    }

    companion object {
        fun all(onClick: OnClick): List<MenuItem> = listOf(
            Upload(onClick),
            Matches(onClick),
            Settings(onClick),
            About(onClick)
        )

        fun anonymous(onClick: OnClick): List<MenuItem> = listOf(
            Upload(),
            Matches(onClick),
            Settings(onClick),
            About(onClick)
        )

        fun error() : List<MenuItem> = listOf(
            Error()
        )
    }

    data class Upload(val _onClick: OnClick? = null) : MenuItem(R.string.profile_item_upload, R.string.profile_item_upload_description, _onClick)
    data class Matches(val _onClick: OnClick? = null) : MenuItem(R.string.profile_item_matches, R.string.profile_item_matches_description, _onClick)
    data class About(val _onClick: OnClick) : MenuItem(R.string.profile_item_about, R.string.profile_item_about_description, _onClick)
    data class Settings(val _onClick: OnClick) : MenuItem(R.string.profile_item_settings, R.string.profile_item_settings_description, _onClick)
    data class Error(val _onClick: OnClick? = null) : MenuItem(R.string.profile_item_error, R.string.profile_item_error_description, null)
}