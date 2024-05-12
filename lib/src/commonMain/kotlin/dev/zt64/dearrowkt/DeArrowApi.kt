package dev.zt64.dearrowkt

import dev.zt64.dearrowkt.model.Branding
import dev.zt64.dearrowkt.model.Service
import dev.zt64.dearrowkt.model.ThumbnailSubmission

public interface DeArrowApi {
    /**
     * Get submission for a video
     * See: [GET /api/branding](https://wiki.sponsor.ajay.app/w/API_Docs/DeArrow#GET_/api/branding)
     *
     * @param videoId
     * @param returnUserId Whether to return the user ID in the response
     * @param fetchAll Hides details with negative score if false
     * @return
     */
    public suspend fun getBranding(
        videoId: String,
        returnUserId: Boolean = false,
        fetchAll: Boolean = false
    ): Branding

    /**
     * Get submission for a video using the SHA256 hash of the video ID for more privacy
     * See: [GET /api/branding](https://wiki.sponsor.ajay.app/w/API_Docs/DeArrow#GET_/api/branding/:sha256HashPrefix)
     *
     * @param videoId
     */
    public suspend fun getBrandingByHash(videoId: String): Branding

    /**
     * Create a submission for a video
     * See: [POST /api/branding](https://wiki.sponsor.ajay.app/w/API_Docs/DeArrow#POST_/api/branding)
     *
     * @param videoId The video ID
     * @param userId The user ID
     * @param service The service
     * @param title
     * @param downVote Whether the submission should be downvoted instead of upvoted
     * @param autoLock Whether the submission should be locked automatically. Applies to VIP users only
     */
    public suspend fun createSubmission(
        videoId: String,
        userId: String,
        title: String,
        thumbnail: ThumbnailSubmission,
        service: Service = Service.YOUTUBE,
        downVote: Boolean = false,
        autoLock: Boolean = false
    )

    /**
     * Create a submission for a video
     * See: [POST /api/branding](https://wiki.sponsor.ajay.app/w/API_Docs/DeArrow#POST_/api/branding)
     *
     * @param videoId The video ID
     * @param userId The user ID
     * @param service The service
     * @param title
     * @param downVote Whether the submission should be downvoted instead of upvoted
     * @param autoLock Whether the submission should be locked automatically. Applies to VIP users only
     */
    public suspend fun createSubmission(
        videoId: String,
        userId: String,
        title: String,
        service: Service = Service.YOUTUBE,
        downVote: Boolean = false,
        autoLock: Boolean = false
    )

    /**
     * Create a submission for a video
     * See: [POST /api/branding](https://wiki.sponsor.ajay.app/w/API_Docs/DeArrow#POST_/api/branding)
     *
     * @param videoId The video ID
     * @param userId The user ID
     * @param service The service
     * @param thumbnail
     * @param downVote Whether the submission should be downvoted instead of upvoted
     * @param autoLock Whether the submission should be locked automatically. Applies to VIP users only
     */
    public suspend fun createSubmission(
        videoId: String,
        userId: String,
        thumbnail: ThumbnailSubmission,
        service: Service = Service.YOUTUBE,
        downVote: Boolean = false,
        autoLock: Boolean = false
    )
}