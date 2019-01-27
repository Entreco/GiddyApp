package nl.entreco.giddyapp.core

import android.content.Context
import dagger.Component

@FeatureScope
@Component(modules = [FeatureModule::class])
interface FeatureComponent {
    fun context(): Context
}