package nl.entreco.giddyapp.core

data class BaseComponent(val id: String) {
    fun <T : FeatureComponent> plus(module: FeatureModule<T>) : T{
        return module.create()
    }
}