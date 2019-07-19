package nl.entreco.giddyapp.libcore.ui

import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class SheetBehavior(val value: String = "sheetBehaviour")