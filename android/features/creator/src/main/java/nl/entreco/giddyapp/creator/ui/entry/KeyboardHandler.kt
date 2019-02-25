package nl.entreco.giddyapp.creator.ui.entry

import android.view.View
import android.view.inputmethod.InputMethodManager
import javax.inject.Inject

class KeyboardHandler @Inject constructor(private val imm: InputMethodManager) {

    private fun show(view: View): KeyboardHandler {
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
        return this
    }

    private fun hide(view: View): KeyboardHandler {
        return this
    }

    fun handle(form: Form) {

    }

}