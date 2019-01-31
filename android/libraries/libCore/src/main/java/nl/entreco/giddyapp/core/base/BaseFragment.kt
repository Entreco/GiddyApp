package nl.entreco.giddyapp.core.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

class BaseFragment : Fragment()

inline fun <reified VM : ViewModel> Fragment.parentViewModelProvider(
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
    crossinline provider: () -> Class<VM>
) = lazy(mode) {
    ViewModelProviders.of(activity as FragmentActivity).get(provider())
}

inline fun <reified VM : ViewModel> Fragment.viewModelProvider(
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.NONE,
    crossinline provider: () -> VM
) = lazy(mode) {
    ViewModelProviders.of(this, object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return provider() as T
        }
    }).get(VM::class.java)
}