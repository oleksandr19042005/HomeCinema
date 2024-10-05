package com.example.homecinema

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homecinema.view.view.viewmodel.theme.HomeCinemaTheme
import dagger.hilt.android.AndroidEntryPoint
import ui.video_list.VideoListScreen
import ui.video_list.VideoListViewModel
import ui.video_player.VideoPlayerScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: VideoListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeCinemaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "videoList") {
                        composable("videoList") {
                            VideoListScreen(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                        composable("videoPlayer/{videoId}") { backStackEntry ->
                            val videoId = backStackEntry.arguments?.getString("videoId") ?: ""
                            VideoPlayerScreen(
                                videoId = videoId,
                                viewModel = viewModel,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}