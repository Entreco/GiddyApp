package nl.entreco.giddyapp.core.ui

import android.content.res.Resources
import android.text.Html
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter

object DetailsBinding {

    @JvmStatic
    @BindingAdapter("ga_fadeIn")
    fun applyFadeIn(view: TextView, value: String?) {
        if (view.text != value && value != null && value.isNotBlank()) {
            fadeAnTranslate(view) {
                view.text = asHtml(value)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("ga_fadeIn")
    fun applyFadeIn(view: TextView, @StringRes value: Int) {
        if (value != 0) {
            fadeAnTranslate(view) {
                view.setText(value)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("ga_fadeIn")
    fun applyFadeIn(view: ImageView, @DrawableRes value: Int) {
        if (value != 0) {
            fadeAnTranslate(view) {
                try {
                    view.setImageResource(value)
                } catch (oops: Resources.NotFoundException) {
                }
            }
        }
    }

    private fun fadeAnTranslate(view: View, update: () -> Unit) {
        view.animate().alpha(0F).translationY(48F).setInterpolator(AccelerateInterpolator()).withEndAction {
            update()
            view.animate().alpha(1F).translationY(0F).setInterpolator(DecelerateInterpolator()).setDuration(100).start()
        }.setDuration(100).start()
    }

    private fun asHtml(value: String?) = Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY)
}