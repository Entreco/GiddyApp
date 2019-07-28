package nl.entreco.giddyapp.libhorses.data

import nl.entreco.giddyapp.libhorses.Horse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class HorseMapperTest {
    private lateinit var subject: HorseMapper

    @Before
    fun setUp() {
        subject = HorseMapper()
    }

    @Test
    fun `it should map ratio (default)`() {
        val mapped = subject.map(
            FbHorse(), "some id"
        ) as? Horse.Normal
        assertNotNull(mapped)
        assertEquals("-", mapped?.details?.ratio?.ratio)
    }

    @Test
    fun `it should map ratio (100%)`() {
        val mapped = subject.map(FbHorse(likes = 1, dislikes = 0), "some id") as? Horse.Normal
        assertNotNull(mapped)
        assertEquals("100%", mapped?.details?.ratio?.ratio)
    }

    @Test
    fun `it should map ratio (0%)`() {
        val mapped = subject.map(FbHorse(likes = 0, dislikes = 1), "some id") as? Horse.Normal
        assertNotNull(mapped)
        assertEquals(" 0%", mapped?.details?.ratio?.ratio)
    }

    @Test
    fun `it should map ratio (33%)`() {
        val mapped = subject.map(FbHorse(likes = 1, dislikes = 2), "some id") as? Horse.Normal
        assertNotNull(mapped)
        assertEquals("33%", mapped?.details?.ratio?.ratio)
    }

    @Test
    fun `it should map ratio (50%)`() {
        val mapped = subject.map(FbHorse(likes = 2, dislikes = 2), "some id") as? Horse.Normal
        assertNotNull(mapped)
        assertEquals("50%", mapped?.details?.ratio?.ratio)
    }
}