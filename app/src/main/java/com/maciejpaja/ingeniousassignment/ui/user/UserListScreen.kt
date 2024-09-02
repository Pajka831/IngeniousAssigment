package com.maciejpaja.ingeniousassignment.ui.user

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.ImageLoader
import com.maciejpaja.ingeniousassignment.R
import com.maciejpaja.ingeniousassignment.models.User
import com.maciejpaja.ingeniousassignment.ui.detail.DetailEvent
import com.maciejpaja.ingeniousassignment.ui.theme.Purple40
import com.maciejpaja.ingeniousassignment.ui.theme.PurpleGrey40
import org.koin.androidx.compose.koinViewModel


@Composable
fun UserSearchView(
    onSearchQueryChange: (String) -> Unit
) {
    Column {
        var text by remember { mutableStateOf("") }

        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearchQueryChange(it)
            },
            maxLines = 1,
            textStyle = TextStyle(fontSize = 16.sp, color = PurpleGrey40),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp)
                .shadow(elevation = 2.dp, spotColor = PurpleGrey40),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp, horizontal = 4.dp),
                    content = {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.icon_search),
                            tint = PurpleGrey40,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 2.dp)
                        )
                        Box(
                            modifier = Modifier.weight(19f),
                            contentAlignment = Alignment.CenterStart,
                            content = {
                                if (text.isEmpty()) {
                                    Text(
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 2.dp),
                                        text = "Search",
                                        color = Color.Gray
                                    )
                                }
                                innerTextField()
                            }
                        )
                        Spacer(Modifier.weight(1f))
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.icon_microphone),
                            tint = PurpleGrey40,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 2.dp)
                        )
                    }
                )

            }
        )
    }
}

@Composable
fun UserList(
    userList: List<User>,
    imageLoader: ImageLoader,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        itemsIndexed(
            userList
        ) { index, user ->
            UserItem(user = user, imageLoader, onItemClick)
        }
    }
}

@Composable
fun UserItem(user: User, imageLoader: ImageLoader, onItemClick: (Int) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onItemClick.invoke(user.id) }
            .height(60.dp)
    ) {

        Text(
            text = user.login,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        Spacer(Modifier.weight(1f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAppBar(
    onBackArrowClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Purple40),
        navigationIcon = {
            IconButton(onClick = onBackArrowClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_back),
                    contentDescription = "Localized description",
                    tint = White
                )
            }
        }
    )
}

@Composable
fun UserScreen(
    imageLoader: ImageLoader, onItemClick: (Int) -> Unit, onBackArrowClick: () -> Unit
) {
    val viewModel: UserViewModel = koinViewModel()
    val userState by viewModel.usersState.collectAsStateWithLifecycle()
    val eventState  by viewModel.event.collectAsState(initial = DetailEvent.Loading)



    Column {
        UserAppBar(
            onBackArrowClick
        )

        UserSearchView {
            viewModel.onSearchInputChange(it)
        }

        if (userState == null) {
            when (eventState) {
                is DetailEvent.Loading -> {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                is DetailEvent.Error -> Toast.makeText(
                    LocalContext.current,
                    (eventState as DetailEvent.Error).error,
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            UserList(
                userList = userState!!,
                imageLoader = imageLoader,
                onItemClick = onItemClick
            )
        }
    }
}