data class VideoListResponse(
    val page: Int,
    val pages: Int,
    val page_size: Int,
    val total: Int,
    val hits: List<Video>
)

data class Video(
    val id: String,
    val title: String,
    val poster: String,
    val duration: Double,
    val urls: VideoUrls
)

data class VideoUrls(
    val mp4: String,
    val mp4_preview: String,
    val mp4_download: String
)
