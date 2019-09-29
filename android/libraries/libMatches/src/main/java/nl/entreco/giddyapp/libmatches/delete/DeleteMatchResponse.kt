package nl.entreco.giddyapp.libmatches.delete

sealed class DeleteMatchResponse{
    object Success : DeleteMatchResponse()
    data class Fail(val err: Throwable): DeleteMatchResponse()
}