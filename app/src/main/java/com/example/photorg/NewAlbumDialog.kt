package com.example.photorg

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource

@Composable
fun NewAlbumDialog(
    onDismiss: () -> Unit,
    onConfirm: (
        albumName: String,
        albumColor: Color
            ) -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(text = "Create a new album")
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm("Test", Color.Magenta)
                }
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }) {
                Text("Cancel")
            }
        }
    )

}