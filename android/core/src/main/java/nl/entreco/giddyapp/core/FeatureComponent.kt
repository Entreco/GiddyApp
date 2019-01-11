package nl.entreco.giddyapp.core

import android.content.Context
import dagger.Component

@Component(modules = [FeatureModule::class])
interface FeatureComponent {
    fun context(): Context
    fun plus(module: ApiModule): ApiComponent
}