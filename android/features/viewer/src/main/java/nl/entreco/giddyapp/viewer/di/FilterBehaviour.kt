package nl.entreco.giddyapp.viewer.di

import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class FilterBehaviour(val value: String = "filterBehaviour")