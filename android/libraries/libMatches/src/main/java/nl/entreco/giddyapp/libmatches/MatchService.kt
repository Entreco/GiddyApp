package nl.entreco.giddyapp.libmatches

interface MatchService {
    fun match(userId: String, matches: List<Match>, done: (MatchResponse) -> Unit)
    fun retrieveForUser(userId: String, done: (List<Match>) -> Unit)
    fun delete(userId: String, horseId: String, done:(Exception?)->Unit)
}