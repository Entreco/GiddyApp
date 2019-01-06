package nl.entreco.giddyapp.core

import dagger.Subcomponent

@ApiScope
@Subcomponent(modules = [ApiModule::class])
interface ApiComponent{
    fun api() : Api
}