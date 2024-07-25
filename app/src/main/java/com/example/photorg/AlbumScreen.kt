package com.example.photorg

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun AlbumScreen(navController: NavController, albumName: String?, colorVal: Int?) {
    Log.d("c", colorVal.toString())
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        NameAndDateBar(albumName, colorVal)
        CameraSection()
    }

}

@Composable
fun NameAndDateBar(albumName: String?, colorVal: Int?) {
    val colorResources = mapOf(
        0 to colorResource(id = R.color.color_option2),
        1 to colorResource(id = R.color.color_option4),
        2 to colorResource(id = R.color.color_option3),
        3 to colorResource(id = R.color.color_option1),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Divider(thickness = 1.5.dp, color = Color.Black)

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResources.entries.elementAt(colorVal!!).value)
                .padding(14.dp)
                .padding(end = 12.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = albumName.toString(),
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
        Divider(thickness = 1.5.dp, color = Color.Black)
    }

}

@Composable
fun CameraSection(
) {
    val viewModel = viewModel<PermissionDialogViewModel>()
    val dialogQueue = viewModel.visiblePermissionDialogQueue

    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel.onPermissionResult(
                permission = Manifest.permission.CAMERA,
                isGranted = isGranted
            )
        }
    )

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.topbar_background)),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Divider(thickness = 1.5.dp, color = Color.Black, modifier = Modifier.padding(bottom = 12.dp))

        Button(
            modifier = Modifier
                .size(100.dp),

            onClick = {
                cameraPermissionResultLauncher.launch(Manifest.permission.CAMERA)
            },
            elevation =  ButtonDefaults.buttonElevation(
                defaultElevation = 10.dp,
                pressedElevation = 15.dp,
                disabledElevation = 0.dp
            ),
            border = BorderStroke(1.dp, Color.Black),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            shape = CircleShape,
        ) {
            Image(
                painter = painterResource(id = R.drawable.camera_icon),
                contentDescription = null,
            )
        }

        Divider(thickness = 1.5.dp, color = Color.Black, modifier = Modifier.padding(top = 12.dp))
    }

    val context = LocalContext.current
    dialogQueue
        .reversed()

        .forEach { permission ->
            PermissionDialog(

                permissionTextProvider = when (permission) {
                    Manifest.permission.CAMERA -> {
                        CameraPermissionTextProvider()
                    }
                    else -> {
                        error("Unknown permission")
                    }
                },

                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(LocalContext.current as Activity, permission),

                onDismiss = viewModel::dismissDialog,
                onOkClick = {
                    viewModel.dismissDialog()
                    cameraPermissionResultLauncher.launch(Manifest.permission.CAMERA)
                },
                onGoToAppSettingsClick = { openAppSettings(context = context) },
            )
        }
}

fun openAppSettings(context: Context) {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    )
    context.startActivity(intent)
}
