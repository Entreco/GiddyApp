package nl.entreco.giddyapp.profile.matches.details

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView

object MatchDetailBinding {

    @JvmStatic
    @BindingAdapter("ga_strokeColor")
    fun applyStrokeColor(card: MaterialCardView, @ColorRes color: Int){
        if(color != 0) {
            card.strokeColor = ContextCompat.getColor(card.context, color)
        }
    }
}