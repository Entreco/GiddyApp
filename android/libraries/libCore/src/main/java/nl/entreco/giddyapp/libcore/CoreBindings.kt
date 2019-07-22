package nl.entreco.giddyapp.libcore

import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter

object CoreBindings {

    @JvmStatic
    @BindingAdapter("showIf")
    fun showIf(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("stringRes")
    fun stringRes(view: TextView, @StringRes resource: Int?) {
        resource?.let { r ->
            if(r != 0) view.setText(r)
        }
    }
}