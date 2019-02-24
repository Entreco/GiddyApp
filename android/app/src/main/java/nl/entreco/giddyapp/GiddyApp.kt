package nl.entreco.giddyapp

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import nl.entreco.giddyapp.core.DaggerFeatureComponent
import nl.entreco.giddyapp.core.FeatureComponent
import nl.entreco.giddyapp.core.FeatureModule

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