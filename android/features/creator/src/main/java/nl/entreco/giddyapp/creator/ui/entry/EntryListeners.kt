package nl.entreco.giddyapp.creator.ui.entry

import android.widget.TextView
import androidx.annotation.IdRes

object EntryListeners {
    interface OnTextEnteredListener {
        fun onEditor(textView: TextView, actionId: Int) : Boolean
    }

    interface OnGenderSelected {
        fun onSelected(@IdRes itemId: Int)
    }

    interface OnNextPlease{
        fun onNext(current: Form)
    }
}