package dev.zt64.dearrowkt.model

import kotlinx.serialization.Serializable

/**
 * Thumbnail
 *
 * @property timestamp
 * @property original
 */
@Serializable
public data class ThumbnailSubmission internal constructor(
    val timestamp: Int?,
    val original: Boolean
) {
    /**
     * Create a thumbnail from a timestamp
     *
     * @param timestamp The timestamp of the thumbnail in seconds
     */
    public constructor(timestamp: Int) : this(timestamp, false)

    /**
     * Create an original thumbnail
     */
    public constructor() : this(null, true)
}