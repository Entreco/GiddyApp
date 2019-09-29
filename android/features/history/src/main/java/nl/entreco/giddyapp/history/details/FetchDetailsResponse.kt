package nl.entreco.giddyapp.history.details

import nl.entreco.giddyapp.libhorses.Horse

sealed class FetchDetailsResponse{
    data class Succes(val horse: Horse.Normal) : FetchDetailsResponse()
    data class NotFound(val notFound: Horse.NotFound) : FetchDetailsResponse()
    data class Error(val none: Horse.None) : FetchDetailsResponse()
}