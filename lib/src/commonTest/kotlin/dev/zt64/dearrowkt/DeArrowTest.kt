package dev.zt64.dearrowkt

import dev.zt64.dearrowkt.model.Branding
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.kotlincrypto.hash.sha2.SHA256
import kotlin.test.Test
import kotlin.test.assertEquals

private data class TestData(val hashedId: String, val branding: Branding)

private val testData = mapOf(
    "ixn5OygxBY4" to TestData(
        hashedId = "1ec6",
        branding = Branding(
            titles = listOf(),
            thumbnails = listOf(),
            randomTime = 0f,
            videoDuration = 0.0
        )
    ),
    "O7xH9ZSp_B4" to TestData(
        hashedId = "b2ca",
        branding = Branding(
            titles = listOf(),
            thumbnails = listOf(),
            randomTime = 0f,
            videoDuration = 0.0
        )
    )
)

class DeArrowTest {
    @Test
    fun test() = runTest {
        val mockEngine = MockEngine { data ->
            when {
                data.url.encodedPath == "/api/branding" -> {
                    val id = data.url.parameters["videoID"] ?: return@MockEngine respondBadRequest()
                    val branding = testData[id]?.branding ?: return@MockEngine respondBadRequest()

                    respond(
                        content = Json.encodeToString(branding),
                        headers = headersOf("Content-Type" to listOf("application/json"))
                    )
                }

                else -> respondBadRequest()
            }
        }
        val client = DeArrowClient(engine = mockEngine)

        testData.forEach { (id) -> client.getBranding(id) }
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    @Test
    fun testHash() {
        testData.forEach { (id, data) ->
            /** Copied from [DeArrowClient.calculateHash] */
            val hash = SHA256()
                .digest(id.toByteArray())
                .asUByteArray()
                .joinToString("") { it.toString(16) }
                .take(4)

            assertEquals(data.hashedId, hash, "Hash is not correct")
        }
    }
}