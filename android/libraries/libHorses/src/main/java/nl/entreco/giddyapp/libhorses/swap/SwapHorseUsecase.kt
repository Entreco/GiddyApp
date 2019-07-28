package nl.entreco.giddyapp.libhorses.swap

import nl.entreco.giddyapp.libhorses.Horse
import nl.entreco.giddyapp.libhorses.cycle.HorseCycler
import nl.entreco.giddyapp.libhorses.fetch.*
import java.util.*
import javax.inject.Inject

class SwapHorseUsecase @Inject constructor(
    private val fetchImageUsecase: FetchImageUsecase,
    private val fetchHorseUsecase: FetchHorseUsecase,
    private val horseCycler: HorseCycler
) {

    private val horses = mutableListOf<Horse>()
    private val queue = ArrayDeque<String>()
    var onPreloadListener: PreloadListener? = null

    fun initWith(collection: List<Horse>) {
        val atLeastTwo = horseCycler.initialHorses(collection)
        horses.removeAll { horse -> !queue.contains(horse.imageRef) }
        horses.addAll(atLeastTwo)
        queue.addAll(atLeastTwo.map { it.imageRef })
        preloadImages(atLeastTwo)
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
        // TODO entreco - 2019-07-27: Perhaps add Horse.Loading instead of crashing here?
        require(horses.isNotEmpty()) { "need to add horses first" }

        if (queue.size < 3) {
            fetchHorseUsecase.go(FetchHorseRequest()) { response ->
                initWith(response.horses)
            }
        }
    }

    private fun preloadImages(collection: List<Horse>) {
        collection.filter { it.imageUri == null }.forEach { horse ->
            fetchImageUsecase.go(FetchImageRequest(horse.imageRef)) { response ->
                when (response) {
                    is FetchImageResponse.Ok -> updateImage(response)
                    is FetchImageResponse.Err -> { }
                }
            }
        }
    }

    private fun updateImage(response: FetchImageResponse.Ok) {
        val index = horses.indexOfFirst { it.imageRef == response.imageRef }
        if (index != -1) {
            val updated = horses[index].update(imageUri = response.image)
            horses[index] = updated
            onPreloadListener?.onImageReady(updated)
        }
    }

    fun clear() {
        queue.clear()
        horses.clear()
    }

    interface PreloadListener {
        fun onImageReady(horse: Horse)
    }
}