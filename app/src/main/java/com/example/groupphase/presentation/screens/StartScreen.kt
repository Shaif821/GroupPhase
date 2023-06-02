package com.example.groupphase.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StartScreen(
    onNavigateSimulate: () -> Unit,
) {


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Start Screen")
    }
}