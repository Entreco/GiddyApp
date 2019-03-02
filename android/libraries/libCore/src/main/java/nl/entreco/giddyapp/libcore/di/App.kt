package nl.entreco.giddyapp.libcore.di

import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class App(val value: String = "application")