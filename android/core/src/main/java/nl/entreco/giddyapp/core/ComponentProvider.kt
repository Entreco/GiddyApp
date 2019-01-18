package nl.entreco.giddyapp.core

interface ComponentProvider<T> {
    fun get(): T
}