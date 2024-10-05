package ui.video_list

import Video
import VideoUrls
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homecinema.model.local.VideoEntity
import com.example.homecinema.model.repository.VideoDataBaseRepository
import com.example.homecinema.model.repository.VideoRepository
import kotlinx.coroutines.launch

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val videoDataBaseRepository: VideoDataBaseRepository,
    private val videoRepository: VideoRepository,
    private val connectivityManager: ConnectivityManager?
) : ViewModel() {

    private val _videos = MutableLiveData<List<Video>?>()
    val videos: MutableLiveData<List<Video>?> = _videos

    private val _currentIndex = MutableLiveData(0)
    val currentIndex: LiveData<Int> = _currentIndex

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    var currentVideoPosition: Long = 0L

    //функція для спочатку очищення а потім вставки відео
    private suspend fun insertCachedVideos(videos: List<Video>?) {
        if (videos != null) {
            videoDataBaseRepository.deleteCashVideos()
            val videoEntities = videos.map { video ->
                VideoEntity(
                    id = video.id, title = video.title, duration = video.duration
                )
            }
            videoDataBaseRepository.insertCashVideos(videoEntities)
        }
    }

    //функція для отримання списку відео
    fun fetchVideos() {
        if (isNetworkAvailable()) {
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    val response = videoRepository.getVideos()
                    val videoList = response?.hits
                    _videos.value = videoList

                    insertCachedVideos(videoList)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    _isLoading.value = false
                }
            }
        } else {

            viewModelScope.launch {
                val videoEntities = videoDataBaseRepository.getAllVideos()
                videoEntities?.let {
                    val videoList = it.map { entity ->
                        Video(
                            id = entity.id,
                            title = entity.title,
                            duration = entity.duration,
                            poster = "",
                            urls = VideoUrls("", "", "")
                        )
                    }
                    _videos.value = videoList
                }
            }
        }
    }

    // Функція для перемикання на наступне відео
    fun nextVideo() {
        _videos.value?.let { list ->
            _currentIndex.value?.let { current ->
                if (current < list.size - 1) {
                    _currentIndex.value = current + 1
                }
            }
        }
    }

    // Функція для перемикання на попереднє відео
    fun previousVideo() {
        _videos.value?.let { list ->
            _currentIndex.value?.let { current ->
                if (current > 0) {
                    _currentIndex.value = current - 1
                }
            }
        }
    }

    // Функція для встановлення індексу поточного відео
    fun setCurrentIndex(index: Int) {
        _currentIndex.value = index
    }

    // Перевірка доступності інтернету
    private fun isNetworkAvailable(): Boolean {
        val activeNetwork = connectivityManager?.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    // Форматування тривалості у формат хвилин і секунд
    fun formatDuration(durationInSeconds: Double): String {
        val totalSeconds = durationInSeconds.toInt()
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}