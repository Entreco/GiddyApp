package nl.entreco.giddyapp.creator.ui.select

import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.creator.CreatorState
import javax.inject.Inject

class SelectViewModel @Inject constructor(select: CreatorState.Select) : ViewModel(){
    val step = SelectBottomModel(select)
}