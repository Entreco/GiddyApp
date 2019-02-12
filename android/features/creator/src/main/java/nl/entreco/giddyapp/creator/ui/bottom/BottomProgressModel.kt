package nl.entreco.giddyapp.creator.ui.bottom

import nl.entreco.giddyapp.creator.CreatorState

data class BottomProgressModel(val state: CreatorState)

internal fun BottomProgressModel.showFab(): Boolean {
    return when (state) {
        is CreatorState.Select -> false
        is CreatorState.Crop -> true
        is CreatorState.Entry -> true
        is CreatorState.Upload -> true
        is CreatorState.Done -> false
    }
}