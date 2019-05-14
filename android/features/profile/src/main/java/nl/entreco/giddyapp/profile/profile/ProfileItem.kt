package nl.entreco.giddyapp.profile.profile

import androidx.databinding.ObservableField

sealed class ProfileItem(private val name: String, private val onClick: OnClick) {
    val title = ObservableField<String>(name)
    val description = ObservableField<String>("Some description based on User")

    fun onClick() {
        onClick.onClick(this)
    }

    interface OnClick {
        fun onClick(item: ProfileItem)
    }

    companion object {
        fun all(onClick: OnClick): List<ProfileItem> = listOf(
            Creator(onClick),
            Custom(onClick),
            Settings(onClick)
        )
    }

    data class Creator(val _onClick: OnClick) : ProfileItem("Creator", _onClick)
    data class Custom(val _onClick: OnClick) : ProfileItem("Something Smart", _onClick)
    data class Settings(val _onClick: OnClick) : ProfileItem("Settings", _onClick)
}