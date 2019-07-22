package nl.entreco.giddyapp.libauth.account.firebase

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import com.firebase.ui.auth.AuthMethodPickerLayout

class FbAuthUiSettings(
    @StyleRes private val _style: Int,
    @LayoutRes private val _layout: Int
) {
    val style: Int
        get() = _style

    private val builder by lazy { AuthMethodPickerLayout.Builder(_layout) }

    fun withEmail(@IdRes btn: Int): FbAuthUiSettings {
        builder.setEmailButtonId(btn)
        return this
    }

    fun withPhone(@IdRes btn: Int): FbAuthUiSettings {
        builder.setPhoneButtonId(btn)
        return this
    }

    fun withGoogle(@IdRes btn: Int): FbAuthUiSettings {
        builder.setGoogleButtonId(btn)
        return this
    }

    fun withFacebook(@IdRes btn: Int): FbAuthUiSettings {
        builder.setFacebookButtonId(btn)
        return this
    }

    fun build(): AuthMethodPickerLayout = builder.build()
}