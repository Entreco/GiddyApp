package nl.entreco.giddyapp.creator.ui.entry

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.libcore.ChangeableField
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.libhorses.*
import javax.inject.Inject

class EntryViewModel @Inject constructor(private val state: CreatorState.Entry) : ViewModel() {

    val constraint = ObservableFloat(0F)
    val image = ChangeableField(state.image.uri)
    val model = ObservableField<EntryModel>((EntryModel(HorseDetail("", ""), state.image)))

    fun compose(done: (EntryModel) -> Unit) {
        if (validate()) {
            done(model.get()!!)
        }
    }

    private fun validate(): Boolean = true

    fun enter(form: Form) {
        val detail = model.get()!!.horseDetail
        val update = when (form) {
            is Form.Name -> detail.copy(name = form.input.get()!!)
            is Form.Desc -> detail.copy(desc = form.input.get()!!)
            is Form.Gender -> detail.copy(gender = HorseGender.values()[form.input.get()])
            is Form.Level -> detail.copy(type = HorseLevel.values()[form.input.get()])
            is Form.Price -> detail.copy(price = HorsePrice.values()[form.input.get()])
            is Form.Category -> detail.copy(category = HorseCategory.values()[form.input.get()])
            else -> detail
        }
        model.set(model.get()!!.copy(horseDetail = update))
    }

    companion object {
        @JvmStatic
        @BindingAdapter("ga_genderIcon")
        fun gender(view: TextView, position: Int) {
            view.setCompoundDrawablesWithIntrinsicBounds(0, 0, HorseGender.values()[position].icon(), 0)
        }

        @JvmStatic
        @BindingAdapter("ga_levelIcon")
        fun level(view: TextView, position: Int) {
//            view.setCompoundDrawablesWithIntrinsicBounds(0, 0, HorseLevel.values()[position].icon(), 0)
        }
        @JvmStatic
        @BindingAdapter("ga_priceIcon")
        fun price(view: TextView, position: Int) {
//            view.setCompoundDrawablesWithIntrinsicBounds(0, 0, HorsePrice.values()[position].icon(), 0)
        }
        @JvmStatic
        @BindingAdapter("ga_categoryIcon")
        fun category(view: TextView, position: Int) {
//            view.setCompoundDrawablesWithIntrinsicBounds(0, 0, HorseCategory.values()[position].icon(), 0)
        }
    }
}