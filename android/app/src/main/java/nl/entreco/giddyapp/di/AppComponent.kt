package nl.entreco.giddyapp.di

import dagger.Component
import nl.entreco.giddyapp.core.FeatureModule

@Component(modules = [AppModule::class, FeatureModule::class])
interface AppComponent