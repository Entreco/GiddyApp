package nl.entreco.giddyapp.creator.ui.bottom

import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.R

data class BottomProgressModel(val state: CreatorState)

internal fun BottomProgressModel.fab(): Int {
    return when (state) {
        is CreatorState.Select -> 0
        is CreatorState.Crop -> R.drawable.ic_resize
        is CreatorState.Entry -> R.drawable.ic_next
        is CreatorState.Upload -> R.drawable.ic_upload
        is CreatorState.Done -> 0
    }
}

internal fun BottomProgressModel.waiting(): Int {
    return when (state) {
        is CreatorState.Entry -> R.drawable.ic_next
        else -> R.drawable.ic_wait
    }
}