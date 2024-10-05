package ui.video_list

import androidx.lifecycle.viewmodel.compose.viewModel

import Video
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.homecinema.R


@Composable
fun VideoListScreen(navController: NavController, viewModel: VideoListViewModel = viewModel()) {

    val videos by viewModel.videos.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)

    LaunchedEffect(Unit) {
        viewModel.fetchVideos()
    }

    Column(verticalArrangement = Arrangement.SpaceBetween) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.07f)
                .background(Color.Black),
                verticalAlignment = Alignment.CenterVertically
            ) {
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "logo app",
                modifier = Modifier
                    .fillMaxWidth(0.14f)
                    .padding(start = 20.dp)
            )


            Text(
                text = "Home Cinema",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 23.sp,
                modifier = Modifier.padding(start = 15.dp)
            )
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.background(Color.Black)
            ) {

                items(videos ?: emptyList()) { video ->
                    VideoItem(video = video, navController = navController)
                }
            }
        }
    }
}


@Composable
fun VideoItem(video: Video, navController: NavController) {
    val viewModel: VideoListViewModel = hiltViewModel()

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        val painter = rememberImagePainter(video.poster)
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(8.dp))
                .clickable { navController.navigate("videoPlayer/${video.id}") },
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = video.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        top = 5.dp, end = 11.dp
                    ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = viewModel.formatDuration(video.duration),
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 15.sp,
                modifier = Modifier.padding(
                    top = 5.dp, end = 3.dp
                )

            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}


