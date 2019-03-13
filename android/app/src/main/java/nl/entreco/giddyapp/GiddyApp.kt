package nl.entreco.giddyapp

import android.app.Application
import com.squareup.leakcanary.LeakCanary
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
        if (LeakCanary.isInAnalyzerProcess(this)) LeakCanary.install(this)
    }

    override fun get(): FeatureComponent = featureComponent
}

fun BaseActivity.featureComponent(): FeatureComponent =
    (application as? DiProvider<FeatureComponent>)?.get()
        ?: throw IllegalStateException("application must implement DiProvider<FeatureComponent>")