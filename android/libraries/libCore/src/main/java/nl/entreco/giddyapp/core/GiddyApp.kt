package nl.entreco.giddyapp.core

import android.app.Application
import com.squareup.leakcanary.LeakCanary

class GiddyApp : Application() {

    private val featureComponent: FeatureComponent by lazy {
        DaggerFeatureComponent.builder()
            .featureModule(FeatureModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        if(LeakCanary.isInAnalyzerProcess(this)) LeakCanary.install(this)
    }

    fun get(): FeatureComponent {
        return featureComponent
    }
}