package com.example.photorg

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class PermissionDialogViewModel: ViewModel() {
    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    //Removes dialog box from queue
    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    //If permission is not granted, add a dialog box to queue
    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ) {
        if(!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }
}