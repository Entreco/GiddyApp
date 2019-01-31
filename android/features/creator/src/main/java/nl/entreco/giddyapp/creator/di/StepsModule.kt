package nl.entreco.giddyapp.creator.di

import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.creator.CreatorState

@Module
class StepsModule(private val state: CreatorState?) {

    @Provides
    fun provideSelectState() : CreatorState.Select{
        if(state !is CreatorState.Select) throw IllegalStateException("state should be 'Select' but is $state")
        return state as CreatorState.Select
    }

    @Provides
    fun provideCropState() : CreatorState.Crop{
        if(state !is CreatorState.Crop) throw IllegalStateException("state should be 'Crop' but is $state")
        return state as CreatorState.Crop
    }

    @Provides
    fun provideEntryState() : CreatorState.Entry{
        if(state !is CreatorState.Entry) throw IllegalStateException("state should be 'Entry' but is $state")
        return state as CreatorState.Entry
    }

    @Provides
    fun provideUploadState() : CreatorState.Upload{
        if(state !is CreatorState.Upload) throw IllegalStateException("state should be 'Upload' but is $state")
        return state as CreatorState.Upload
    }
}