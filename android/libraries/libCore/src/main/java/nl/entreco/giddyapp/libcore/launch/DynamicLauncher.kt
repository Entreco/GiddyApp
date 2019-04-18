package nl.entreco.giddyapp.libcore.launch

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import nl.entreco.giddyapp.libcore.di.AppContext
import javax.inject.Inject

class DynamicLauncher @Inject constructor(@AppContext context: Context) : LifecycleObserver {

    private val manager = SplitInstallManagerFactory.create(context)
    private val stateListener = SplitInstallStateUpdatedListener { state ->
        events.postValue(state.status())
        Log.i("YOGO", "install status: ${state.status()} ${state.errorCode()} ${state.moduleNames()}")
        when (state.status()) {
            SplitInstallSessionStatus.INSTALLED -> {
                unRegisterListener()
            }
        }
    }

    private val events = MutableLiveData<Int>()
    fun listen(): LiveData<Int> = events


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun registerListener() {
        manager.registerListener(stateListener)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun unRegisterListener() {
        manager.unregisterListener(stateListener)
    }

    fun launch(lifeCycle: Lifecycle, module: String) {
        lifeCycle.addObserver(this)
        if (manager.installedModules.contains(module)) {
            // Ready
            events.postValue(SplitInstallSessionStatus.INSTALLED)
            lifeCycle.removeObserver(this)
            unRegisterListener()
            return
        }

        val request = SplitInstallRequest.newBuilder().addModule(module).build()
        registerListener()
        manager.startInstall(request)
    }
}