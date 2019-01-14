package nl.entreco.giddyapp

import dagger.Component
import nl.entreco.giddyapp.core.FeatureModule

@Component(modules = [AppModule::class, FeatureModule::class])
interface AppComponent {
}