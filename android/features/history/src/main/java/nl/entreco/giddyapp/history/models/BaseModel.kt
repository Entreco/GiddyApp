package nl.entreco.giddyapp.history.models

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView

sealed class BaseModel {
    abstract val id: String

    sealed class HeaderModel : BaseModel() {
        data class Txt(val _id: Int, val text: String) : HeaderModel() {
            override val id: String
                get() = "$_id"
        }

        data class Res(val resource: Int) : HeaderModel() {
            override val id: String
                get() = "$resource"
        }

        data class Loading(val resource: Int) : HeaderModel() {
            override val id: String
                get() = "$resource"
        }
    }

    sealed class DescriptionModel : BaseModel() {
        data class Txt(val text: String) : DescriptionModel() {
            override val id: String
                get() = text
        }

        data class Res(@StringRes val resource: Int) : DescriptionModel() {
            override val id: String
                get() = "$resource"
        }
    }

    sealed class DetailModel : BaseModel() {

        data class Res(@StringRes val resource: Int, @DrawableRes val icon: Int, val value: String) : DetailModel() {
            override val id: String
                get() = "$resource:$value"
        }

        data class Loading(val resource: Int) : DetailModel() {
            override val id: String
                get() = "$resource"
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseModel) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

abstract class BaseViewHolder<Model : BaseModel>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    abstract fun bind(model: Model?, position: Int)
}