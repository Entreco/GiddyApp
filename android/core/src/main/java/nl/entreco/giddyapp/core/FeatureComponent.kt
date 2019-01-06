package nl.entreco.giddyapp.core

import dagger.Component

@Component(modules = [FeatureModule::class])
interface FeatureComponent {
    fun plus(module: ApiModule): ApiComponent
}