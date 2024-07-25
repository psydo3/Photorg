package com.example.photorg

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
    ) {
        TopBar()
        Spacer(modifier = Modifier.height(16.dp))
        AlbumSection(
            navController = navController,
            albums = listOf(
                Album("Singapore", colorResource(R.color.color_option1)),
                Album("Japan", colorResource(R.color.color_option4)),
                Album("Thailand", colorResource(R.color.color_option2)),
                Album("South Korea", colorResource(R.color.color_option3)),
                Album("Barcelona", colorResource(R.color.color_option4)),
            )
        )
    }
}

@Composable
fun TopBar(
) {
    val openDialog = remember { mutableStateOf(false) }

    Divider(thickness = 1.5.dp, color = Color.Black)

    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.topbar_background))
            .padding(14.dp)
            .padding(end = 12.dp)

    ){
        Text(
            text = "Photorg",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp
        )

        Image(
            painter = painterResource(R.drawable.create_album),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clickable(
                    onClick = { openDialog.value = true }
                )
        )
    }

    Divider(thickness = 1.5.dp, color = Color.Black)

    if (openDialog.value) {
        NewAlbumDialog(
            onDismiss = {
                openDialog.value = false
                        },
            onConfirm = { name, color ->
                openDialog.value = false
            }
        )
    }
}

@Composable
fun AlbumSection(
    navController: NavController,
    albums: List<Album>,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .scale(0.99f)
            .padding(end = 6.dp),
    ){
        items(albums.size){
            AlbumItem(
                navController = navController,
                albumName = albums[it].albumName,
                albumColor = albums[it].albumColor
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumItem(
    navController: NavController,
    albumName: String = "Unnamed Album",
    albumColor: Color = colorResource(R.color.color_option2),
) {
    var colorValue = 0

    val colorResources = mapOf(
        0 to colorResource(id = R.color.color_option2),
        1 to colorResource(id = R.color.color_option4),
        2 to colorResource(id = R.color.color_option3),
        3 to colorResource(id = R.color.color_option1),
    )
    for (color in colorResources) {
        if (albumColor == color.value) {
            colorValue = color.key
        }
    }

    val openDialog = remember { mutableStateOf(false) }

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
                        navController.navigate(Routes.albumScreen+"/$albumName/$colorValue")
                    },
                    onLongClick = {
                        openDialog.value = true
                    }
                ),
            colorFilter = ColorFilter.tint(albumColor),
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
                .clickable(
                    onClick = { openDialog.value = true }
                )
        )

        if (openDialog.value) {
            NewAlbumDialog(
                namePassed = albumName,
                colorPassed = albumColor,
                onDismiss = {
                    openDialog.value = false
                },
                onConfirm = { name, color ->
                    openDialog.value = false
                }
            )
        }
    }
}