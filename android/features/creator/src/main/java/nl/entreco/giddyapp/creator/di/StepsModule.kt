package nl.entreco.giddyapp.creator.di

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.libcore.di.App

@Module
class StepsModule(private val sheet: View, private val state: CreatorState?) {

    @Provides
    fun provideBottomSheet(): BottomSheetBehavior<View> {
        return BottomSheetBehavior.from(sheet)
    }

    @Provides
    fun provideInputMethodManager(@App context: Context): InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    @Provides
    fun provideSelectState(): CreatorState.Select {
        if (state !is CreatorState.Select) throw IllegalStateException("state should be 'Select' but is $state")
        return state
    }

    @Provides
    fun provideCropState(): CreatorState.Crop {
        if (state !is CreatorState.Crop) throw IllegalStateException("state should be 'Crop' but is $state")
        return state
    }

    @Provides
    fun provideEntryNameState(): CreatorState.EntryName {
        if (state !is CreatorState.EntryName) throw IllegalStateException("state should be 'Entry' but is $state")
        return state
    }

    @Provides
    fun provideEntryDescriptionState(): CreatorState.EntryDescription {
        if (state !is CreatorState.EntryDescription) throw IllegalStateException("state should be 'Entry' but is $state")
        return state
    }

    @Provides
    fun provideEntryGenderState(): CreatorState.EntryGender {
        if (state !is CreatorState.EntryGender) throw IllegalStateException("state should be 'Entry' but is $state")
        return state
    }

    @Provides
    fun provideEntryPriceState(): CreatorState.EntryPrice {
        if (state !is CreatorState.EntryPrice) throw IllegalStateException("state should be 'Entry' but is $state")
        return state
    }

    @Provides
    fun provideEntryCategoryState(): CreatorState.EntryCategory {
        if (state !is CreatorState.EntryCategory) throw IllegalStateException("state should be 'Entry' but is $state")
        return state
    }

    @Provides
    fun provideEntryLevelState(): CreatorState.EntryLevel {
        if (state !is CreatorState.EntryLevel) throw IllegalStateException("state should be 'Entry' but is $state")
        return state
    }

    @Provides
    fun provideUploadState(): CreatorState.Upload {
        if (state !is CreatorState.Upload) throw IllegalStateException("state should be 'Upload' but is $state")
        return state
    }
}