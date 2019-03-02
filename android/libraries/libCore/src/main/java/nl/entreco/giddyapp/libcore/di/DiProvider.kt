package nl.entreco.giddyapp.libcore.di

interface DiProvider<T> {
    fun get(): T
}