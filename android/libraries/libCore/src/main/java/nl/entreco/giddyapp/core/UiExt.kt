package nl.entreco.giddyapp.core

import android.view.View
import android.view.ViewTreeObserver

fun View.doOnLayout(f:()->Unit){
    viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            f()
        }
    })
}