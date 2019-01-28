package nl.entreco.giddyapp.creator.di

import dagger.Subcomponent
import nl.entreco.giddyapp.creator.ui.crop.CropViewModel
import nl.entreco.giddyapp.creator.ui.select.SelectViewModel
import nl.entreco.giddyapp.creator.ui.upload.UploadViewModel

@Subcomponent(modules = [StepsModule::class])
interface StepsComponent {
    fun select(): SelectViewModel
    fun crop(): CropViewModel
    fun upload(): UploadViewModel
}