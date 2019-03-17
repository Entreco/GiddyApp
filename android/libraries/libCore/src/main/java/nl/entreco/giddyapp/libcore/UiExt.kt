package nl.entreco.giddyapp.libcore

import android.view.View
import android.view.ViewTreeObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun View.doOnLayout(f:()->Unit){
    viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            f()
        }
    })
}

fun <T> LiveData<T>.observeOnce(observer: Observer<T>) {
    observeForever(object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}