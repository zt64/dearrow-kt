package dev.zt64.dearrowkt

import dev.zt64.dearrowkt.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.core.*
import kotlinx.serialization.json.Json
import org.kotlincrypto.hash.sha2.SHA256

/** Official DeArrow API URL. */
private const val API_URL = "https://sponsor.ajay.app/api/"
private const val USERAGENT = "DeArrowKt (https://github.com/zt64/dearrow-kt)"

/**
 * DeArrow client.
 *
 * @constructor Create a DeArrow client with the given [httpClient].
 */
public class DeArrowClient(private val httpClient: HttpClient) : DeArrowApi {
    /**
     * Create a DeArrow client with the given [apiUrl] and [userAgent] using Ktor.
     */
    public constructor(
        apiUrl: String = API_URL,
        userAgent: String = USERAGENT
    ) : this(
        httpClient = HttpClient {
            install(UserAgent) {
                agent = userAgent
            }

            install(ContentNegotiation) {
                json(Json)
            }

            defaultRequest {
                url(apiUrl)
                contentType(ContentType.Application.Json)
            }
        }
    )

    public override suspend fun getBranding(
        videoId: String,
        returnUserId: Boolean,
        fetchAll: Boolean
    ): Branding {
        return httpClient
            .get("branding") {
                parameter("videoID", videoId)
                parameter("returnUserID", returnUserId)
                parameter("fetchAll", fetchAll)
            }.body<Branding>()
    }

    public override suspend fun getBrandingByHash(videoId: String): Branding {
        val hash = calculateHash(videoId)

        return httpClient
            .get("branding/$hash")
            .body<Map<String, Branding>>()
            .filterKeys { it == videoId }
            .values
            .first()
    }

    public override suspend fun createSubmission(
        videoId: String,
        userId: String,
        title: String,
        service: Service,
        downVote: Boolean,
        autoLock: Boolean
    ) {
        postSubmission(
            videoId = videoId,
            userId = userId,
            title = title,
            thumbnail = null,
            service = service,
            downVote = downVote,
            autoLock = autoLock
        )
    }

    public override suspend fun createSubmission(
        videoId: String,
        userId: String,
        thumbnail: ThumbnailSubmission,
        service: Service,
        downVote: Boolean,
        autoLock: Boolean
    ) {
        postSubmission(
            videoId = videoId,
            userId = userId,
            title = null,
            thumbnail = thumbnail,
            service = service,
            downVote = downVote,
            autoLock = autoLock
        )
    }

    public override suspend fun createSubmission(
        videoId: String,
        userId: String,
        title: String,
        thumbnail: ThumbnailSubmission,
        service: Service,
        downVote: Boolean,
        autoLock: Boolean
    ) {
        postSubmission(
            videoId = videoId,
            userId = userId,
            title = title,
            thumbnail = thumbnail,
            service = service,
            downVote = downVote,
            autoLock = autoLock
        )
    }

    private suspend fun postSubmission(
        videoId: String,
        userId: String,
        title: String?,
        thumbnail: ThumbnailSubmission?,
        service: Service,
        downVote: Boolean,
        autoLock: Boolean
    ) {
        httpClient.post("branding") {
            setBody(
                SubmissionRequest(
                    videoId = videoId,
                    userId = userId,
                    userAgent = USERAGENT,
                    service = service,
                    title = title?.let(SubmissionRequest::Title),
                    thumbnail = thumbnail,
                    downvote = downVote,
                    autoLock = autoLock
                )
            )
        }
    }

    private companion object {
        @OptIn(ExperimentalUnsignedTypes::class)
        private fun calculateHash(videoId: String) = SHA256()
            .digest(videoId.toByteArray())
            .asUByteArray()
            .joinToString("") { it.toString(16) }
            .take(4)
    }
}