package nl.entreco.giddyapp.profile.matches.details

import nl.entreco.giddyapp.libhorses.Horse

sealed class FetchDetailsResponse{
    data class Succes(val horse: Horse.Normal) : FetchDetailsResponse()
    data class NotFound(val notFound: Horse.NotFound) : FetchDetailsResponse()
    data class Error(val none: Horse.None) : FetchDetailsResponse()
}