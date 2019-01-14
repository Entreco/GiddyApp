package nl.entreco.giddyapp.core

import android.content.Context
import dagger.Component
import nl.entreco.giddyapp.core.api.ApiComponent
import nl.entreco.giddyapp.core.api.ApiModule
import nl.entreco.giddyapp.core.images.ImageComponent
import nl.entreco.giddyapp.core.images.ImageModule

@FeatureScope
@Component(modules = [FeatureModule::class])
interface FeatureComponent {
    fun context(): Context
    fun plus(module: ApiModule): ApiComponent
    fun plus(module: ImageModule): ImageComponent
}