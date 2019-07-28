package nl.entreco.giddyapp.libhorses

import android.widget.TextView
import androidx.databinding.BindingAdapter
import nl.entreco.giddyapp.libcore.ui.DetailsBinding
import nl.entreco.giddyapp.libhorses.horses.R

object HorseBinding {
    @JvmStatic
    @BindingAdapter("ga_fadeIn")
    fun applyFadeIn(view: TextView, level: HorseLevel?) {
        val resolved = view.resources.getStringArray(R.array.horse_level)[level?.ordinal ?: 0]
        val detail = view.resources.getString(R.string.detail_1, resolved)
        apply(view, detail)
    }

    @JvmStatic
    @BindingAdapter("ga_fadeIn")
    fun applyFadeIn(view: TextView, level: HorsePrice?) {
        val resolved = view.resources.getStringArray(R.array.horse_price)[level?.ordinal ?: 0]
        val detail = view.resources.getString(R.string.detail_2, resolved)
        apply(view, detail)
    }

    @JvmStatic
    @BindingAdapter("ga_slideIn", "ga_entries")
    fun applySlideIn(view: TextView, newLevel: Int, array: Array<String>) {
        val resolved = array[newLevel]
        apply(view, resolved)
    }

    @JvmStatic
    @BindingAdapter("ga_fadeIn")
    fun applyFadeIn(view: TextView, ratio: HorseRatio?) {
        val detail = view.resources.getString(R.string.detail_4, ratio?.ratio)
        apply(view, detail)
    }

    @JvmStatic
    @BindingAdapter("ga_fadeIn")
    fun applyFadeIn(view: TextView, posted: HorsePosted?) {
        val detail = view.resources.getString(R.string.detail_5, posted?.since ?: "-")
        apply(view, detail)
    }

    @JvmStatic
    @BindingAdapter("ga_fadeIn")
    fun applyFadeIn(view: TextView, level: HorseCategory?) {
        val resolved = view.resources.getStringArray(R.array.horse_category)[level?.ordinal ?: 0]
        val detail = view.resources.getString(R.string.detail_3, resolved)
        apply(view, detail)
    }

    private fun apply(view: TextView, resolved: String?) {
        if (view.text != resolved) {
            DetailsBinding.fadeAndTranslate(view) {
                view.text = DetailsBinding.asHtml(resolved)
            }
        }
    }
}
