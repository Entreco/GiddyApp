package nl.entreco.giddyapp.core

interface FeatureModule<T : FeatureComponent>{
    fun create() : T
}