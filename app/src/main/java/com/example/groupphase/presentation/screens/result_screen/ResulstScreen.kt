package com.example.groupphase.presentation.screens.result_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.groupphase.presentation.components.SimulationCard

@Composable
fun ResultScreen(
    id: String?,
    onNavigateStart: () -> Unit,
    viewModel: ResultViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(id) {
        if(id != null) viewModel.getSimulationById(id.toLong())
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        onNavigateStart()
                    }
            )
            Text(text = "Results", fontSize = 32.sp)
            Spacer(modifier = Modifier.weight(1f))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
        ) {
            if(state.currentSimulation != null) {
                Text(
                    text = "Current simulation $id",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(16.dp)
                )
                SimulationCard(simulation = state.currentSimulation!!)
            }

            Divider(modifier = Modifier.padding(12.dp))

            state.simulations.forEach {
                if(it.id == state.currentSimulation?.id) return@forEach
                SimulationCard(it)
                Divider(modifier = Modifier.padding(12.dp))
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = { onNavigateStart() },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Return to start")
            }
        }
        Text(text = state.error)
    }
}