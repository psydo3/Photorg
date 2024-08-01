package com.example.photorg.albumpage.ui

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun PhotoSelect(
    selectedImageUris: List<Uri>,
) : List<Uri>{

    return selectedImageUris
}