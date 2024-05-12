package dev.zt64.dearrowkt

import io.ktor.utils.io.core.*
import kotlinx.coroutines.test.runTest
import org.kotlincrypto.hash.sha2.SHA256
import kotlin.test.Test
import kotlin.test.assertEquals

private val videoIds = listOf(
    "ixn5OygxBY4",
    "O7xH9ZSp_B4"
)

class DeArrowTest {
    @Test
    fun test() = runTest {
        val deArrowClient = DeArrowClient()

        videoIds.forEach {
            val branding = deArrowClient.getBranding(it)
            val secureBranding = deArrowClient.getBranding(it)

            assertEquals(branding, secureBranding, "Not the same branding")
        }
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    @Test
    fun testHash() {
        val map = mapOf(
            "ixn5OygxBY4" to "1ec6",
            "O7xH9ZSp_B4" to "b2ca"
        )

        map.forEach { (id, expectedHash) ->
            /** Copied from [DeArrowClient.calculateHash] */
            val hash = SHA256()
                .digest(id.toByteArray())
                .asUByteArray()
                .joinToString("") { it.toString(16) }
                .take(4)

            assertEquals(expectedHash, hash, "Hash is not correct")
        }
    }
}