package nl.entreco.giddyapp.viewer.fetch

import nl.entreco.giddyapp.viewer.Horse

data class FetchHorseResponse(val current: Horse? = null, val next: Horse? = null)