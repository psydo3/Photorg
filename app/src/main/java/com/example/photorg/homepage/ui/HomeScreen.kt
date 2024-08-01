package com.example.photorg.homepage.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.photorg.R
import com.example.photorg.homepage.data.AlbumEvent
import com.example.photorg.homepage.data.AlbumsState
import com.example.photorg.homepage.data.SortType
import com.example.photorg.navigation.Routes

@Composable
fun HomeScreen(
    navController: NavController,
    state: AlbumsState,
    onEvent: (AlbumEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
    ){
        TopBar(
            state = state,
            onEvent = onEvent
        )
        Spacer(modifier = Modifier.height(16.dp))
        AlbumSection(
            navController = navController,
            state = state,
            onEvent = onEvent
        )

        if (state.isAddingAlbum) {
            AddAlbumDialog(
                state = state,
                onEvent = onEvent
            )
        }
    }
}

@Composable
fun TopBar(
    state: AlbumsState,
    onEvent: (AlbumEvent) -> Unit
) {
    Divider(thickness = 1.5.dp, color = Color.Black)

    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.topbar_background))
            .padding(14.dp)

    ){
        Text(
            text = "Photorg",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp
        )

        Row (
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        ){
            Image(
                painter = painterResource(R.drawable.sort),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .size(40.dp)
                    .clickable {
                        onEvent(AlbumEvent.SortAlbums(SortType.COLOR))
                    }
            )
            Image(
                painter = painterResource(R.drawable.create_album),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        onEvent(AlbumEvent.ShowDialog)
                    }
            )
        }
    }

    Divider(thickness = 1.5.dp, color = Color.Black)
}

@Composable
fun AlbumSection(
    state: AlbumsState,
    onEvent: (AlbumEvent) -> Unit,
    navController: NavController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .scale(0.99f)
            .padding(end = 6.dp),
    ){
        items(state.albums.size){
            AlbumItem(
                navController = navController,
                albumName = state.albums[it].albumName,
                albumColor = state.albums[it].albumColor,
                onEvent = onEvent,
                state = state,
                albumIndex = it
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumItem(
    navController: NavController,
    albumName: String = "Unnamed Album",
    albumColor: Int = 0,
    onEvent: (AlbumEvent) -> Unit,
    state: AlbumsState,
    albumIndex: Int
) {
    val colorResources = mapOf(
        0 to colorResource(id = R.color.color_option2),
        1 to colorResource(id = R.color.color_option4),
        2 to colorResource(id = R.color.color_option3),
        3 to colorResource(id = R.color.color_option1),
    )

    Box(
        modifier = Modifier
            .size(155.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.album_icon),
            contentDescription = null,

            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(bottom = 16.dp)
                .padding(top = 10.dp)
                .combinedClickable(
                    onClick = {
                        navController.navigate(Routes.albumScreen + "/$albumName/$albumColor")
                    },
                    onLongClick = {

                    }
                ),
            colorFilter = ColorFilter.tint(colorResources.entries.elementAt(albumColor).value),
        )
        Image(
            painter = painterResource(id = R.drawable.close),
            contentDescription = null,
            Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
                .size(30.dp)
                .border(
                    width = 1.25.dp,
                    color = Color.Black,
                    shape = CircleShape
                )
                .clickable {
                    onEvent(AlbumEvent.DeleteAlbum(state.albums[albumIndex]))
                }
        )
        Text(
            text = albumName,
            fontWeight = FontWeight.W500,
            fontSize = 13.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Black,
            modifier = Modifier
                .width(78.dp)
                .align(Alignment.BottomCenter)
                .clickable {

                }
        )
    }
}