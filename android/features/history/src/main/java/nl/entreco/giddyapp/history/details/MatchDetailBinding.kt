package nl.entreco.giddyapp.history.details

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import nl.entreco.giddyapp.history.models.BaseModel

object MatchDetailBinding {

    @JvmStatic
    @BindingAdapter("ga_strokeColor")
    fun applyStrokeColor(card: MaterialCardView, @ColorRes color: Int) {
        if (color != 0) {
            card.strokeColor = ContextCompat.getColor(card.context, color)
        }
    }

    @JvmStatic
    @BindingAdapter("text")
    fun setText(view: TextView, model: BaseModel?) {
        when (model) {
            is BaseModel.HeaderModel.Txt -> view.text = model.text
            is BaseModel.HeaderModel.Res -> view.setText(model.resource)
            is BaseModel.DescriptionModel.Txt -> view.setText(model.text)
            is BaseModel.DescriptionModel.Res -> view.setText(model.resource)
            is BaseModel.DetailModel.Res -> view.setText(model.resource)
        }
    }

    @JvmStatic
    @BindingAdapter("value")
    fun setValue(view: TextView, model: BaseModel?) {
        when (model) {
            is BaseModel.DetailModel.Res -> view.text = model.value
        }
    }

    @JvmStatic
    @BindingAdapter("icon")
    fun showIcon(view: ImageView, model: BaseModel?) {
        when (model) {
            is BaseModel.DetailModel.Res -> view.setImageResource(model.icon)
            else -> view.setImageDrawable(null)
        }
    }
}