package nl.entreco.giddyapp.core.di

interface DiProvider<T> {
    fun get(): T
}