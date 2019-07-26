package nl.entreco.giddyapp.libcore.ui

import android.content.res.Resources
import android.text.Html
import android.text.Spanned
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
            fadeAndTranslate(view) {
                view.text = asHtml(value)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("ga_fadeIn")
    fun applyFadeIn(view: TextView, @StringRes value: Int) {
        if (value != 0) {
            fadeAndTranslate(view) {
                view.setText(value)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("ga_spinIn")
    fun applyFadeIn(view: ImageView, @DrawableRes value: Int) {
        if (value != 0) {
            fadeAndSpin(view, view.tag != value) {
                try {
                    view.setImageResource(value)
                    view.tag = value
                } catch (oops: Resources.NotFoundException) { }
            }
        }
    }

    fun fadeAndTranslate(view: View, update: () -> Unit) {
        view.animate().alpha(0F)
            .translationY(48F)
            .setInterpolator(AccelerateInterpolator())
            .withEndAction {
                update()
                view.animate().alpha(1F)
                    .translationY(0F)
                    .setInterpolator(DecelerateInterpolator())
                    .setDuration(100).start()
            }.setDuration(100).start()
    }

    private fun fadeAndSpin(view: View, shouldSpin: Boolean, update: () -> Unit) {
        fadeAndTranslate(view){
            update()
        }

        if(shouldSpin) {
            view.animate().alpha(0F)
                .rotationBy(-180F)
                .scaleX(0F)
                .scaleY(0F)
                .setInterpolator(AccelerateInterpolator()).withEndAction {
                    view.animate().alpha(1F)
                        .rotationBy(-180F)
                        .scaleX(1F)
                        .scaleY(1F)
                        .setInterpolator(DecelerateInterpolator())
                        .setDuration(100).start()
                }.setDuration(100).start()
        }
    }

    fun asHtml(value: String?): Spanned = Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY)
}