package nl.entreco.giddyapp.creator.di

import dagger.Subcomponent
import nl.entreco.giddyapp.creator.ui.crop.CropViewModel
import nl.entreco.giddyapp.creator.ui.entry.*
import nl.entreco.giddyapp.creator.ui.select.SelectViewModel
import nl.entreco.giddyapp.creator.ui.upload.UploadViewModel
import nl.entreco.giddyapp.libcore.ui.DetailSheet

@Subcomponent(modules = [StepsModule::class])
interface StepsComponent {
    fun select(): SelectViewModel
    fun crop(): CropViewModel
    fun enterName(): EntryNameViewModel
    fun enterDescription(): EntryDescriptionViewModel
    fun enterGender(): EntryGenderViewModel
    fun enterPrice(): EntryPriceViewModel
    fun enterCategory(): EntryCategoryViewModel
    fun enterLevel(): EntryLevelViewModel
    fun upload(): UploadViewModel
    fun sheet(): DetailSheet
}