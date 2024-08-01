package com.example.photorg.albumpage.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import coil.compose.AsyncImage
import com.example.photorg.homepage.data.AlbumEvent
import com.example.photorg.homepage.data.AlbumsState
import com.example.photorg.R
import com.example.photorg.albumpage.data.PermissionDialogViewModel
import java.io.File
import java.io.FileOutputStream

public var pictureCount = 0

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AlbumScreen(
    navController: NavController,
    albumName: String?, colorVal: Int?,
    state: AlbumsState,
    onEvent: (AlbumEvent) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        NameAndDateBar(albumName, colorVal)

        ImageSection(
            albumName.toString()
        )

        CameraSection(
            albumName.toString()
        )
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
fun ImageSection(
    albumName: String,
) {
    val file = File(LocalContext.current.filesDir, "")

    val files by remember {
        mutableStateOf(file.listFiles()?.filter { it.name.endsWith(".jpg") }!!)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(.7f)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        items(files.size){
                AsyncImage(
                    model = files[it],
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp),
                )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun CameraSection(
    albumName : String
) {
    val context = LocalContext.current

    val permissionsToRequest = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_MEDIA_IMAGES,
    )

    val viewModel = viewModel<PermissionDialogViewModel>()
    val dialogQueue = viewModel.visiblePermissionDialogQueue

    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionsToRequest.forEach { permission ->
                viewModel.onPermissionResult(
                    permission = permission,
                    isGranted = if(perms.keys.contains(permission)){
                        perms[permission] == true
                    } else true,
                )
            }
        }
    )

    var selectedImageUris by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }

    val file = File(context.filesDir, albumName)

    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            if (uris.isNotEmpty()){
                uris.forEach { uri ->
                    val inputStream = context.contentResolver.openInputStream(uri).use {
                        it?.readBytes()
                    }
                    FileOutputStream(file.toString() + pictureCount++ + "_" + albumName + ".jpg").use {
                        it.write(inputStream)
                    }
                    Log.d("uri", file.toString())
                }

            }
            selectedImageUris = uris
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
            onClick = {
                multiplePermissionResultLauncher.launch(permissionsToRequest)

                if (
                    (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES)
                            == PackageManager.PERMISSION_GRANTED))
                {
                    multiplePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
            },
            modifier = Modifier
                .size(100.dp),
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

    dialogQueue
        .reversed()

        .forEach { permission ->
            PermissionDialog(
                permissionTextProvider = when (permission) {
                    Manifest.permission.CAMERA -> {
                        CameraPermissionTextProvider()
                    }
                    Manifest.permission.READ_MEDIA_IMAGES -> {
                       StoragePermissionTextProvider()
                    }
                    else -> {
                        error("Unknown permission")
                    }
                },

                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(LocalContext.current as Activity, permission),

                onDismiss = viewModel::dismissDialog,
                onOkClick = {
                    viewModel.dismissDialog()
                    multiplePermissionResultLauncher.launch(permissionsToRequest)
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
