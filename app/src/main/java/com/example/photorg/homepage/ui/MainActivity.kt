package com.example.photorg.homepage.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.photorg.navigation.Navigation
import com.example.photorg.ui.theme.PhotorgTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotorgTheme {
                Navigation()
            }
        }
    }
}