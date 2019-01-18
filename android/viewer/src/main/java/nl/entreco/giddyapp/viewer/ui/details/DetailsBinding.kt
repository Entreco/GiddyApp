package nl.entreco.giddyapp.viewer.ui.details

import android.os.Build
import android.text.Html
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.databinding.BindingAdapter

object DetailsBinding {

    @JvmStatic
    @BindingAdapter("ga_fadeIn")
    fun applyFadeIn(view: TextView, value: String?) {
        if (view.text != value && value != null && value.isNotBlank()) {
            view.animate().alpha(0F).translationY(48F).setInterpolator(AccelerateInterpolator()).withEndAction {
                view.setText(asHtml(value))
                view.animate().alpha(1F).translationY(0F).setInterpolator(DecelerateInterpolator()).setDuration(100).start()
            }.setDuration(100).start()
        }
    }

    private fun asHtml(value: String?) = if(Build.VERSION.SDK_INT < 24) Html.fromHtml(value) else  Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY)
}