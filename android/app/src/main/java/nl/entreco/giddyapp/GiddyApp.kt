package nl.entreco.giddyapp

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import nl.entreco.giddyapp.libcore.base.BaseActivity
import nl.entreco.giddyapp.libcore.di.DiProvider


class GiddyApp : Application(),
    DiProvider<FeatureComponent> {

    private val featureComponent: FeatureComponent by lazy {
        DaggerFeatureComponent.builder()
            .app(this)
            .metrics(resources.displayMetrics)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) enableStrictMode()
    }

    private fun enableStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )
        StrictMode.setVmPolicy(
            VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )
    }

    override fun get(): FeatureComponent = featureComponent
}

fun BaseActivity.featureComponent(): FeatureComponent =
    (application as? DiProvider<FeatureComponent>)?.get()
        ?: throw IllegalStateException("application must implement DiProvider<FeatureComponent>")