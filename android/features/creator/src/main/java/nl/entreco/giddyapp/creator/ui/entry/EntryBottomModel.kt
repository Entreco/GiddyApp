package nl.entreco.giddyapp.creator.ui.entry

import androidx.databinding.ObservableInt
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.R

class EntryBottomModel(val state: CreatorState) {
    val title = ObservableInt(R.string.sheet_title_entry)
    val description = ObservableInt(R.string.sheet_description_entry)
}