package nl.entreco.giddyapp.core

import android.view.View
import androidx.databinding.BindingAdapter

object CoreBindings {

    @JvmStatic
    @BindingAdapter("showIf")
    fun showIf(view: View, show: Boolean){
        view.visibility = if(show) View.VISIBLE else View.GONE
    }
}