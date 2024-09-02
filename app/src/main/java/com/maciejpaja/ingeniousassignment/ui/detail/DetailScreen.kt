package com.maciejpaja.ingeniousassignment.ui.detail

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import coil.compose.AsyncImage
import com.maciejpaja.ingeniousassignment.ui.user.UserAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(userId: Int, imageLoader: ImageLoader, onBackArrowClick: () -> Unit) {

    val viewModel: DetailViewModel = koinViewModel()
    val detailState by viewModel.detailsState.collectAsStateWithLifecycle()
    val eventState by viewModel.event.collectAsState(initial = DetailEvent.Loading)

    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LaunchedEffect(Unit) {
            viewModel.getUserDetails(userId)
        }

        UserAppBar(onBackArrowClick)

        if (detailState == null) {
            when (eventState) {
                is DetailEvent.Loading -> {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                is DetailEvent.Error -> Toast.makeText(
                    LocalContext.current, (eventState as DetailEvent.Error).error, Toast.LENGTH_LONG
                ).show()
            }
        } else {
            detailState?.let {

                UserAvatarImage(avatarUrl = it.avatarUrl ?: "", imageLoader = imageLoader)

                it.name?.let { name ->
                    Text(
                        text = name, modifier = Modifier.padding(bottom = 32.dp)

                    )
                }
                it.login?.let { login ->
                    Text(
                        text = login, modifier = Modifier.padding(bottom = 32.dp)
                    )
                }

                it.email?.let { email ->
                    Text(
                        text = email, modifier = Modifier.padding(bottom = 32.dp)
                    )
                }

                it.location?.let { location ->
                    Text(
                        text = location, modifier = Modifier
                    )
                }
            }
        }

    }
}


@Composable
fun UserAvatarImage(avatarUrl: String, imageLoader: ImageLoader) {
    AsyncImage(
        model = avatarUrl,
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .height(320.dp)
            .width(200.dp)
            .padding(top = 60.dp, bottom = 60.dp),
        imageLoader = imageLoader
    )
}