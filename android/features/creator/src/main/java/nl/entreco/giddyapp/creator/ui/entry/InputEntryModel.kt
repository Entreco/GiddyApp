package nl.entreco.giddyapp.creator.ui.entry

import android.widget.TextView

class InputEntryModel(
    private val currentForm: Form,
    private val nextListener: EntryListeners.OnNextPlease) :
    EntryListeners.OnTextEnteredListener,
    EntryListeners.OnGenderSelected {

    override fun onEditor(textView: TextView, actionId: Int): Boolean {
        when(currentForm){
            is Form.Name -> currentForm.input.set(textView.text.toString())
            is Form.Desc -> currentForm.input.set(textView.text.toString())
            else -> { /* unsupported for this form */}
        }
        nextListener.onNext(currentForm)
        return true
    }

    override fun onSelected(position: Int) {
        when(currentForm){
            is Form.Gender -> currentForm.input.set(position)
        }
        nextListener.onNext(currentForm)
    }
}