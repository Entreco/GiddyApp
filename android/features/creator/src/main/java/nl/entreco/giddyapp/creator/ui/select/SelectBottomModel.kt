package nl.entreco.giddyapp.creator.ui.select

import androidx.databinding.ObservableInt
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.R

data class SelectBottomModel(val state: CreatorState) {
    val title = ObservableInt(R.string.sheet_title_select)
    val description = ObservableInt(R.string.sheet_description_select)
}
