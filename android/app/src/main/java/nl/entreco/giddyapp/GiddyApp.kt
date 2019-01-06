package nl.entreco.giddyapp

import android.app.Application
import nl.entreco.giddyapp.core.DaggerFeatureComponent
import nl.entreco.giddyapp.core.FeatureComponent
import nl.entreco.giddyapp.core.FeatureModule

class GiddyApp : Application() {

    private val featureComponent: FeatureComponent by lazy {
        DaggerFeatureComponent.builder()
            .featureModule(FeatureModule(this))
            .build()
    }

    fun get(): FeatureComponent {
        return featureComponent
    }
}