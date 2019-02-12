package nl.entreco.giddyapp.creator.ui.crop

import androidx.databinding.ObservableInt
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.R

class CropBottomModel(val state: CreatorState) {
    val title = ObservableInt(R.string.sheet_title_crop)
    val description = ObservableInt(R.string.sheet_description_crop)
}