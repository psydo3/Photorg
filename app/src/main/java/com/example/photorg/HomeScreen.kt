package com.example.photorg

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    Column {
        TopBar()
        Spacer(modifier = Modifier.height(16.dp))
        AlbumSection()
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
            painter = painterResource(R.drawable.album),
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
fun AlbumSection() {
    Album()
}

@Composable
fun Album(
    albumName: String? = "Unnamed Album",
    albumColor: Color = colorResource(R.color.pink),
) {
    Box(

    ){
        Image(
            painter = painterResource(id = R.drawable.album_icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(albumColor)
        )
    }
}