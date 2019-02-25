package nl.entreco.giddyapp.creator.ui.entry

import android.widget.TextView
import androidx.annotation.IdRes

class InputEntryModel(
    private val currentForm: Form,
    private val onUpdate: (Form) -> Unit) :
    EntryListeners.OnTextEnteredListener,
    EntryListeners.OnGenderSelected {

    override fun onEditor(textView: TextView, actionId: Int): Boolean {
        when(currentForm){
            is Form.Name -> currentForm.input.set(textView.text.toString())
            is Form.Desc -> currentForm.input.set(textView.text.toString())
            else -> { /* unsupported for this form */}
        }
        onUpdate(currentForm)
        return true
    }

    override fun onSelected(@IdRes itemId: Int) {
        when(currentForm){
            is Form.Gender -> currentForm.checked(itemId)
        }
        onUpdate(currentForm)
    }
}