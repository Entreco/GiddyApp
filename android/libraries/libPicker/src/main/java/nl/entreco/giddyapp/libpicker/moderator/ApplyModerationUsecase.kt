package nl.entreco.giddyapp.libpicker.moderator

import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libcore.onUi
import nl.entreco.giddyapp.libpicker.ImageLabeller
import java.util.*

internal class ApplyModerationUsecase(
    private val labeller: ImageLabeller
) {
    companion object {
        internal const val seperator = "|"
    }

    private val locale by lazy { Locale.getDefault() }

    fun go(request: ApplyModerationRequest, done: (ApplyModerationResponse) -> Unit) {
        onBg {
            labeller.getLabels(request.context, request.uri) { labels ->
                val blocked = labels.toLowercase().filterNotBlocked(request.blocked)
                val required = labels.toLowercase().filterRequired(request.required)

                val response = when (blocked.isEmpty() && required.size == request.required.size) {
                    true -> Moderation.Allowed
                    else -> Moderation.Rejected(
                        blocked.joinToString(seperator),
                        request.required.joinToString(seperator)
                    )
                }

                onUi { done(ApplyModerationResponse(response)) }
            }
        }
    }

    private fun List<String>.filterNotBlocked(blocked: List<String>): Set<String> =
        intersect(blocked)

    private fun List<String>.filterRequired(required: List<String>): Set<String> =
        intersect(required)


    private fun List<String>.toLowercase(): List<String> = map { it.toLowerCase(locale) }
}