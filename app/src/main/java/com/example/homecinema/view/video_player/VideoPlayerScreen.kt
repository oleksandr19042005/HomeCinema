package ui.video_player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import ui.video_list.VideoListViewModel

@Composable
fun VideoPlayerScreen(
    videoId: String,
    viewModel: VideoListViewModel,
    navController: NavController
) {
    val videos by viewModel.videos.observeAsState(emptyList())
    val currentIndex by viewModel.currentIndex.observeAsState(0)

    LaunchedEffect(videoId) {
        val initialIndex = videos?.indexOfFirst { it.id == videoId }
        if (initialIndex != null) {
            if (initialIndex >= 0) {
                viewModel.setCurrentIndex(initialIndex)
            }
        }
    }

    val currentVideoUrl = videos?.getOrNull(currentIndex)?.urls?.mp4

    currentVideoUrl?.let { url ->
        VideoPlayer(
            videoUrl = url,
            viewModel = viewModel
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { viewModel.previousVideo() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Previous Video")
            }
            IconButton(onClick = { viewModel.nextVideo() }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Next Video")
            }
        }
    } ?: run {
        Text("Loading video...")
    }
}

@Composable
fun VideoPlayer(videoUrl: String, viewModel: VideoListViewModel) {
    val context = LocalContext.current
    val player = remember { ExoPlayer.Builder(context).build() }

    LaunchedEffect(videoUrl) {
        player.setMediaItem(MediaItem.fromUri(videoUrl))
        player.prepare()
        player.seekTo(viewModel.currentVideoPosition)
        player.playWhenReady = true
    }

    DisposableEffect(player) {
        onDispose {
            viewModel.currentVideoPosition = player.currentPosition
            player.release()
        }
    }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        factory = {
            PlayerView(context).apply {
                this.player = player
            }
        }
    )
}





