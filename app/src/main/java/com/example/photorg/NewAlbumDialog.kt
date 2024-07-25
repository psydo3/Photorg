package com.example.photorg

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp

@Composable
fun NewAlbumDialog(
    onDismiss: () -> Unit,
    onConfirm: (
        albumName: String,
        albumColor: Color,
            ) -> Unit,
    namePassed: String = "",
    colorPassed: Color = colorResource(id = R.color.color_option2),
) {
    var albumText by remember { mutableStateOf(namePassed) }
    var selectedColor by remember { mutableStateOf(0) }

    val colorResources = mapOf(
        0 to colorResource(id = R.color.color_option2),
        1 to colorResource(id = R.color.color_option4),
        2 to colorResource(id = R.color.color_option3),
        3 to colorResource(id = R.color.color_option1),
    )

    if (namePassed.isNotEmpty()) {
        for (color in colorResources.entries) {
            if (color.value == colorPassed) {
                selectedColor = color.key
            }
        }
    }

    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(if (albumText.isEmpty()) "Create new album" else "Edit album")
        },
        text = {
            Column(
                Modifier.fillMaxWidth()
            ){
                OutlinedTextField(
                    value = albumText,
                    label = { Text("Album name") },
                    onValueChange = {
                        albumText = it
                    },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (color in colorResources.entries) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(6.dp)
                                .height(55.dp)
                                .border(
                                    width = if (selectedColor == color.key) 3.dp else 1.dp,
                                    color = Color.Black,
                                    shape = CircleShape,
                                )
                                .padding(5.dp)
                                .clip(RoundedCornerShape(200.dp))
                                .background(color.value)
                                .clickable {
                                    selectedColor = color.key
                                }

                        ){
                            Text(" ")
                        }
                    }
                }
            }


        },

        confirmButton = {
            Button(
                onClick = {
                    onConfirm("Test", colorResources[selectedColor]!!)
                },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.button_color)
                )
            ) {
                Text(
                    if (albumText.isEmpty()) "Create" else "Save"
                )
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.button_color)
                )
                ) {
                Text("Cancel")
            }
        }
    )
}