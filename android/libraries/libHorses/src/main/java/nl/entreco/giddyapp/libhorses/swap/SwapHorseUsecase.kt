package nl.entreco.giddyapp.libhorses.swap

import nl.entreco.giddyapp.libhorses.Horse
import nl.entreco.giddyapp.libhorses.fetch.FetchHorseRequest
import nl.entreco.giddyapp.libhorses.fetch.FetchHorseUsecase
import nl.entreco.giddyapp.libhorses.fetch.FetchImageRequest
import nl.entreco.giddyapp.libhorses.fetch.FetchImageUsecase
import java.util.*
import javax.inject.Inject

class SwapHorseUsecase @Inject constructor(
    private val fetchImageUsecase: FetchImageUsecase,
    private val fetchHorseUsecase: FetchHorseUsecase
) {

    private val horses = mutableListOf<Horse>()
    private val queue = ArrayDeque<String>()
    var onPreloadListener: PreloadListener? = null

    fun initWith(collection: List<Horse>) {
        val atLeastOne = if (collection.isEmpty()) listOf(Horse.none()) else collection
        horses.removeAll { horse -> !queue.contains(horse.imageRef) }
        horses.addAll(atLeastOne)
        queue.addAll(atLeastOne.map { it.imageRef })
        preloadImages(atLeastOne)
    }

    fun go(done: (Horse, Horse) -> Unit) {
        done(pop(), peek())
    }

    private fun pop(): Horse {
        ensure()

        val popped = queue.pop()
        return horses.first { it.imageRef == popped }
    }

    private fun peek(): Horse {
        ensure()

        val peeked = queue.peek()
        return horses.first { it.imageRef == peeked }
    }

    private fun ensure() {
        require(horses.isNotEmpty()){ "need to add horses first" }
        if (queue.size < 3) {
            fetchHorseUsecase.go(FetchHorseRequest()) { response ->
                initWith(response.horses)
            }
        }
    }

    private fun preloadImages(collection: List<Horse>) {
        collection.filter { it.imageUri == null }.forEach { horse ->
            fetchImageUsecase.go(
                FetchImageRequest(
                    horse.imageRef,
                    horse.imageExt
                )
            ) { response ->
                val index = horses.indexOfFirst { it.imageRef == response.imageRef }
                if(index != -1) {
                    val updated = horses[index].copy(imageUri = response.image)
                    horses[index] = updated
                    onPreloadListener?.onImageReady(updated)
                }
            }
        }
    }

    interface PreloadListener {
        fun onImageReady(horse: Horse)
    }
}