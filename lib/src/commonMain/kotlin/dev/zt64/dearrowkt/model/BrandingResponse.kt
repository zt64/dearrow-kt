package dev.zt64.dearrowkt.model

import dev.zt64.dearrowkt.DeArrowApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Branding response returned from [DeArrowApi.getBranding]
 *
 * @property titles
 * @property thumbnails
 * @property randomTime
 * @property videoDuration
 */
@Serializable
public data class Branding internal constructor(
    val titles: List<Title>,
    val thumbnails: List<Thumbnail>,
    val randomTime: Float,
    val videoDuration: Double?
)

/**
 * @property original Whether the title or thumbnail is original
 * @property votes
 * @property locked
 * @property uuid
 * @property userId Only present if requested
 */
@Serializable
internal sealed interface Item {
    val original: Boolean
    val votes: Int
    val locked: Boolean
    val uuid: String
    val userId: String?
}

@Serializable
public data class Title internal constructor(
    val title: String,
    override val original: Boolean,
    override val votes: Int,
    override val locked: Boolean,
    @SerialName("UUID")
    override val uuid: String,
    @SerialName("userID")
    override val userId: String? = null
) : Item

@Serializable
public data class Thumbnail internal constructor(
    val timestamp: Float? = null,
    override val original: Boolean,
    override val votes: Int,
    override val locked: Boolean,
    @SerialName("UUID")
    override val uuid: String,
    @SerialName("userID")
    override val userId: String
) : Item