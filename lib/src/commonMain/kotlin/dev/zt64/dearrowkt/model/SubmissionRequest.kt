package dev.zt64.dearrowkt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Submission request body */
@Serializable
internal data class SubmissionRequest(
    @SerialName("videoID")
    val videoId: String,
    @SerialName("userID")
    val userId: String,
    val userAgent: String,
    val service: Service? = null,
    val title: Title? = null,
    val thumbnail: ThumbnailSubmission? = null,
    val downvote: Boolean? = null,
    val autoLock: Boolean? = null
) {
    @Serializable
    data class Title(val title: String)
}