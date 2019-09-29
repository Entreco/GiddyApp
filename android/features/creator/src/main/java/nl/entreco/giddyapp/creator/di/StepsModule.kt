package nl.entreco.giddyapp.creator.di

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.libcore.di.AppContext
import nl.entreco.giddyapp.libcore.ui.SheetBehavior

@Module
class StepsModule(private val sheet: View, private val state: CreatorState?) {

    @Provides
    @SheetBehavior
    fun provideBottomSheet(): BottomSheetBehavior<View> {
        return BottomSheetBehavior.from(sheet)
    }

    @Provides
    fun provideInputMethodManager(@AppContext context: Context): InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    @Provides
    fun provideSelectState(): CreatorState.Select {
        check(state is CreatorState.Select) { "state should be 'Select' but is $state" }
        return state
    }

    @Provides
    fun provideCropState(): CreatorState.Crop {
        check(state is CreatorState.Crop) { "state should be 'Crop' but is $state" }
        return state
    }

    @Provides
    fun provideEntryNameState(): CreatorState.EntryName {
        check(state is CreatorState.EntryName) { "state should be 'Entry' but is $state" }
        return state
    }

    @Provides
    fun provideEntryDescriptionState(): CreatorState.EntryDescription {
        check(state is CreatorState.EntryDescription) { "state should be 'Entry' but is $state" }
        return state
    }

    @Provides
    fun provideEntryGenderState(): CreatorState.EntryGender {
        check(state is CreatorState.EntryGender) { "state should be 'Entry' but is $state" }
        return state
    }

    @Provides
    fun provideEntryPriceState(): CreatorState.EntryPrice {
        check(state is CreatorState.EntryPrice) { "state should be 'Entry' but is $state" }
        return state
    }

    @Provides
    fun provideEntryCategoryState(): CreatorState.EntryCategory {
        check(state is CreatorState.EntryCategory) { "state should be 'Entry' but is $state" }
        return state
    }

    @Provides
    fun provideEntryLevelState(): CreatorState.EntryLevel {
        check(state is CreatorState.EntryLevel) { "state should be 'Entry' but is $state" }
        return state
    }

    @Provides
    fun provideUploadState(): CreatorState.Upload {
        check(state is CreatorState.Upload) { "state should be 'Upload' but is $state" }
        return state
    }
}