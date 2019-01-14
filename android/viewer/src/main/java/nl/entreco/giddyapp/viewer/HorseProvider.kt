package nl.entreco.giddyapp.viewer

import nl.entreco.giddyapp.viewer.fetch.FetchImageRequest
import nl.entreco.giddyapp.viewer.fetch.FetchImageUsecase
import java.util.*
import javax.inject.Inject

class HorseProvider @Inject constructor(
    private val fetchImageUsecase: FetchImageUsecase
) {

    private val horses = mutableListOf<Horse>()
    private val queue = ArrayDeque<String>()
    var imageReadyListener: Listener? = null

    fun addAll(collection: List<Horse>) {
        val atLeastOne = if (collection.isEmpty()) listOf(Horse.none()) else collection
        horses.addAll(atLeastOne)
        queue.addAll(atLeastOne.map { it.imageRef })
        preloadImages(atLeastOne)
    }


    fun pop(): Horse {
        ensure()
        val popped = queue.pop()
        return horses.first { it.imageRef == popped }
    }

    fun peek(): Horse {
        ensure()

        val peeked = queue.peek()
        return horses.first { it.imageRef == peeked }
    }

    private fun ensure() {
        if (horses.isEmpty()) throw IllegalStateException("need to add horses first")
        if (queue.isEmpty()) {
            queue.addAll(horses.shuffled().map { it.imageRef })

        }
    }

    private fun preloadImages(collection: List<Horse>) {
        collection.filter { it.imageUri == null }.forEach { horse ->
            fetchImageUsecase.go(FetchImageRequest(horse.imageRef)) { response ->
                val index = horses.indexOfFirst { it.imageRef == response.imageRef }
                val updated = horses[index].copy(imageUri = response.image)
                horses[index] = updated
                imageReadyListener?.onImageReady(updated)
            }
        }
    }

    interface Listener {
        fun onImageReady(horse: Horse)
    }
}