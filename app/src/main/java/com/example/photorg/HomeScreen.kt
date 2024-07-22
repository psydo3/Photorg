package com.example.photorg

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    Column {
        TopBar()
    }
}

@Composable
fun TopBar(modifier: Modifier = Modifier) {
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

        //change icon to real image
        Icon(
            imageVector = Icons.Default.Create,
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    //implement later
                }
        )
    }
}