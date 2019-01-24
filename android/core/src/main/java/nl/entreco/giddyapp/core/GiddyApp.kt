package nl.entreco.giddyapp.core

import android.app.Application

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