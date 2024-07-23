package com.example.photorg

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun HomeScreen(navController: NavController) {
    Column {
        TopBar()
        Spacer(modifier = Modifier.height(16.dp))
        AlbumSection(
            navController = navController,
            albums = listOf(
                Album("Singapore", colorResource(R.color.dark_green)),
                Album("Japan", colorResource(R.color.red)),
                Album("Thailand", colorResource(R.color.teal_200)),
                Album("South Korea", colorResource(R.color.teal_200)),
                Album("Barcelona", colorResource(R.color.purple_700)),
            )
        )
    }
}

@Composable
fun TopBar() {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp)
            .padding(end = 12.dp)
    ){
        Text(
            text = "Photorg",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Image(
            painter = painterResource(R.drawable.create_album),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    //implement later
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
        columns = GridCells.Fixed(4),
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

@Composable
fun AlbumItem(
    navController: NavController,
    albumName: String = "Unnamed Album",
    albumColor: Color = colorResource(R.color.pink),
) {
    Box(
        modifier = Modifier
            .size(130.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.album_icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(albumColor),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(bottom = 16.dp)
                .padding(top = 10.dp)
                .clickable {
                    navController.navigate(Routes.albumScreen)
                }
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
                .clickable {

                }
        )
    }
}