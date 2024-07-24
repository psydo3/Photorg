package com.example.photorg

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AlbumScreen(navController: NavController) {
    NameAndDateBar()
    Divider(thickness = 2.5.dp, color = Color.Black)
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun NameAndDateBar() {
    Divider(thickness = 2.5.dp, color = Color.Black)

    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.topbar_background))
            .padding(14.dp)
            .padding(end = 12.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "South Korea",
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            )
            Text(
                text = "24 July, 2024",
                fontWeight = FontWeight.W400,
                fontSize = 12.sp
            )
        }


        //implement delete / edit button
    }
}

