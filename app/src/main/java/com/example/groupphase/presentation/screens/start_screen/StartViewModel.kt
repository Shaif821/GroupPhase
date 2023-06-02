package com.example.groupphase.presentation.screens.start_screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<StartState>(StartState())
    val state = _state.asStateFlow()

    init {
        // Check if teams exists, if not create the teams and players

    }



}